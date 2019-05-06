package swing;

import java.awt.*;

public class TileSet extends Sprite {

    private int[] tilePositions;

    public TileSet(Level level) {
        super();
        initTile();
    }

    private void initTile() {

        loadImage("src/resources/tileWall.png");
        getImageDimensions();
    }
}
