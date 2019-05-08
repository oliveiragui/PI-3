package swing;

import java.util.ArrayList;

public class Level {

    private int id;
    private final int pixelsMultiplier = 32;
    private Tile[][] tiles;

    private TileSet tileSet;

    public Level(int id) {

        this.id = id;
        this.tileSet = new TileSet(id);
        this.tiles = new Tile[this.tileSet.getTilePositions().length][this.tileSet.getTilePositions().length];
        initTiles();
    }

    public void initTiles() {
        int[][] tilePos = tileSet.getTilePositions();
        for (int i = 0; i < tilePos.length; i++) {
            for (int j = 0; j < tilePos.length; j++) {
                String imageSource =
                        tilePos[i][j] == 1
                        ? "src/resources/tileWall.png"
                        : tilePos[i][j] == 2
                        ? "src/resources/tileGrass.png"
                        : "";

                tiles[i][j] = new Tile(j*this.pixelsMultiplier,i*this.pixelsMultiplier, imageSource);
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }
}
