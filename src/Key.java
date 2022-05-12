import org.jnativehook.keyboard.*;
import java.util.*;
public class Key {

    public static final Map<Integer,Character> keyMap = new HashMap<>();

    private char keyChar;
    private int timesPressed;
    private boolean pressed;

    public static void buildKeyMap()
    {
        keyMap.put(NativeKeyEvent.VC_A ,'A');
        keyMap.put(NativeKeyEvent.VC_B ,'B');
        keyMap.put(NativeKeyEvent.VC_C ,'C');
        keyMap.put(NativeKeyEvent.VC_D ,'D');
        keyMap.put(NativeKeyEvent.VC_E ,'E');
        keyMap.put(NativeKeyEvent.VC_F ,'F');
        keyMap.put(NativeKeyEvent.VC_G ,'G');
        keyMap.put(NativeKeyEvent.VC_H ,'H');
        keyMap.put(NativeKeyEvent.VC_I ,'I');
        keyMap.put(NativeKeyEvent.VC_J ,'J');
        keyMap.put(NativeKeyEvent.VC_K ,'K');
        keyMap.put(NativeKeyEvent.VC_L ,'L');
        keyMap.put(NativeKeyEvent.VC_M ,'M');
        keyMap.put(NativeKeyEvent.VC_N ,'N');
        keyMap.put(NativeKeyEvent.VC_O ,'O');
        keyMap.put(NativeKeyEvent.VC_P ,'P');
        keyMap.put(NativeKeyEvent.VC_Q ,'Q');
        keyMap.put(NativeKeyEvent.VC_R ,'R');
        keyMap.put(NativeKeyEvent.VC_S ,'S');
        keyMap.put(NativeKeyEvent.VC_T ,'T');
        keyMap.put(NativeKeyEvent.VC_U ,'U');
        keyMap.put(NativeKeyEvent.VC_V ,'V');
        keyMap.put(NativeKeyEvent.VC_W ,'W');
        keyMap.put(NativeKeyEvent.VC_X ,'X');
        keyMap.put(NativeKeyEvent.VC_Y ,'Y');
        keyMap.put(NativeKeyEvent.VC_Z ,'Z');
        keyMap.put(NativeKeyEvent.VC_1, '1');
        keyMap.put(NativeKeyEvent.VC_2, '2');
        keyMap.put(NativeKeyEvent.VC_3, '3');
        keyMap.put(NativeKeyEvent.VC_4, '4');
        keyMap.put(NativeKeyEvent.VC_5, '5');
        keyMap.put(NativeKeyEvent.VC_6, '6');
        keyMap.put(NativeKeyEvent.VC_7, '7');
        keyMap.put(NativeKeyEvent.VC_8, '8');
        keyMap.put(NativeKeyEvent.VC_9, '9');
        keyMap.put(NativeKeyEvent.VC_0, '0');
        keyMap.put(NativeKeyEvent.VC_OPEN_BRACKET, '[');
        keyMap.put(NativeKeyEvent.VC_CLOSE_BRACKET ,']');
        keyMap.put(NativeKeyEvent.VC_BACK_SLASH ,'\\');
        keyMap.put(NativeKeyEvent.VC_QUOTE , '\"');
        keyMap.put(NativeKeyEvent.VC_COMMA ,',');
        keyMap.put(NativeKeyEvent.VC_PERIOD,'.');
        keyMap.put(NativeKeyEvent.VC_SLASH ,'/');
        keyMap.put(NativeKeyEvent.VC_SPACE, ' ');
        keyMap.put(NativeKeyEvent.VC_SEMICOLON ,';');
        keyMap.put(NativeKeyEvent.VC_MINUS ,'-');
        keyMap.put(NativeKeyEvent.VC_EQUALS ,'+');
        
    }

    public Key(char keyChar) {
        this.keyChar = keyChar;
    }

    public int getTimesPressed() {
        return timesPressed;
    }

    public char getKeyChar() {
        return keyChar;
    }

    public void pressKey()
    {
        timesPressed++;
    }

    public boolean isPressed() {return pressed;}


}
