package swing;

import java.awt.*;

public class Tile extends Sprite {

    //private int[] tilePositions;

    public Tile(int x, int y, String imageSrc) {
        super(x, y);
        initTile(imageSrc);
    }

    private void initTile(String imageSrc) {

        loadImage(imageSrc);
        getImageDimensions();
    }
}
