package swing;

import java.util.ArrayList;

public class Level {

    private int id;
    private ArrayList<Tile> tiles = new ArrayList<>();

    public Level(int id) {
        this.id = id;
    }

    public ArrayList<Tile> getTiles() {
        //if (this.id == 1){
            tiles.add(new Tile(10,10, "src/resources/tileWall.png"));
            tiles.add(new Tile(10,50, "src/resources/tileWall.png"));
            tiles.add(new Tile(10,90, "src/resources/tileWall.png"));
            tiles.add(new Tile(10,130, "src/resources/tileWall.png"));
            tiles.add(new Tile(10,170, "src/resources/tileWall.png"));

        //}


        return tiles;
    }
}
