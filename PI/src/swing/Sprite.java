package swing;

import javax.swing.*;
import java.awt.*;

public class Sprite {

    protected int x;
    protected int y;
    int width;
    int height;
    protected boolean visible;
    protected Image image;

    protected int topI = x;
    protected int topF = x + width;
    protected int downI = y + height;
    protected int downF = downI + width;
    protected int leftI = y;
    protected int leftF = y + height;
    protected int rightI = x + width;
    protected int rightF = rightI + height;

    protected int midTop = y + (width/2);

    protected int midRight = x + width;


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Sprite(int x, int y) {

        this.x = x;
        this.y = y;
        visible = true;
    }

    public Sprite() {
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    protected void getImageDimensions() {

        setWidth(image.getWidth(null));
        setHeight(image.getHeight(null));
    }

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}