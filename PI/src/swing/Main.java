package swing;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Janela janela = new Janela();
            janela.setVisible(true);
        });
    }
}
