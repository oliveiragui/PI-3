public class Enemy extends Character implements GameObject
{
    public Enemy(Sprite sprite, int xZoom, int yZoom, int xPos, int yPos)
    {
        super(sprite, xZoom, yZoom, xPos, yPos);
    }

    @Override
    public void updateCamera(Rectangle camera) {

    }
}