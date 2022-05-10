import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window extends JFrame {
    // button for adding a key
    // button for calculating UR(unstable rate)
    // graph to display UR
    // 
    
    private JFrame j;
    private JLabel label; //test
    private JPanel p; //test
    private JLabel label2; //test
    private JPanel p2; //test
    private KeyManager manager;
    public Window(){
        j = new JFrame();
        manager = new KeyManager();
        manager.addKey('S');
        manager.addKey('D');
        j.setSize(500, 330);
        j.setLocation(5, 5);
        j.addKeyListener(new KeyTracker());

        p = new JPanel();
        label = new JLabel();
        p2 = new JPanel();
        label2 = new JLabel();
        j.add(p);
        p.setLocation(20,120);
        p.setSize(50,50);
        p.add(label);

        j.add(p2);
        p2.add(label2);
        p2.setLocation(120,120);
        p2.setSize(50,50);


        j.setVisible(true);
    }
    public class KeyTracker extends KeyAdapter { // better key detection + no hold blocking
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();

            if (key == KeyEvent.VK_S) {
                manager.pressKey('S');
                label.setText(manager.getKeyInfo('S'));
                System.out.println(manager.getKeyInfo('S'));
            }
            if (key == KeyEvent.VK_D) {
                manager.pressKey('D');
                label2.setText(manager.getKeyInfo('D'));
                System.out.println(manager.getKeyInfo('D'));
            }


        }
    }
}

