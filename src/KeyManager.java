import java.util.HashMap;
import java.util.Map;

public class KeyManager {
    private Map<Character, Key> keysTracking;
    private int totalPresses;
    private int sessionPresses;
    private double time;
    private double maxKps;
    public KeyManager() {
        keysTracking = new HashMap<>();
    }

    public Key addKey(char c)
    {
        c = Character.toUpperCase(c);
        if (keysTracking.containsKey(c)) return null;
        Key k = new Key(c);
        keysTracking.put(c, k);
        return k;
    }

    public Key removeKey(char c)
    {
        c = Character.toUpperCase(c);
        return keysTracking.remove(c);
    }

    public double getMaxKps()
    {
        return maxKps;
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
        double kps = sessionPresses/(time + 0.2); //offset time a bit so kps is more stable from the start
        if(kps > maxKps) maxKps = kps;
        return (sessionPresses / time);
    }

    public int getBpm() {
        return (int)(getKps() * 15);
    } // the most accurate with most rhythm game charts

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
