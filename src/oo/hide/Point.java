package oo.hide;

import java.util.Objects;

public class Point {

    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point other = (Point) obj;
        return Objects.equals(this.x, other.x) && Objects.equals(this.y, other.y);
    }
    @Override
    public int hashCode() {
        return 0;
    }
}