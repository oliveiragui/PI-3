import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

public class Tiles
{
    /**
     * Objeto Spritesheet que contém os tiles
     */
    private SpriteSheet spriteSheet;

    /**
     * Arraylist de tiles que foram encontrados no spriteSheet
     */
    private ArrayList<Tile> tilesList = new ArrayList<Tile>();


    /**
     * Esse método só funciona caso o spriteSheet tenha sido carregado corretamente
     *
     * @param tilesFile arquivo de texto contendo a informação de cada posição de um tile
     *
     * @param spriteSheet instancia da spriteSheet carregada com a imagem
     */
    public Tiles(File tilesFile, SpriteSheet spriteSheet)
    {
        this.spriteSheet = spriteSheet;
        try
        {
            Scanner scanner = new Scanner(tilesFile);
            while(scanner.hasNextLine())
            {
                String line = scanner.nextLine();
                if(!line.startsWith("//"))
                {
                    String[] splitString = line.split("-");
                    String tileName = splitString[0];
                    int spriteX = Integer.parseInt(splitString[1]);
                    int spriteY = Integer.parseInt(splitString[2]);
                    Tile tile = new Tile(tileName, spriteSheet.getSprite(spriteX, spriteY));

                    if(splitString.length >= 4) {
                        tile.collidable = true;
                        tile.collisionType = Integer.parseInt(splitString[3]);
                    }

                    tilesList.add(tile);
                }
            }
        }
        catch(FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Renderiza os tiles na tela
     */
    public void renderTile(int tileID, RenderHandler renderer, int xPosition, int yPosition, int xZoom, int yZoom)
    {
        if(tileID >= 0 && tilesList.size() > tileID)
        {
            renderer.renderSprite(tilesList.get(tileID).sprite, xPosition, yPosition, xZoom, yZoom, false);
        }
        else
        {
            System.out.println("TileID " + tileID + " fora do escopo " + tilesList.size() + ".");
        }
    }

    /**
     * @return o tamanho de tiles presentes no jogo
     */
    public int size()
    {
        return tilesList.size();
    }

    /**
     * @return um Array de sprites com cada sprite rendereizado
     */
    public Sprite[] getSprites()
    {
        Sprite[] sprites = new Sprite[size()];

        for(int i = 0; i < sprites.length; i++)
            sprites[i] = tilesList.get(i).sprite;

        return sprites;
    }

    /**
     * @return o tipo de colisão baseado no arquivo Tiles.txt
     */
    public int collisionType(int tileID)
    {
        if(tileID >= 0 && tilesList.size() > tileID)
        {
            return tilesList.get(tileID).collisionType;
        }
        else
        {
            System.out.println("TileID " + tileID + " fora do escopo " + tilesList.size() + ".");
        }
        return -1;
    }

    /**
     * Struct Tile
     *
     * Controla as informações gerais de um unico tile
     */
    class Tile
    {
        public String tileName;
        public Sprite sprite;
        public boolean collidable = false;
        public int collisionType = -1;

        public Tile(String tileName, Sprite sprite)
        {
            this.tileName = tileName;
            this.sprite = sprite;
        }
    }
}