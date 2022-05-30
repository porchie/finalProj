import org.jnativehook.keyboard.*;
import java.util.*;
public class Key {

    public static final Map<Integer,Character> NATIVE_KEY_MAP = new HashMap<>();
    private char keyChar;
    private int timesPressed;
    public static void buildNativeKeyMap() // map of native keys -> Character
    {
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_A ,'A');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_B ,'B');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_C ,'C');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_D ,'D');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_E ,'E');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_F ,'F');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_G ,'G');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_H ,'H');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_I ,'I');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_J ,'J');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_K ,'K');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_L ,'L');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_M ,'M');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_N ,'N');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_O ,'O');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_P ,'P');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_Q ,'Q');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_R ,'R');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_S ,'S');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_T ,'T');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_U ,'U');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_V ,'V');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_W ,'W');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_X ,'X');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_Y ,'Y');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_Z ,'Z');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_1, '1');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_2, '2');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_3, '3');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_4, '4');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_5, '5');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_6, '6');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_7, '7');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_8, '8');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_9, '9');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_0, '0');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_OPEN_BRACKET, '[');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_CLOSE_BRACKET ,']');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_BACK_SLASH ,'\\');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_QUOTE , '\"');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_COMMA ,',');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_PERIOD,'.');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_SLASH ,'/');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_SPACE, ' ');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_SEMICOLON ,';');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_MINUS ,'-');
        NATIVE_KEY_MAP.put(NativeKeyEvent.VC_EQUALS ,'+');
        
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
}
