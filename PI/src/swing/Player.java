/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package swing;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Player extends Sprite {

    private int dx;
    private int dy;
    private List<Missile> missiles;
    private boolean collideTop;
    private boolean collideDown;
    private boolean collideLeft;
    private boolean collideRight;
    private boolean upPressed;
    private boolean downPressed;
    private boolean leftPressed;
    private boolean rightPressed;

    public boolean isUpPressed() {
        return upPressed;
    }

    public void setUpPressed(boolean upPressed) {
        this.upPressed = upPressed;
    }

    public boolean isDownPressed() {
        return downPressed;
    }

    public void setDownPressed(boolean downPressed) {
        this.downPressed = downPressed;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isCollideTop() {
        return collideTop;
    }

    public void setCollideTop(boolean collideTop) {
        this.collideTop = collideTop;
    }

    public boolean isCollideDown() {
        return collideDown;
    }

    public void setCollideDown(boolean collideDown) {
        this.collideDown = collideDown;
    }

    public boolean isCollideLeft() {
        return collideLeft;
    }

    public void setCollideLeft(boolean collideLeft) {
        this.collideLeft = collideLeft;
    }

    public boolean isCollideRight() {
        return collideRight;
    }

    public void setCollideRight(boolean collideRight) {
        this.collideRight = collideRight;
    }

    public Player(int x, int y) {
        super(x, y);

        initSpaceShip();
    }

    private void initSpaceShip() {

        missiles = new ArrayList<>();

        loadImage("src/resources/link-E.png");
        getImageDimensions();
    }

    public void move() {
        x += dx;
        y += dy;
    }

    public List<Missile> getMissiles() {
        return missiles;
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE) {
            fire();
        }

        if (key == KeyEvent.VK_LEFT && !isCollideLeft()) {
            System.out.println("Pressed Left");
            setLeftPressed(true);
            dx = -1;
        }

        if (key == KeyEvent.VK_RIGHT && !isCollideRight()) {
            System.out.println("Pressed Right");
            setRightPressed(true);
            dx = 1;
        }

        if (key == KeyEvent.VK_UP && !isCollideTop()) {
            System.out.println("Pressed Up");
            setUpPressed(true);
            dy = -1;
        }

        if (key == KeyEvent.VK_DOWN  && !isCollideDown()) {
            System.out.println("Pressed Down");
            setDownPressed(true);
            dy = 1;
        }
    }

    public void fire() {
        missiles.add(new Missile(x + width, y + height / 2));
    }

    public void stopDirection(String direction){
        if (direction.equals("left") || direction.equals("right")) {
            dx = 0;
        }
        if (direction.equals("up") || direction.equals("down")) {
            dy = 0;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            System.out.println("Released Left");
            setLeftPressed(false);
            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {
            System.out.println("Released Right");
            setRightPressed(false);
            dx = 0;
        }

        if (key == KeyEvent.VK_UP) {
            System.out.println("Released Up");
            setUpPressed(false);
            dy = 0;
        }

        if (key == KeyEvent.VK_DOWN) {
            System.out.println("Released Down");
            setDownPressed(false);
            dy = 0;
        }
    }
}