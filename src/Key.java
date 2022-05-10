public class Key {
    private char keyChar;
    private int timesPressed;
    private boolean pressed;

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
