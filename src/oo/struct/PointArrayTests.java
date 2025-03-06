package oo.struct;

import org.junit.jupiter.api.Test;

public class PointArrayTests {

    @Test
    public void coordinatesAsArrays() {
        Point3D[] trianglePoints = {
                new Point3D(1, 1, 0),
                new Point3D(5, 1, 0),
                new Point3D(3, 7, 1)
        };

        for (Point3D point : trianglePoints) {
            System.out.println(point.z  );
        }
    }

    @Test
    public void coordinatesAsObjects() {
        Point3D[] trianglePoints = {
                new Point3D(1, 1, 0),
                new Point3D(5, 1, 0),
                new Point3D(3, 7, 1)
        };

        for (Point3D point : trianglePoints) {
            System.out.println(point.z);
        }
    }
}

class Point3D {
    public int x;
    public int y;
    public int z;

    public Point3D(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}

