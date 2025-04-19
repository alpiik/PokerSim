package poly.shapes;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

public class ShapeTest {

    @Test
    public void computesTotalArea() {

        List<Shape> objects = List.of(
                new Circle(5), new Rectangle(2, 4), new Square(3));

        double totalArea = 0.0;
        for (Shape shape : objects) {
            totalArea += shape.getArea();
        }
    }
}