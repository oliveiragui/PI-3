import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Jogo extends JPanel implements KeyListener {
    private boolean jogoAtivo; // variável de controle para game Loop

    public Jogo(){

        JFrame frame = new JFrame("Ola Mundo Grafico");
        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null); // coloca o JFrame no centro da tela frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setResizable(false);//desabilita maximizar janela
        frame.setVisible(true);
        frame.add(this); // adiciona o JPanel na janela
        frame.addKeyListener(this); // registra o JPanel na lista de eventos a serem repassados setFocusable(true); // permite o JPanel receber o os eventos
        this.setBackground( Color.white ); // muda cor de fundo do JPanel
        jogoAtivo = true;

    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(750, 550, 50, 50); g.setColor(Color.white);
        g.fillRect(0, 0, 500, 500); g.setColor(Color.darkGray);
        g.fillOval(150, 200, 200, 40); g.setColor(Color.red);
        g.fillOval(100, 80, 150, 150); g.setColor(Color.pink);
        g.fillOval(120, 105, 30, 30); g.setColor(Color.blue);
        g.setFont(new Font("Arial Bold", Font.PLAIN, 36)); g.drawString("Projeto Integrador III", 30, 450);
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

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        System.out.println("Pressionei a tecla:"+e. getKeyChar());

    }

    @Override
    public void keyReleased(KeyEvent e) {

        System.out.println("Liberei a tecla:"+e.getKeyChar());

    }

}

