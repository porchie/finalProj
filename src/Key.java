import org.jnativehook.keyboard.*;
import java.util.*;
public class Key {

    public static final Map<Integer,Character> NativeKeyMap = new HashMap<>();
    private char keyChar;
    private int timesPressed;
    private boolean pressed;

    public static void buildNativeKeyMap()
    {
        NativeKeyMap.put(NativeKeyEvent.VC_A ,'A');
        NativeKeyMap.put(NativeKeyEvent.VC_B ,'B');
        NativeKeyMap.put(NativeKeyEvent.VC_C ,'C');
        NativeKeyMap.put(NativeKeyEvent.VC_D ,'D');
        NativeKeyMap.put(NativeKeyEvent.VC_E ,'E');
        NativeKeyMap.put(NativeKeyEvent.VC_F ,'F');
        NativeKeyMap.put(NativeKeyEvent.VC_G ,'G');
        NativeKeyMap.put(NativeKeyEvent.VC_H ,'H');
        NativeKeyMap.put(NativeKeyEvent.VC_I ,'I');
        NativeKeyMap.put(NativeKeyEvent.VC_J ,'J');
        NativeKeyMap.put(NativeKeyEvent.VC_K ,'K');
        NativeKeyMap.put(NativeKeyEvent.VC_L ,'L');
        NativeKeyMap.put(NativeKeyEvent.VC_M ,'M');
        NativeKeyMap.put(NativeKeyEvent.VC_N ,'N');
        NativeKeyMap.put(NativeKeyEvent.VC_O ,'O');
        NativeKeyMap.put(NativeKeyEvent.VC_P ,'P');
        NativeKeyMap.put(NativeKeyEvent.VC_Q ,'Q');
        NativeKeyMap.put(NativeKeyEvent.VC_R ,'R');
        NativeKeyMap.put(NativeKeyEvent.VC_S ,'S');
        NativeKeyMap.put(NativeKeyEvent.VC_T ,'T');
        NativeKeyMap.put(NativeKeyEvent.VC_U ,'U');
        NativeKeyMap.put(NativeKeyEvent.VC_V ,'V');
        NativeKeyMap.put(NativeKeyEvent.VC_W ,'W');
        NativeKeyMap.put(NativeKeyEvent.VC_X ,'X');
        NativeKeyMap.put(NativeKeyEvent.VC_Y ,'Y');
        NativeKeyMap.put(NativeKeyEvent.VC_Z ,'Z');
        NativeKeyMap.put(NativeKeyEvent.VC_1, '1');
        NativeKeyMap.put(NativeKeyEvent.VC_2, '2');
        NativeKeyMap.put(NativeKeyEvent.VC_3, '3');
        NativeKeyMap.put(NativeKeyEvent.VC_4, '4');
        NativeKeyMap.put(NativeKeyEvent.VC_5, '5');
        NativeKeyMap.put(NativeKeyEvent.VC_6, '6');
        NativeKeyMap.put(NativeKeyEvent.VC_7, '7');
        NativeKeyMap.put(NativeKeyEvent.VC_8, '8');
        NativeKeyMap.put(NativeKeyEvent.VC_9, '9');
        NativeKeyMap.put(NativeKeyEvent.VC_0, '0');
        NativeKeyMap.put(NativeKeyEvent.VC_OPEN_BRACKET, '[');
        NativeKeyMap.put(NativeKeyEvent.VC_CLOSE_BRACKET ,']');
        NativeKeyMap.put(NativeKeyEvent.VC_BACK_SLASH ,'\\');
        NativeKeyMap.put(NativeKeyEvent.VC_QUOTE , '\"');
        NativeKeyMap.put(NativeKeyEvent.VC_COMMA ,',');
        NativeKeyMap.put(NativeKeyEvent.VC_PERIOD,'.');
        NativeKeyMap.put(NativeKeyEvent.VC_SLASH ,'/');
        NativeKeyMap.put(NativeKeyEvent.VC_SPACE, ' ');
        NativeKeyMap.put(NativeKeyEvent.VC_SEMICOLON ,';');
        NativeKeyMap.put(NativeKeyEvent.VC_MINUS ,'-');
        NativeKeyMap.put(NativeKeyEvent.VC_EQUALS ,'+');
        
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
