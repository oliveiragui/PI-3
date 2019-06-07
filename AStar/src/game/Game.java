package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JPanel;

import game.astar.Map;
import game.astar.Node;
import game.entity.Player;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends JPanel implements MouseListener {

    private Map map;
    private Player player, playerAzul;
    private Player[] playerEnemy;
    private List<Node> path, path2, pathEnemy;

    private int delay = 15000;
    private int interval = 100000;
    private TimerTask task;

    private Timer timer = new Timer();
    private Random rand = new Random();
    private int[] posX = {2, 20, 20, 2};
    private int[] posY = {2, 2, 10, 10};

    int[][] m0 = { //
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, //
        {1, 0, 1, 0, 1, 0, 1, 0, 0, 0, 0, 0, 1}, //
        {1, 0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1}, //
        {1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1}, //
        {1, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1}, //
        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1}, //
        {1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1}, //
        {1, 0, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1}, //
        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1}, //
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

    int[][] m1 = { //
        {0, 0, 0, 0, 0, 0, 0}, //
        {0, 0, 0, 1, 0, 0, 0}, //
        {0, 0, 0, 1, 0, 0, 0}, //
        {0, 0, 0, 1, 0, 0, 0}, //
        {0, 0, 0, 0, 0, 0, 0}};

    int[][] m2 = {
        {0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 1, 1, 1, 1, 1, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0}
    };

    int[][] m3 = {
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},};

    public Game() {
        // Change this to whatever map you want, and feel free to add more.
        //int[][] m = m0;
//		int[][] m = m1;
        int[][] m = m3;

        setPreferredSize(new Dimension(m[0].length * 32, m.length * 32));
        addMouseListener(this);

        map = new Map(m);
        player = new Player(19, 11);
        playerAzul = new Player(5, 4);
        
        playerEnemy = new Player[10];
        
         for (int i = 0; i < playerEnemy.length; i++) {
            int n = rand.nextInt(4);
            playerEnemy[i] = new Player(posX[n], posY[n]);
        
//        timer.scheduleAtFixedRate(new TimerTask() {
//            public void run() {
//                for (int i = 0; i < playerEnemy.length; i++) {
//            int n = rand.nextInt(4);
//            playerEnemy[i] = new Player(posX[n], posY[n]);
//
//        }
//
//               
//            }
//        }, delay, interval);

//        for (int i = 0; i < playerEnemy.length; i++) {
//            int n = rand.nextInt(4);
//            playerEnemy[i] = new Player(posX[n], posY[n]);
//
       }
    }

    public void update(){
            player.update();

//        playerAzul.update();

        for (int i = 0; i < playerEnemy.length; i++) {
            playerEnemy[i].update();

        }

    }

    public void render(Graphics2D g) throws IOException {
        map.drawMap(g, path, path2, pathEnemy);
        g.setColor(Color.GRAY);
        for (int x = 0; x < getWidth(); x += 32) {
            g.drawLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += 32) {
            g.drawLine(0, y, getWidth(), y);
        }

        g.setColor(Color.RED);
        g.fillRect(player.getX() * 32 + player.getSx(), player.getY() * 32 + player.getSy(), 32, 32);

//        g.setColor(Color.BLUE);
//        g.fillRect(playerAzul.getX() * 32 + playerAzul.getSx(), playerAzul.getY() * 32 + playerAzul.getSy(), 32, 32);


        for (int i = 0; i < playerEnemy.length; i++) {
            g.setColor(Color.BLACK);
            g.fillRect(playerEnemy[i].getX() * 32 + playerEnemy[i].getSx(), playerEnemy[i].getY() * 32 + playerEnemy[i].getSy(), 32, 32);
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int mx = e.getX() / 32;
        int my = e.getY() / 32;
        if (map.getNode(mx, my).isWalkable()) {

//            path2 = map.findPath(playerAzul.getX(), playerAzul.getY(), player.getX(), player.getY());
//            playerAzul.followPath(path2);
            path = map.findPath(player.getX(), player.getY(), player.getX(), player.getY());
            //player.followPath(path);

            for (int i = 0; i < playerEnemy.length; i++) {
                pathEnemy = map.findPath(playerEnemy[i].getX(), playerEnemy[i].getY(), player.getX(), player.getY());
                playerEnemy[i].followPath(pathEnemy);
            }

        } else {
            System.out.println("Can't walk to that node!");
        }
        map.printMap(path);
        System.out.println("====================================");
        map.printMap(pathEnemy);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
