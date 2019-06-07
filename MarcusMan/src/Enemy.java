import astar.Node;

import java.util.LinkedList;
import java.util.List;

public class Enemy implements GameObject
{
    private Rectangle enemyRectangle;
    private Rectangle collisionCheckRectangle;
    private int speed;

    //0 = Right, 1 = Left, 2 = Up, 3 = Down
    private int direction = 0;
    private int layer = 0;
    private Sprite sprite;
    private AnimatedSprite animatedSprite = null;


    private boolean walking;
    private boolean fixing;
    private List<Node> path;

    private int sx;
    private int sy;

    public Enemy(Sprite sprite, int xZoom, int yZoom)
    {
        this.sprite = sprite;

        if(sprite != null && sprite instanceof AnimatedSprite)
            animatedSprite = (AnimatedSprite) sprite;

        updateDirection();
        enemyRectangle = new Rectangle(-90, -150, 20, 26);
        enemyRectangle.generateGraphics(3, 0xFF00FF90);
        collisionCheckRectangle = new Rectangle(0, 0, 10*xZoom, 15*yZoom);

        sx = 0;
        sy = 0;
        speed = 2;
    }

    private void updateDirection()
    {
        if(animatedSprite != null)
        {
            animatedSprite.setAnimationRange(direction * 8, (direction * 8) + 7);
        }
    }

    //Call every time physically possible.
    public void render(RenderHandler renderer, int xZoom, int yZoom)
    {
        if(animatedSprite != null)
            renderer.renderSprite(animatedSprite, enemyRectangle.x, enemyRectangle.y, xZoom, yZoom, false);
        else if(sprite != null)
            renderer.renderSprite(sprite, enemyRectangle.x, enemyRectangle.y, xZoom, yZoom, false);
        else
            renderer.renderRectangle(enemyRectangle, xZoom, yZoom, false);

    }

    //Call at 60 fps rate.
    public void update(Game game)
    {
        if (fixing)
        {
            fix();
        }
        if (walking)
        {
            walk();
        }


            animatedSprite.update(game);

        //updateCamera(game.getRenderer().getCamera());
    }

    public void followPath(List<Node> path)
    {
        this.path = path;
        if (walking)
        {
            fixing = true;
            walking = false;
        }
        else
        {
            walking = true;
        }
    }

    private void fix()
    {
        if (sx > 0)
        {
            sx -= speed;
            if (sx < 0)
            {
                sx = 0;
            }
        }
        if (sx < 0)
        {
            sx += speed;
            if (sx > 0)
            {
                sx = 0;
            }
        }
        if (sy > 0)
        {
            sy -= speed;
            if (sy < 0)
            {
                sy = 0;
            }
        }
        if (sy < 0)
        {
            sy += speed;
            if (sy > 0)
            {
                sy = 0;
            }
        }
        if (sx == 0 && sy == 0)
        {
            fixing = false;
            walking = true;
        }
    }

    private void walk()
    {
        if (path == null)
        {
            walking = false;
            return;
        }
        if (path.size() <= 0)
        {
            walking = false;
            path = null;
            return;
        }
        Node next = ((LinkedList<Node>) path).getFirst();
        if (next.getX() != this.enemyRectangle.x)
        {
            sx += (next.getX() < this.enemyRectangle.x ? -speed : speed);
            if (sx % 32 == 0)
            {
                ((LinkedList<Node>) path).removeFirst();
                if (sx > 0)
                    this.enemyRectangle.x++;
                else
                    this.enemyRectangle.x--;
                sx %= 32;
            }
        }
        else if (next.getY() != this.enemyRectangle.y)
        {
            sy += (next.getY() < this.enemyRectangle.y ? -speed : speed);
            if (sy % 32 == 0)
            {
                ((LinkedList<Node>) path).removeFirst();
                if (sy > 0)
                    this.enemyRectangle.y++;
                else
                    this.enemyRectangle.y--;
                sy %= 32;
            }
        }
    }

    public int getLayer() {
        return layer;
    }

    public Rectangle getRectangle() {
        return enemyRectangle;
    }

    //Call whenever mouse is clicked on Canvas.
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) { return false; }
}