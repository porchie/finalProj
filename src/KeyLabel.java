import javax.swing.*;

public class KeyLabel extends JLabel {
    private Key key;

    public KeyLabel(Icon icon, Key key)
    {
        super(icon);
        this.key = key;
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.CENTER);
        setText(key.getKeyChar() + " " + key.getTimesPressed());
    }
}
