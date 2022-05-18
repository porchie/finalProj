import java.util.*;

public class KeyManager {
    private Map<Character, Key> keysTracking;
    private int totalPresses;
    private int sessionPresses;
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
        //System.out.println(k.getKeyChar());
        keysTracking.put(c, k);
        return k;
    }

    public Key removeKey(char c)
    {
        c = Character.toUpperCase(c);
        return keysTracking.remove(c);
    }


    public void resetSession()
    {
        sessionPresses = 0;
        time = 0;
    }
    public int getTotalPresses() {
        return totalPresses;
    }

    public double getKps() {
        return (sessionPresses / time);
    }

    public int getBpm() {
        return (int)(getKps() * 15);
    }

    public double getTime() {
        return time;
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
        sessionPresses++;
        return true; // true, a key has been pressed, so reset the timer for session reset
    }

    public String getKeyInfo(char c)
    {
        c = Character.toUpperCase(c);
        Key k = keysTracking.get(c);
        if(k == null) return "";
        if( c == ' ') return "space " + k.getTimesPressed();
        return c + " " + k.getTimesPressed();
    }
}
