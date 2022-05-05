import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameWindow extends JFrame {

    private JFrame j;
    public GameWindow(){
        j = new JFrame();
        j.setSize(500, 330);
        j.setLocation(5, 5);
        j.addKeyListener(new KeyTracker());
        j.setVisible(true);
    }
    public class KeyTracker extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_W)
            {
                System.out.println("w");
            }
            if (key == KeyEvent.VK_A)
            {
                System.out.println("a");
            }
            if (key == KeyEvent.VK_S)
            {
                System.out.println("s");
            }
            if (key == KeyEvent.VK_D)
            {
                System.out.println("d");
            }
        }
    }
}
