package poly;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;


public class AverageTest {

    @Test
    public void computesAverageFromListOfNumbers() {

        Double result = getAverage(List.of(3.9, 4, 1.2, 0, 9, 2));

        assertThat(result).isCloseTo(3.35, within(0.01));
    }

    public Double getAverage(List<? extends Number> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Number number : numbers) {
            sum += number.doubleValue();
        }

        return sum / numbers.size();
    }
}