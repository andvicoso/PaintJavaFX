package paint.model;

/**
 *
 * @author Anderson
 */
import static java.lang.Double.compare;

public final class Point {

    private final int x;
    private final int y;

    public Point(double x, double y) {
        this((int) x, (int) y);
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Point point = (Point) o;
        return compare(point.x, x) == 0 && compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.x;
        hash = 53 * hash + this.y;
        return hash;
    }
}
