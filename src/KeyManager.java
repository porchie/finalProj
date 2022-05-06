import java.util.*;

public class KeyManager {
    private Map<Character, Key> keysTracking;
    private int totalPresses;
    private int kps;
    private int bpm;

    public KeyManager() {
        keysTracking = new HashMap<>();
    }

    public boolean addKey(char c)
    {
        c = Character.toUpperCase(c);
        if (keysTracking.containsKey(c)) return false;
        keysTracking.put(c, new Key(c));
        return true;
    }

    public void pressKey(char c)
    {
        c = Character.toUpperCase(c);
        Key k = keysTracking.get(c);
        if(k == null) return;
        k.pressKey();
        return;
    }

    public String getKeyInfo(char c)
    {
        c = Character.toUpperCase(c);
        Key k = keysTracking.get(c);
        if(k == null) return "";
        return c + " " + k.getTimesPressed();
    }
}
