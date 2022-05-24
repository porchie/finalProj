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



public class KpsWindow extends JFrame {

    //Volatile allows read from main mem and change to main mem to deal with concurrency
    private volatile boolean active;
    private volatile boolean closed;

    // Components
    private JFrame mainWindow;
    private JPanel keyPanel;
    private JLabel infoLabel;
    private JPanel infoPanel;
    private JPanel buttonPanel;
    private JButton keyButton;
    private JButton removeButton;
    private KeyManager manager;
    private Map<Character, KeyLabel> keyLabelMap;


    private long startTime;
    private long lastPressTime;
    private BufferedImage buttonUp;
    private BufferedImage buttonDown;


    private ArrayList<Character> keyOrder;


    public KpsWindow(){
        mainWindow = new JFrame("javaKPS");
        mainWindow.setLayout(new BorderLayout());
        manager = new KeyManager();
        buttonPanel = new JPanel();
        keyButton = new JButton();
        Key.buildNativeKeyMap();
        keyOrder = new ArrayList<>();
        keyLabelMap = new HashMap<>();
        keyPanel = new JPanel();
        infoPanel = new JPanel();
        removeButton = new JButton();

        // button images
        try {
           buttonUp = ImageIO.read(new File("assets/button_up.png"));
           buttonDown = ImageIO.read(new File("assets/button_down.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        infoPanel.setPreferredSize(new Dimension(100,100));
        infoLabel = new JLabel("<html>KPS:<br>BPM:<br>TOTAL KEYS:</html>",SwingConstants.CENTER);
        infoPanel.add(infoLabel, BorderLayout.PAGE_END);
        infoLabel.setHorizontalTextPosition(JLabel.CENTER);
        infoLabel.setVerticalTextPosition(JLabel.CENTER);


        mainWindow.setSize(1000, 330);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        try { // global screen for nativeKeyPresses
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException e) {
            e.printStackTrace();
        }
        GlobalScreen.addNativeKeyListener(new KeyTracker());

        mainWindow.add(keyPanel);
        mainWindow.add(infoPanel, BorderLayout.SOUTH);
        mainWindow.add(buttonPanel,BorderLayout.NORTH);
        keyPanel.setLocation(20,120);
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
                        if(c == null) l.setText("Bad key");
                        else if(addKey(c))
                        {
                            keyFrame.dispose();
                            GlobalScreen.removeNativeKeyListener(this);
                        }
                        else
                        {
                            l.setText("Already added");
                        }
                    }
                    @Override
                    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

                    @Override
                    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}
                };

                GlobalScreen.addNativeKeyListener(tempListen);
                keyFrame.addWindowListener(new WindowAdapter() { // closing window event
                    @Override
                    public void windowClosing(WindowEvent windowEvent) {
                        GlobalScreen.removeNativeKeyListener(tempListen);
                    }
                });
                keyFrame.setVisible(true);
            }
        }); // opens a new window that prompts for a key

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
                        if(c == null) l.setText("Bad Key");
                        else if(removeKey(c))
                        {
                            keyFrame.dispose();
                            GlobalScreen.removeNativeKeyListener(this);
                        }
                        else
                        {
                            l.setText("Not a key being tracked");
                        }
                    }
                    @Override
                    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {}

                    @Override
                    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}
                };
                GlobalScreen.addNativeKeyListener(tempListen);

                keyFrame.addWindowListener(new WindowAdapter() { // closing window event
                    @Override
                    public void windowClosing(WindowEvent windowEvent) {
                        GlobalScreen.removeNativeKeyListener(tempListen);
                    }
                });
                keyFrame.setVisible(true);
            }
        }); // opens a new window that prompts for a key
        mainWindow.addWindowListener(new WindowAdapter() { // closing window event
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                closed = true;
            }
        });
        mainWindow.pack();
        mainWindow.setVisible(true);
        while(!closed) // main loop that runs when the program is running
        {
            if(active) {
                long curTime = new Date().getTime();
                manager.updateTime(curTime - startTime);
                updateLabel();
                if(curTime - lastPressTime > 3500)
                {
                    active = false;
                    manager.resetSession();
                    updateLabel();
                }
            }
        }
        System.out.println("closed");
        // save to file here
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
        if(active)infoLabel.setText("<html>KPS:" + (int)manager.getKps() + "<br>" +
                "BPM:" + manager.getBpm() + "<br>" +
                "TOTAL KEYS:" + manager.getTotalPresses()+ "<br>" +
                "</html>");
        else infoLabel.setText("<html>MAX KPS:" + (int)manager.getMaxKps() + "<br>" +
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
                keyPanel.remove(kl);
                mainWindow.invalidate();
                mainWindow.validate();
                mainWindow.repaint();

                mainWindow.pack();
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
            keyPanel.add(kl);
            keyOrder.add(c);
            mainWindow.pack();
            return true;
        }
        return false;
    }
    public class KeyTracker implements NativeKeyListener {


        @Override
        public void nativeKeyPressed(NativeKeyEvent e) {

            if(!active)
            {
                active = true;
                startTime = new Date().getTime();
            }
                lastPressTime = new Date().getTime();
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

