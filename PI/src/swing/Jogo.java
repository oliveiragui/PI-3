package swing;

import swing.Player;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Jogo extends JPanel implements ActionListener {

    private final int ICRAFT_X = 40;
    private final int ICRAFT_Y = 60;
    private Timer timer;
    private boolean jogoAtivo; // variável de controle para game Loop
    private Player player;
    private final int DELAY = 10;
    private Level level;


    public Jogo(){

        initialize();
    }

    private void initialize() {

        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        player = new Player(ICRAFT_X, ICRAFT_Y);

        level = new Level(1);

        timer = new Timer(DELAY, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);

        Toolkit.getDefaultToolkit().sync();
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.drawImage(player.getImage(), player.getX(),
                player.getY(), this);


        for (int i = 0; i < level.getTiles().length; i++) {
            for (int j = 0; j < level.getTiles().length; j++) {
                g2d.drawImage(level.getTiles()[i][j].getImage(), level.getTiles()[i][j].getX(),
                        level.getTiles()[i][j].getY(), this);
            }
        }

        List<Missile> missiles = player.getMissiles();

        for (Missile missile : missiles) {

            g2d.drawImage(missile.getImage(), missile.getX(),
                    missile.getY(), this);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        updateMissiles();
        updatePlayer();
        checkCollisions();
        repaint();

    }

    private void updateMissiles() {

        List<Missile> missiles = player.getMissiles();

        for (int i = 0; i < missiles.size(); i++) {

            Missile missile = missiles.get(i);

            if (missile.isVisible()) {

                missile.move();
            } else {

                missiles.remove(i);
            }
        }
    }

    private void updatePlayer() {

        player.move();
    }



    public void checkCollisions() {

        Rectangle r3 = player.getBounds();

        for (int i = 0; i < level.getTiles().length; i++) {
            for (int j = 0; j < level.getTiles().length; j++) {
                Rectangle r2 = level.getTiles()[i][j].getBounds();

                if (r3.intersects(r2)) {
                    //spaceship.setVisible(false);
                    level.getTiles()[i][j].setVisible(false);

                    //ingame = false;
                }

            }
        }

    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }

    public void run() {
        while(jogoAtivo) {

            //desenha a tela repaint();
            // aguarda alguns milisegundos
            try {
            Thread.sleep(50);
            }
            catch(InterruptedException ex) {

            }
        }
    }

}

