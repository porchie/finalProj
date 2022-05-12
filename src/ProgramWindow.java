import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private JPanel p; //test
    private KeyManager manager;
    private Map<Character, KeyLabel> keyLabelMap;

    private BufferedImage buttonUp;
    private BufferedImage buttonDown;


    public ProgramWindow(){
        Key.buildKeyMap();

        try {
           buttonUp = ImageIO.read(new File("assets/button_up.png"));
           buttonDown = ImageIO.read(new File("assets/button_down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        keyLabelMap = new HashMap<>();

        p = new JPanel();
        j = new JFrame();
        manager = new KeyManager();




        addKey('S');
        addKey('D');
        addKey('F');
        addKey('K');
        addKey('L');

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


        j.add(p);
        p.setLocation(20,120);
        p.setSize(50,50);
        j.setVisible(true);
    }

    private void addKey(char c)
    {
        c = Character.toUpperCase(c);
        Key k = manager.addKey(c);
        if(!keyLabelMap.containsKey(c)) {
            KeyLabel kl = new KeyLabel(new ImageIcon(buttonUp), k);
            kl.setForeground(Color.WHITE);
            keyLabelMap.put(c, kl);
            p.add(kl);
        }
    }
    public class KeyTracker implements NativeKeyListener {


        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {

                int key = e.getKeyCode();
                Character c = Key.keyMap.get(key);

                KeyLabel kl = keyLabelMap.get(c);
                if(kl != null)
                {
                    kl.setIcon(new ImageIcon(buttonDown));
                }

        }


        @Override
        public void nativeKeyReleased(NativeKeyEvent e) {
            int key = e.getKeyCode();
            Character c = Key.keyMap.get(key);

            KeyLabel kl = keyLabelMap.get(c);

            if(kl != null)
            {
                manager.pressKey(c);
                kl.setText(manager.getKeyInfo(c));
                kl.setIcon(new ImageIcon(buttonUp));
            }

        }

        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            //System.out.println("typed");

        }

    }
}

