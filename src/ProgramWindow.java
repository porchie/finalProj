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
    private JPanel p;

    private JLabel infoLabel;
    private KeyManager manager;
    private Map<Character, KeyLabel> keyLabelMap;

    private BufferedImage buttonUp;
    private BufferedImage buttonDown;

    private KeyGraph kg;
    private ArrayList<Character> keyOrder;


    public ProgramWindow(){
        Key.buildKeyMap();
        keyOrder = new ArrayList<>();
        try {
           buttonUp = ImageIO.read(new File("assets/button_up.png"));
           buttonDown = ImageIO.read(new File("assets/button_down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        keyLabelMap = new HashMap<>();

        p = new JPanel();

        //infoLabel = new JLabel("KPS:\nBPM:\nTOTAL KEYS:",SwingConstants.CENTER);
        //p.setLayout(new BorderLayout());
        //p.add(infoLabel, BorderLayout.PAGE_END);
        j = new JFrame();
        manager = new KeyManager();


        addKey('S');
        addKey('D');
        addKey('F');
        addKey('K');
        addKey('L');

        j.setSize(1000, 330);
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

        //j.add(infoPanel);
        j.setVisible(true);
    }

    private void updateLabel()
    {
        infoLabel.setText("KPS:" + manager.getKps() + "\n" + "BPM:" + manager.getBpm() + "\n" + "TOTAL KEYS:" + manager.getTotalPresses());
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
            keyOrder.add(c);
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
                updateLabel();
            }

        }

        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            //System.out.println("typed");

        }

    }
}

