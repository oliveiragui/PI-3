import java.awt.image.BufferedImage;

public class SpriteSheet
{
    /**
     * Array de pixels da spritesheet
     */
    private int[] pixels;

    /**
     * Imagem da spriteSheet a conter os frames de animação
     */
    private BufferedImage image;

    /**
     * Largura da imagem
     */
    public final int SIZEX;

    /**
     * Altura da imagem
     */
    public final int SIZEY;

    /**
     * Carrega os sprites baseado no tamanho de cada animação e guarda nesse Array
     */
    private Sprite[] loadedSprites = null;

    /**
     * sempre false caso o metodo loadSprites não tenha sido chamado, e vice versa
     */
    private boolean spritesLoaded = false;

    /**
     * tamanho do sprite em largura, pois os sprites são armazenados em arrays horizontais
     */
    private int spriteSizeX;

    public SpriteSheet(BufferedImage sheetImage)
    {
        image = sheetImage;
        SIZEX = sheetImage.getWidth();
        SIZEY = sheetImage.getHeight();

        pixels = new int[SIZEX*SIZEY];
        pixels = sheetImage.getRGB(0, 0, SIZEX, SIZEY, pixels, 0, SIZEX);
    }

    /**
     * tamanho do sprite em largura, pois os sprites são armazenados em arrays horizontais
     */
    public void loadSprites(int spriteSizeX, int spriteSizeY)
    {
        this.spriteSizeX = spriteSizeX;
        loadedSprites = new Sprite[(SIZEX / spriteSizeX) * (SIZEY / spriteSizeY)];

        int spriteID = 0;
        for(int y = 0; y < SIZEY; y += spriteSizeY)
        {
            for(int x = 0; x < SIZEX; x += spriteSizeX)
            {
                loadedSprites[spriteID] = new Sprite(this, x, y, spriteSizeX, spriteSizeY);
                spriteID++;
            }
        }

        spritesLoaded = true;
    }

    /**
     * retorna um sprite na posição x,y do spriteSheet
     */
    public Sprite getSprite(int x, int y)
    {
        if(spritesLoaded)
        {
            int spriteID = x + y * (SIZEX / spriteSizeX);

            if(spriteID < loadedSprites.length)
                return loadedSprites[spriteID];
            else
                System.out.println("SpriteID " + spriteID + " fora do escopo de " + loadedSprites.length + ".");
        }
        else
            System.out.println("O spritesheet nao contem sprites carregados.");

        return null;
    }

    public Sprite[] getLoadedSprites()
    {
        return loadedSprites;
    }

    public int[] getPixels()
    {
        return pixels;
    }

    public BufferedImage getImage()
    {
        return image;
    }

}