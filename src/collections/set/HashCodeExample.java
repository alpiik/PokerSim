package collections.set;

import oo.hide.Point;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class HashCodeExample {

    @Test
    public void runExample() {
        Set<Point> set = new HashSet<>();

        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        System.out.println("p1 equals p2: " + p1.equals(p2));
        System.out.println("p1 hashCode: " + p1.hashCode());
        System.out.println("p2 hashCode: " + p2.hashCode());

        set.add(p1);
        set.add(p2);

        System.out.println("Set size: " + set.size());
    }
}