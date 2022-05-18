import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
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
    private JPanel buttonPanel;
    private JButton keyButton;
    private JButton removeButton;
    private KeyManager manager;
    private Map<Character, KeyLabel> keyLabelMap;

    private long startTime;
    private BufferedImage buttonUp;
    private BufferedImage buttonDown;

    private long lastPressTime;

    private KeyGraph kg;
    private ArrayList<Character> keyOrder;


    public ProgramWindow(){ // kps is still lacking, need to only consider a current time frame
                            // i.e. 1 session, where the kps is tracked
        startTime = new Date().getTime();
        buttonPanel = new JPanel();
        keyButton = new JButton();
        Key.buildNativeKeyMap();
        keyOrder = new ArrayList<>();
        keyLabelMap = new HashMap<>();
        p = new JPanel();
        infoPanel = new JPanel();
        removeButton = new JButton();


        try {
           buttonUp = ImageIO.read(new File("assets/button_up.png"));
           buttonDown = ImageIO.read(new File("assets/button_down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }



      //  infoPanel.setLayout(new BorderLayout());
        j = new JFrame("javaKPS");
        j.setLayout(new BorderLayout());
        manager = new KeyManager();


        // read from config.cfg file here

        String fileName = "src/config.cfg";
        File cfgFile = new File(fileName);
        try {
            if (cfgFile.createNewFile()){

            }
            else
            {
                // MAKE IT CHECK IF EMPTY
                if(cfgFile.length() != 0) {
                    Scanner sc = new Scanner(cfgFile);
                    String temp = sc.nextLine();
                    String[] keys = temp.split(";;");
                    for (String k : keys) {
                        char c = Character.toUpperCase(k.charAt(0));
                        addKey(c);
                    }
                    sc.close();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


     /*   addKey('S');
        addKey('D');
        addKey('K');
        addKey('L');*/

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
        j.add(buttonPanel,BorderLayout.NORTH);
        buttonPanel.add(keyButton);
        buttonPanel.add(removeButton);

        keyButton.setText("Add a Key");
        keyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame keyFrame = new JFrame("Add a Key");
                keyFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JLabel l = new JLabel("Press a key to add it");
                keyFrame.add(new JPanel().add(l));
                keyFrame.setSize(300, 200);
                keyFrame.setLocationRelativeTo(null);
                NativeKeyListener tempListen = new NativeKeyListener() {

                    @Override
                    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
                        int key = nativeKeyEvent.getKeyCode();
                        Character c = Key.NativeKeyMap.get(key);
                        if(addKey(c))
                        {
                            keyFrame.dispose();
                            GlobalScreen.removeNativeKeyListener(this);
                        }
                        else
                        {
                            l.setText("Already added or bad key");
                        }
                    }
                    @Override
                    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

                    @Override
                    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}
                };

                GlobalScreen.addNativeKeyListener(tempListen);

                keyFrame.setVisible(true);
            }
        });

        removeButton.setText("Remove a Key");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame keyFrame = new JFrame("Remove a Key");
                keyFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                JLabel l = new JLabel("Press a key to remove it");
                keyFrame.add(new JPanel().add(l));
                keyFrame.setSize(300, 200);
                keyFrame.setLocationRelativeTo(null);


                NativeKeyListener tempListen = new NativeKeyListener() {

                    @Override
                    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
                        int key = nativeKeyEvent.getKeyCode();
                        Character c = Key.NativeKeyMap.get(key);
                        if(removeKey(c))
                        {
                            keyFrame.dispose();
                            GlobalScreen.removeNativeKeyListener(this);
                        }
                        else
                        {
                            l.setText("Not a key being tracked or bad key");
                        }
                    }
                    @Override
                    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

                    @Override
                    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}
                };
                GlobalScreen.addNativeKeyListener(tempListen);

                keyFrame.setVisible(true);
            }
        });

        p.setLocation(20,120);


        j.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                closed = true;
                //System.out.println("closing");
            }
        });

        j.pack();
        j.setVisible(true);


        while(!closed)
        {
            long curTime = new Date().getTime();
            manager.updateTime(curTime - startTime);
            updateLabel();
        }
        System.out.println("closcd"); // save to file here
        try {
            FileWriter write = new FileWriter(cfgFile);
            for(Character k:keyOrder)
            {
                write.write(k + ";;");
            }
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }

    private void updateLabel()
    {
        infoLabel.setText("<html>KPS:" + (int)manager.getKps() + "<br>" +
                "BPM:" + manager.getBpm() + "<br>" +
                "TOTAL KEYS:" + manager.getTotalPresses()+ "<br>" +
                "</html>");
    }

    private boolean removeKey(char c)
    {
        c = Character.toUpperCase(c);
        for(int i = 0; i < keyOrder.size();i++)
        {
            if(keyOrder.get(i) == c)
            {
                keyOrder.remove(i);
                KeyLabel kl = keyLabelMap.remove(c);
                manager.removeKey(c);
                p.remove(kl);
                j.invalidate();
                j.validate();
                j.repaint();

                j.pack();
                return true;
            }
        }
        return false;
    }

    private boolean addKey(char c)
    {
        c = Character.toUpperCase(c);
        //System.out.println(c);
        Key key = manager.addKey(c);
        if(!keyLabelMap.containsKey(c)) {
            KeyLabel kl = new KeyLabel(new ImageIcon(buttonUp), key);
            kl.setForeground(Color.WHITE);
            keyLabelMap.put(c, kl);
            p.add(kl);
            keyOrder.add(c);
            j.pack();
            return true;
        }
        return false;
    }
    public class KeyTracker implements NativeKeyListener {


        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {


                int key = e.getKeyCode();
                Character c = Key.NativeKeyMap.get(key);

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
            Character c = Key.NativeKeyMap.get(key);

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

