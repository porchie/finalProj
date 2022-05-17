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


    private boolean closed;
    private JLabel infoLabel;
    private JPanel infoPanel;
    private KeyManager manager;
    private Map<Character, KeyLabel> keyLabelMap;

    private long startTime;
    private BufferedImage buttonUp;
    private BufferedImage buttonDown;

    private long lastPressTime;

    private KeyGraph kg;
    private ArrayList<Character> keyOrder;


    public ProgramWindow(){ // kps is still lacking, need to only consider a current time frame
                            // i.e. 1 session, where the kps is tracked, not fucking everything argh.
        startTime = new Date().getTime();
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
        infoPanel = new JPanel();

      //  infoPanel.setLayout(new BorderLayout());
        j = new JFrame("javaKPS");
        j.setLayout(new BorderLayout());
        manager = new KeyManager();

        addKey('S');
        addKey('D');
        addKey('F');
        addKey('K');
        addKey('L');

        infoPanel.setPreferredSize(new Dimension(100,100));
        infoLabel = new JLabel("<html>KPS:<br>BPM:<br>TOTAL KEYS:</html>",SwingConstants.CENTER);
        infoPanel.add(infoLabel, BorderLayout.PAGE_END);

        infoLabel.setHorizontalTextPosition(JLabel.CENTER);
        infoLabel.setVerticalTextPosition(JLabel.CENTER);


        j.setSize(1000, 330);
        j.setLocationRelativeTo(null);
        j.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



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
        j.add(infoPanel, BorderLayout.SOUTH);
        p.setLocation(20,120);
       // p.setSize(50,50);

        j.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                closed = true;
                System.out.println("closing");
            }
        });


        j.setVisible(true);


        while(!closed)
        {
            long curTime = new Date().getTime();
            manager.updateTime(curTime - startTime);
            updateLabel();
        }
        System.out.println("closcd");

        System.exit(0);
    }

    private void updateLabel()
    {
        infoLabel.setText("<html>KPS:" + (int)manager.getKps() + "<br>" +
                "BPM:" + manager.getBpm() + "<br>" +
                "TOTAL KEYS:" + manager.getTotalPresses()+ "<br>" +
                manager.getTime() +"</html>");
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
                    lastPressTime = new Date().getTime();
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
                //updateLabel();
            }
        }

        @Override
        public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
            //System.out.println("typed");

        }

    }
}

