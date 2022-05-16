import java.util.*;

public class KeyManager {
    private Map<Character, Key> keysTracking;
    private int totalPresses;
    private double time;
    // UR calculator, maybe a class that takes in the bpm to calculate unstable rate
    
    //according to most osu tools, the bpm is kpm/4, so kps * 15, kinda weird how its not kps*60???? or just bpm=kpm idk im not a musician
    // 

    public KeyManager() {
        keysTracking = new HashMap<>();
    }

    public Key addKey(char c) // need to somehow detect this somehow???
    {
        c = Character.toUpperCase(c);
        if (keysTracking.containsKey(c)) return null;
        Key k = new Key(c);
        keysTracking.put(c, k);
        return k;
    }

    public int getTotalPresses() {
        return totalPresses;
    }

    public double getKps() {
        return (totalPresses / time);
    }

    public int getBpm() {
        return (int)(getKps() * 15);
    }

    public void updateTime(long time)
    {
        this.time = (double)time / 1000;
    }
    public boolean pressKey(char c)
    {
        c = Character.toUpperCase(c);
        Key k = keysTracking.get(c);
        if(k == null) return false; // no key of this character is tracked, so false, no key has been pressed, and the timer for session reset continues
        k.pressKey();
        totalPresses++;
        return true; // true, a key has been pressed, so reset the timer for session reset
    }

    public String getKeyInfo(char c) // testing method for seeing key info maybe useful for display
    {
        c = Character.toUpperCase(c);
        Key k = keysTracking.get(c);
        if(k == null) return "";
        if( c == ' ') return "space " + k.getTimesPressed();
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
