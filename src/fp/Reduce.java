package fp;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException; // Kasutamata, võib eemaldada kui IllegalArgumentException on piisav
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Reduce {

    @Test
    public void reducesListToSingleResult() {

        var numbers = List.of(1, 2, 3, 4);
        var strings = List.of("1", "2", "3");
        var emptyList = List.<Integer>of(); // Tühi list testimiseks

        assertThat(reduce(numbers, (a, b) -> a + b)).isEqualTo(10);
        assertThat(reduce(numbers, (a, b) -> a * b)).isEqualTo(24);
        assertThat(reduce(strings, (a, b) -> a + b)).isEqualTo("123");
        assertThrows(IllegalArgumentException.class, () -> {
            reduce(emptyList, (a, b) -> a + b);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            this.<Integer>reduce(null, (a, b) -> a + b);
        });
    }
    private <T> T reduce(List<T> list, BiFunction<T, T, T> func) {

        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("Input list cannot be null or empty for reduce operation");
        }
        T accumulator = list.get(0);

        for (int i = 1; i < list.size(); i++) {
            accumulator = func.apply(accumulator, list.get(i));
        }
        return accumulator;
    }
}