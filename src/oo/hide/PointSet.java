package oo.hide;

import java.util.Arrays;
import java.util.Objects;

public class PointSet {

    private Point[] points;
    private int size;
    public PointSet(int capacity) {
        this.points = new Point[capacity];
        this.size = 0;
    }
    public PointSet() {
        this(10);
    }
    public void add(Point point) {
        if (contains(point)) {
            return;
        }
        if (size == points.length) {
            points = Arrays.copyOf(points, points.length * 2);
        }
        points[size++] = point;
    }
    public int size() {
        return size;
    }
    public boolean contains(Point point) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(points[i], point)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            sb.append(points[i]);
            if (i < size - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof PointSet)) {
            return false;
        }
        PointSet other = (PointSet) obj;
        if (this.size != other.size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!other.contains(points[i])) {
                return false;
            }
        }
        return true;
    }
    public PointSet subtract(PointSet other) {
        PointSet result = new PointSet();
        for (int i = 0; i < size; i++) {
            if (!other.contains(points[i])) {
                result.add(points[i]);
            }
        }
        return result;
    }
    public PointSet intersect(PointSet other) {
        PointSet result = new PointSet();
        for (int i = 0; i < size; i++) {
            if (other.contains(points[i])) {
                result.add(points[i]);
            }
        }
        return result;
    }
    public void remove(Point point) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(points[i], point)) {
                // Shift remaining elements to the left
                System.arraycopy(points, i + 1, points, i, size - i - 1);
                size--;
                break;
            }
        }
    }
    @Override
    public int hashCode() {
        return 0;
    }
}