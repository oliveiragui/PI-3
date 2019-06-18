import java.awt.Canvas;
import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import java.lang.Runnable;
import java.lang.Thread;

import javax.swing.JFrame;

import javax.imageio.ImageIO;

import java.io.IOException;
import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Game extends JFrame implements Runnable
{
    public static int alpha = 0xFFFF00DC;

    private Canvas canvas = new Canvas();
    private RenderHandler renderer;

    private SpriteSheet sheet;
    private Sprite logoSprite;
    private Sprite titleSprite;
    private Sprite level1Sprite;
    private Sprite level2Sprite;
    private Sprite editSprite;
    private SpriteSheet playerSheet;

    private int selectedTileID = 2;
    private int selectedLayer = 0;

    private Tiles tiles;
    private Map map;
    private Map map2;

    private GameObject[] objects;
    private KeyBoardListener keyListener = new KeyBoardListener(this);
    private MouseEventListener mouseListener = new MouseEventListener(this);

    private Player player;
    private Enemy enemy;
    private Menu title;
    private Menu logo;
    private Menu level1;
    private Menu level2;
    private Menu edit;


    public boolean editMode = false;

    private int xZoom = 2;
    private int yZoom = 2;

    public int currentScreen = 0;
    private int playerStartX = 7;
    private int playerStartY = 7;
    private int enemyStartX = 4;
    private int enemyStartY = 7;


    public Game()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setBounds(0,0, 16 * 36, 14 * 36);

        setLocationRelativeTo(null);

        add(canvas);

        setVisible(true);

        canvas.createBufferStrategy(3);

        renderer = new RenderHandler(getWidth(), getHeight());

        BufferedImage logoImage = loadImage("sprites/bagpass.png");
        logoSprite = new Sprite(logoImage);

        BufferedImage titleImage = loadImage("sprites/LOGO.png");
        titleSprite = new Sprite(titleImage);

        BufferedImage level1Image = loadImage("sprites/level1.png");
        level1Sprite = new Sprite(level1Image);

        BufferedImage level2Image = loadImage("sprites/level2.png");
        level2Sprite = new Sprite(level2Image);

        BufferedImage editImage = loadImage("sprites/edit.png");
        editSprite = new Sprite(editImage);

        BufferedImage sheetImage = loadImage("sprites/arv-cinz.png");
        sheet = new SpriteSheet(sheetImage);
        sheet.loadSprites(16, 16);

        BufferedImage playerSheetImage = loadImage("sprites/player2.png");
        playerSheet = new SpriteSheet(playerSheetImage);
        playerSheet.loadSprites(18, 18);

        AnimatedSprite playerAnimations = new AnimatedSprite(playerSheet, 1);

        tiles = new Tiles(new File("src/Tiles.txt"),sheet);

        map = new Map(new File("src/Map.txt"), tiles);

        map2 = new Map(new File("src/Map2.txt"), tiles);

        GUIButton[] buttons = new GUIButton[tiles.size()];
        Sprite[] tileSprites = tiles.getSprites();

        for(int i = 0; i < buttons.length; i++)
        {
            Rectangle tileRectangle = new Rectangle(0, i*(16*xZoom + 2), 16*xZoom, 16*yZoom);

            buttons[i] = new SDKButton(this, i, tileSprites[i], tileRectangle);
        }

        GUI gui = new GUI(buttons, 5, 5, true);

        objects = new GameObject[8];
        player = new Player(playerAnimations, xZoom, yZoom, playerStartX, playerStartY);
        enemy = new Enemy(playerAnimations, xZoom, yZoom, enemyStartX, enemyStartY);
        logo = new Menu(logoSprite, getWidth()/2  - (logoSprite.getWidth() / 2), getHeight()/2 - (logoSprite.getHeight() / 2), 0, this);
        title = new Menu(titleSprite, getWidth()/2  - (titleSprite.getWidth() / 2), getHeight() / 2 - (titleSprite.getHeight() / 2) - 150, 99, this);
        level1 = new Menu(level1Sprite, getWidth()/2  - (level1Sprite.getWidth() / 2), getHeight()/2, 1, this);
        level2 = new Menu(level2Sprite, getWidth()/2  - (level2Sprite.getWidth() / 2), getHeight()/2 + 50, 2, this);
        edit = new Menu(editSprite, getWidth()/2  - (editSprite.getWidth() / 2), getHeight()/2 + 150, 3, this);
        objects[0] = player;
        objects[1] = enemy;
        objects[2] = gui;
        objects[3] = logo;
        objects[4] = title;
        objects[5] = level1;
        objects[6] = level2;
        objects[7] = edit;

        canvas.addKeyListener(keyListener);
        canvas.addFocusListener(keyListener);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);

        addComponentListener(new ComponentListener() {
            public void componentResized(ComponentEvent e) {
                int newWidth = canvas.getWidth();
                int newHeight = canvas.getHeight();

                if(newWidth > renderer.getMaxWidth())
                    newWidth = renderer.getMaxWidth();

                if(newHeight > renderer.getMaxHeight())
                    newHeight = renderer.getMaxHeight();

                renderer.getCamera().w = newWidth;
                renderer.getCamera().h = newHeight;
                canvas.setSize(newWidth, newHeight);
                pack();
            }

            public void componentHidden(ComponentEvent e) {}
            public void componentMoved(ComponentEvent e) {}
            public void componentShown(ComponentEvent e) {}
        });
        canvas.requestFocus();
    }


    public void update()
    {
        for(int i = 0; i < objects.length; i++)
            objects[i].update(this);
        gameLogics();
    }

    private void gameLogics() {
        // No level 1 quando o player e o inimigo conseguem estar perto da arvore na mesma linha horizontal
        // destrava uma passagem pra finalizar a fase
        if (enemy.getRectangle().x > player.getRectangle().x-100 && enemy.getRectangle().y == 87 && player.getRectangle().y == 87) {
            map.removeTile(selectedLayer, -1, 0);
        }
        // Quando eles chegam ao topo da fase, volta pra seleçao de level
        if (player.getRectangle().y < -200) {
            map.removeTile(selectedLayer, -9, 0);
            if (enemy.getRectangle().y < -200) {
                currentScreen = 1;
                player.getRectangle().setX(playerStartX);
                player.getRectangle().setY(playerStartY);
                enemy.getRectangle().setX(enemyStartX);
                enemy.getRectangle().setY(enemyStartY);
            }
        }


    }


    private BufferedImage loadImage(String path)
    {
        try
        {
            BufferedImage loadedImage = ImageIO.read(Game.class.getResource(path));
            BufferedImage formattedImage = new BufferedImage(loadedImage.getWidth(), loadedImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            formattedImage.getGraphics().drawImage(loadedImage, 0, 0, null);

            return formattedImage;
        }
        catch(IOException exception)
        {
            exception.printStackTrace();
            return null;
        }
    }

    public void handleCTRL(boolean[] keys)
    {
        if(keys[KeyEvent.VK_S]) {
            if (currentScreen == 2 && editMode)
                map.saveMap();
            if (currentScreen == 3 && editMode)
                map2.saveMap();

        }
    }

    public void leftClick(int x, int y)
    {

        Rectangle mouseRectangle = new Rectangle(x, y, 1, 1);
        boolean stoppedChecking = false;

        if(!stoppedChecking)
            stoppedChecking = objects[3].handleMouseClick(mouseRectangle, renderer.getCamera(), xZoom, yZoom);

        for(int i = 0; i < objects.length; i++)
            if(!stoppedChecking)
                stoppedChecking = objects[i].handleMouseClick(mouseRectangle, renderer.getCamera(), xZoom, yZoom);

        if(!stoppedChecking)
        {
            x = (int) Math.floor((x + renderer.getCamera().x)/(16.0 * xZoom));
            y = (int) Math.floor((y + renderer.getCamera().y)/(16.0 * yZoom));

            if (currentScreen == 2 && editMode) {
                map.setTile(selectedLayer, x, y, selectedTileID);
            }
            if (currentScreen == 3 && editMode) {
                map2.setTile(selectedLayer, x, y, selectedTileID);
            }

        }
    }

    public void rightClick(int x, int y)
    {
        x = (int) Math.floor((x + renderer.getCamera().x)/(16.0 * xZoom));
        y = (int) Math.floor((y + renderer.getCamera().y)/(16.0 * yZoom));
        if (currentScreen == 2 && editMode)
            map.removeTile(selectedLayer, x, y);
        if (currentScreen == 3 && editMode)
            map2.removeTile(selectedLayer, x, y);
    }

    public void render() {
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        super.paint(graphics);

        if (currentScreen == 0){
            renderer.renderSprite(logoSprite, getWidth()/2  - (logoSprite.getWidth() / 2), getHeight()/2 - (logoSprite.getHeight() / 2), 1, 1, true);
        }

        if (currentScreen == 1) {
            renderer.renderSprite(titleSprite, title.getRectangle().x, title.getRectangle().y, 1, 1, true);
            renderer.renderSprite(level1Sprite, level1.getRectangle().x, level1.getRectangle().y, 1, 1, true);
            renderer.renderSprite(level2Sprite,  level2.getRectangle().x, level2.getRectangle().y, 1, 1, true);
            renderer.renderSprite(editSprite,  edit.getRectangle().x, edit.getRectangle().y, 1, 1, true);
        }

        if(currentScreen == 2)
            map.render(renderer, objects, xZoom, yZoom, editMode);

        if(currentScreen == 3)
            map2.render(renderer, objects, xZoom, yZoom, editMode);


        renderer.render(graphics);

        graphics.dispose();
        bufferStrategy.show();
        renderer.clear();

    }

    public void changeTile(int tileID)
    {
        selectedTileID = tileID;
    }

    public int getSelectedTile()
    {
        return selectedTileID;
    }

    public void run()
    {
        long lastTime = System.nanoTime(); //long 2^63
        double nanoSecondConversion = 1000000000.0 / 60; //60 frames per second
        double changeInSeconds = 0;

        while(true)
        {
            long now = System.nanoTime();

            changeInSeconds += (now - lastTime) / nanoSecondConversion;
            while(changeInSeconds >= 1) {
                update();
                changeInSeconds--;
            }

            render();
            lastTime = now;
        }

    }

    /*********************************************************************/
    /** Centro Universitario Senac **/
    /** Tecnologia em Jogos Digitais - 1o semestre de 2019 **/
    /** <Professor Bruno Sanches> **/
    /** **/
    /** Projeto Integrador III - Projeto Final **/
    /** Arquivo: <PI-3 GuilhermeMacedo-PeterDeNardo> **/
    /** Alunos: **/
    /** <Guilherme Oliveira Macedo> **/
    /** <Peter De Nardo Muller> **/
    /** Referencia de codigo: https://www.youtube.com/watch?v=lDzKX3djE-M**/
    /** <Apresentado dia 13/06/2019> **/
    /*********************************************************************/
    public static void main(String[] args)
    {
        Game game = new Game();
        Thread gameThread = new Thread(game);
        gameThread.start();
    }

    public KeyBoardListener getKeyListener()
    {
        return keyListener;
    }

    public MouseEventListener getMouseListener()
    {
        return mouseListener;
    }

    public RenderHandler getRenderer()
    {
        return renderer;
    }

    public Map getMap() {
        return map;
    }

    public int getXZoom() {
        return xZoom;
    }

    public int getYZoom() {
        return yZoom;
    }
}