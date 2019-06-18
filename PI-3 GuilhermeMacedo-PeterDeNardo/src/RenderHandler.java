import java.awt.Graphics;
import java.awt.GraphicsDevice;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 * A Classe RenderHandler lida com todo tipo de renderização
 * na tela do jogo, ela posiciona os pixels, a camera, o tamanho de
 * cada objeto presente na tela
 */

public class RenderHandler
{
    /**
     * O atributo view lida com o espaço da tela que pode ser desenhado
     */
    private BufferedImage view;

    /**
     * O atributo camera mantém a renderização focada em um ponto X,Y
     * o que modifica a atualização dos sprites e do player
     */
    private Rectangle camera;

    private int[] pixels;

    /**
     * Tamanho maximo da tela
     */
    private int maxScreenWidth, maxScreenHeight;

    public RenderHandler(int width, int height)
    {
        GraphicsDevice[] graphicsDevices = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

        for(int i = 0; i < graphicsDevices.length; i++) {
            if(maxScreenWidth < graphicsDevices[i].getDisplayMode().getWidth())
                maxScreenWidth = graphicsDevices[i].getDisplayMode().getWidth();

            if(maxScreenHeight < graphicsDevices[i].getDisplayMode().getHeight())
                maxScreenHeight = graphicsDevices[i].getDisplayMode().getHeight();
        }

        view = new BufferedImage(maxScreenWidth, maxScreenHeight, BufferedImage.TYPE_INT_RGB);

        camera = new Rectangle(0, 0, width, height);

        pixels = ((DataBufferInt) view.getRaster().getDataBuffer()).getData();

    }

    /**
     * Renderiza os pixels conforme o tamanho da tela
     */
    public void render(Graphics graphics)
    {
        graphics.drawImage(view.getSubimage(0, 0, camera.w, camera.h), 0, 0, camera.w, camera.h, null);
    }

    /**
     * Renderiza sprites na tela
     */
    public void renderSprite(Sprite sprite, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed) {
        renderArray(sprite.getPixels(), sprite.getWidth(), sprite.getHeight(), xPosition, yPosition, xZoom, yZoom, fixed);
    }

    public void renderRectangle(Rectangle rectangle, Rectangle offset, int xZoom, int yZoom, boolean fixed)
    {
        int[] rectanglePixels = rectangle.getPixels();
        if(rectanglePixels != null)
            renderArray(rectanglePixels, rectangle.w, rectangle.h, rectangle.x + offset.x, rectangle.y + offset.y, xZoom, yZoom, fixed);
    }

    public void renderArray(int[] renderPixels, int renderWidth, int renderHeight, int xPosition, int yPosition, int xZoom, int yZoom, boolean fixed)
    {
        for(int y = 0; y < renderHeight; y++)
            for(int x = 0; x < renderWidth; x++)
                for(int yZoomPosition = 0; yZoomPosition < yZoom; yZoomPosition++)
                    for(int xZoomPosition = 0; xZoomPosition < xZoom; xZoomPosition++)
                        setPixel(renderPixels[x + y * renderWidth], (x * xZoom) + xPosition + xZoomPosition, ((y * yZoom) + yPosition + yZoomPosition), fixed);
    }

    private void setPixel(int pixel, int x, int y, boolean fixed)
    {
        int pixelIndex = 0;
        if(!fixed)
        {
            if(x >= camera.x && y >= camera.y && x <= camera.x + camera.w && y <= camera.y + camera.h)
                pixelIndex = (x - camera.x) + (y - camera.y) * view.getWidth();
        }
        else
        {
            if(x >= 0 && y >= 0 && x <= camera.w && y <= camera.h)
                pixelIndex = x + y * view.getWidth();
        }

        if(pixels.length > pixelIndex && pixel != Game.alpha)
            pixels[pixelIndex] = pixel;
    }

    public Rectangle getCamera()
    {
        return camera;
    }

    public int getMaxWidth() {
        return maxScreenWidth;
    }

    public int getMaxHeight() {
        return maxScreenHeight;
    }

    public void clear()
    {
        for(int i = 0; i < pixels.length; i++)
            pixels[i] = 0;
    }

}