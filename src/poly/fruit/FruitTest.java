package poly.fruit;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FruitTest {

    @Test
    public void computesTotalWeight() {

        List<Weighable> fruits = List.of(new Apple(0.1), new Orange(120));

        assertThat(getTotalWeightInGrams(fruits)).isEqualTo(220);
    }

    private int getTotalWeightInGrams(List<Weighable> list) {
        return list.stream().mapToInt(Weighable::getWeightInGrams).sum();
    }
}
