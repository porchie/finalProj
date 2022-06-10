import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class KeyLabel extends JLabel {
    private Key key;
    private KeyVisRectangle curRect; // reference to the rectangle that is the hold note or currently holding
    private ArrayList<KeyVisRectangle> rects;

    public KeyLabel(Icon icon, Key key)
    {
        super(icon);
        this.key = key;
        rects=new ArrayList<>();
        curRect = null;
        setHorizontalTextPosition(JLabel.CENTER);
        setVerticalTextPosition(JLabel.CENTER);
        setText(  ((key.getKeyChar() == ' ')  ? "space":key.getKeyChar()) + " " + key.getTimesPressed());
    }
    public void setCurRect(KeyVisRectangle rect)
    {
        this.curRect = rect;
        if(curRect!=null)
        {
            rects.add(curRect);
        }
    }

    public KeyVisRectangle getCurRect() {
        return curRect;
    }

    public ArrayList<KeyVisRectangle> getRects() {
        return rects;
    }
}
