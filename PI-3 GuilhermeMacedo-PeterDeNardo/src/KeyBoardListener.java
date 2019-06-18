import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.FocusListener;
import java.awt.event.FocusEvent;

public class KeyBoardListener implements KeyListener, FocusListener
{
    /**
     * O atributo keys contém o limite de teclas que podem ser pressionadas
     */
    public boolean[] keys = new boolean[120];

    private Game game;

    public KeyBoardListener(Game game)
    {
        this.game = game;
    }

    /**
     * Invoca a cada pressionar de tecla,
     * atualiza no array keys a tecla como true/false
     *
     * Quando editando o mapa, chama a função do objeto Game handleCTRL, que
     * permite salvar cada tile em uma posição e adicionar no arquivo Map.txt
     */
    public void keyPressed(KeyEvent event)
    {

        int keyCode = event.getKeyCode();

        if(keyCode < keys.length)
            keys[keyCode] = true;

        if(keys[KeyEvent.VK_CONTROL]) {
            game.handleCTRL(keys);
        }

        // Apertar ESC sai da tela do level e volta pro menu
        if(game.currentScreen == 2 || game.currentScreen == 3) {
            if (keys[KeyEvent.VK_ESCAPE])
                game.currentScreen = 1;
        }

    }

    /**
     * Invoca quando solta alguma tecla,
     * atualiza no array keys a tecla como true/false
     *
     */
    public void keyReleased(KeyEvent event)
    {
        int keyCode = event.getKeyCode();

        if(keyCode < keys.length)
            keys[keyCode] = false;
    }

    /**
     * Não permite que ative os eventos caso o foco na janela do jogo seja perdido
     */
    public void focusLost(FocusEvent event)
    {
        for(int i = 0; i < keys.length; i++)
            keys[i] = false;
    }

    public void keyTyped(KeyEvent event) {}

    public void focusGained(FocusEvent event) {}

    /**
     * Retorna true/false para as teclas Up/W
     */
    public boolean up()
    {
        return keys[KeyEvent.VK_W] || keys[KeyEvent.VK_UP];
    }

    /**
     * Retorna true/false para as teclas Down/S
     */
    public boolean down()
    {
        return keys[KeyEvent.VK_S] || keys[KeyEvent.VK_DOWN];
    }

    /**
     * Retorna true/false para as teclas Left/A
     */
    public boolean left()
    {
        return keys[KeyEvent.VK_A] || keys[KeyEvent.VK_LEFT];
    }

    /**
     * Retorna true/false para as teclas Right/D
     */
    public boolean right()
    {
        return keys[KeyEvent.VK_D] || keys[KeyEvent.VK_RIGHT];
    }

}