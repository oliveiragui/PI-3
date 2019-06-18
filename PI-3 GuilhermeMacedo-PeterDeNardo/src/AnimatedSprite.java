import java.awt.image.BufferedImage;

public class AnimatedSprite extends Sprite implements GameObject
{

    /**
     * Array que guarda a divisão da spritesheet/animações do sprite
     */
    private Sprite[] sprites;

    /**
     * Guarda a posição do sprite atual
     */
    private int currentSprite = 0;

    /**
     * Velocidade da animação da spritesheet
     */
    private int speed;

    /**
     * Contador para verificar se a animação está constante com as atualizações da tela
     */
    private int counter = 0;

    /**
     * Guarda a primeira posição da spritesheet que o objeto está
     */
    private int startSprite = 0;

    /**
     * Guarda a ultima posição da spritesheet que o objeto está
     */
    private int endSprite;

    /**
     * @param sheet recebe uma spritesheet com as animações
     * @param positions recebe as posições de cada frame da spritesheet
     * @param speed recebe a velocidade que atualiza os frames até a sprite mudar
     */
    public AnimatedSprite(SpriteSheet sheet, Rectangle[] positions, int speed)
    {
        sprites = new Sprite[positions.length];
        this.speed = speed;
        this.endSprite = positions.length - 1;

        for(int i = 0; i < positions.length; i++)
            sprites[i] = new Sprite(sheet, positions[i].x, positions[i].y, positions[i].w, positions[i].h);
    }

    /**
     * @param sheet recebe uma spritesheet com as animações
     * @param speed recebe a velocidade que atualiza os frames até a sprite mudar
     */
    public AnimatedSprite(SpriteSheet sheet, int speed)
    {
        sprites = sheet.getLoadedSprites();
        this.speed = speed;
        this.endSprite = sprites.length - 1;
    }

    /**
     * @param images recebe um array de BufferedImage que dita em qual posição os frames estão
     * @param speed recebe a velocidade que atualiza os frames até a sprite mudar
     */
    public AnimatedSprite(BufferedImage[] images, int speed)
    {
        sprites = new Sprite[images.length];
        this.speed = speed;
        this.startSprite = images.length - 1;

        for(int i = 0; i < images.length; i++)
            sprites[i] = new Sprite(images[i]);

    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {}

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }

    @Override
    public void update(Game game)
    {
        counter++;
        if(counter >= speed)
        {
            counter = 0;
            incrementSprite();
        }
    }

    /**
     * Invocado sempre que o sprite chega ao fim de uma animação
     */
    public void reset()
    {
        counter = 0;
        currentSprite = startSprite;
    }

    /**
     * @param startSprite indica qual o sprite inicial da spritesheet
     * @param endSprite indica qual o sprite final da spritesheet
     */
    public void setAnimationRange(int startSprite, int endSprite)
    {
        this.startSprite = startSprite;
        this.endSprite = endSprite;
        reset();
    }

    /**
     * @return Largura da sprite atual
     */
    public int getWidth()
    {
        return sprites[currentSprite].getWidth();
    }

    /**
     * @return Altura da sprite atual
     */
    public int getHeight()
    {
        return sprites[currentSprite].getHeight();
    }

    /**
     * @return Array de pixels da sprite atual
     */
    public int[] getPixels()
    {
        return sprites[currentSprite].getPixels();
    }

    /**
     * Invodado para mudar a sprite quando a velocidade está constante
     */
    public void incrementSprite()
    {
        currentSprite++;
        if(currentSprite >= endSprite)
            currentSprite = startSprite;
    }

    @Override
    public int getLayer() {
        System.out.println("Este metodo nao se aplica nesta classe");
        return -1;
    }

    @Override
    public Rectangle getRectangle() {
        System.out.println("Este metodo nao se aplica nesta classe");
        return null;
    }

}