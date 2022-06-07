public class KeyVisRectangle {
    private int x;
    private int y;
    private int h;
    private int w;
    private int totalTraveled;
    public KeyVisRectangle(int x, int y, int h, int w) {
        this.x = x;
        this.y = y;
        this.h = h;
        this.w = w;
    }
    public void travel(int trav)
    {
        totalTraveled+=trav;
    }
    public int getTotalTraveled()
    {
        return totalTraveled;
    }


    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setH(int h) {
        this.h = h;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }

    public int getW() {
        return w;
    }
}
