package swing;

import javax.swing.JFrame;

public class Janela extends JFrame {

    public Janela() {

        initialize();
    }

    private void initialize() {

        add(new Jogo());

        setTitle("Fish Miracle");
        setSize(400, 300);

        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
