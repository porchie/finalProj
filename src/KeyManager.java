import java.util.*;

public class KeyManager {
    private Map<Character, Key> keysTracking;
    private int totalPresses;
    private int kps;
    private int bpm;

    public KeyManager() {
        keysTracking = new HashMap<>();
    }

    public boolean addKey(char c) // need to somehow detect this somehow???
    {
        c = Character.toUpperCase(c);
        if (keysTracking.containsKey(c)) return false;
        keysTracking.put(c, new Key(c));
        return true;
    }

    public boolean pressKey(char c)
    {
        c = Character.toUpperCase(c);
        Key k = keysTracking.get(c);
        if(k == null) return false; // no key of this character is tracked, so false, no key has been pressed, and the timer for session reset continues
        k.pressKey();
        return true; // true, a key has been pressed, so reset the timer for session reset
    }

    public String getKeyInfo(char c)
    {
        c = Character.toUpperCase(c);
        Key k = keysTracking.get(c);
        if(k == null) return "";
        return c + " " + k.getTimesPressed();
    }
    
    
  
    
    //need a way to track the number of key presses in a certain time period, then after a reset, the value of this period dependent kps is reset
    //such that kps isnt determined by total runtime of program but a certain kps session. This session should reset after a certain time period where no 
    //key is pressed.
    //The time tracking should occur within the window, where if no keypresses are detected for a while, the kps session resets. This session is the way to calculate
    //kps and max kps 
    //IF there was no session that resets times and kps, not pressing anything because you stopped for a bit will destroy the kps and also i dont want a timer to be
    //always ticking.
    
    //do this ^
    
    //bro what is java timing btw
}
