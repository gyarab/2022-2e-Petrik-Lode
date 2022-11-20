package shipspack.ships;

public class coordinates {
    int x;
    int y;

    public coordinates(int x, int y) {
        this.x = x;
        this.y = y;

    }


    public Integer getX() {
        return x;
    }
    public Integer getY() {
        return y;
    }

public void set(int passX, int passY)
{
    x = passX;
    y = passY;
}

    @Override
    public String toString() {
        return "coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
