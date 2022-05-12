import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.*;



public class ProgramWindow extends JFrame {
    // button for adding a key
    // button for calculating UR(unstable rate)
    // graph to display UR
    // 


    private JFrame j;
    private JLabel label; //test
    private Map<JPanel,Key> KeyToLabelDisplay;
    private JPanel p; //test
    private JLabel label2; //test
    private JPanel p2; //test
    private KeyManager manager;


    public ProgramWindow(){
        Key.buildKeyMap();

        j = new JFrame();
        manager = new KeyManager();
        manager.addKey('S');
        manager.addKey('D');
        j.setSize(500, 330);
        j.setLocation(5, 5);


        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try { // global screen
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new KeyTracker());


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
    public class KeyTracker implements NativeKeyListener { // need even not active window detect keys


        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {

                int key = e.getKeyCode();
           // System.out.println("pressed");


                if (key == NativeKeyEvent.VC_S) {
                    manager.pressKey('S');
                    label.setText(manager.getKeyInfo('S'));
                    //System.out.println(manager.getKeyInfo('S'));
                }
                if (key == NativeKeyEvent.VC_D) {
                    manager.pressKey('D');
                    label2.setText(manager.getKeyInfo('D'));
                    //System.out.println(manager.getKeyInfo('D'));
                }

        }


        @Override
        public void nativeKeyReleased(NativeKeyEvent e) {
            int key = e.getKeyCode();
            //System.out.println("key released");
        }

        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            //System.out.println("typed");

        }

    }
}

