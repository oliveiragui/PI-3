import java.awt.event.MouseListener;

public class Menu implements GameObject {
    private Game game;
    private int menuId;
    private Rectangle menuRectangle;

    private int layer = 0;
    private Sprite sprite;

    public Menu (Sprite sprite, int x, int y, int menuId, Game game) {
        this.sprite = sprite;
        this.menuId = menuId;
        this.game = game;

        menuRectangle = new Rectangle(x, y, sprite.getWidth(), sprite.getHeight());
        menuRectangle.generateGraphics(3, 0xFF00FF90);
    }

    @Override
    public void render(RenderHandler renderer, int xZoom, int yZoom) {

    }

    @Override
    public void update(Game game) {
    }

    @Override
    public boolean handleMouseClick(Rectangle mouseRectangle, Rectangle camera, int xZoom, int yZoom) {
        if (mouseRectangle.intersects(menuRectangle)) {
            if (menuId == 0 && game.currentScreen == 0) {
                game.currentScreen = 1;
                System.out.println("1");
            }
            if (menuId == 1 && game.currentScreen == 1) {
                game.currentScreen = 2;
                System.out.println("2");
            }
            if (menuId == 2 && game.currentScreen == 1) {
                game.currentScreen = 3;
                System.out.println("3");
            }
            if (menuId == 3 && game.currentScreen == 1) {
                game.editMode = !game.editMode;
                System.out.println(game.editMode);
            }
        }
        return false;
    }

    @Override
    public int getLayer() {
        return layer;
    }

    @Override
    public Rectangle getRectangle() {
        return menuRectangle;
    }
}
