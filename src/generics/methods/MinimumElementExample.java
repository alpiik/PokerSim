package generics.methods;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MinimumElementExample {

    @Test
    public void findsMinimumElementFromList() {
        assertThat(minimumElement(Arrays.asList(1, 2, -5))).isEqualTo(-5);
        assertThat(minimumElement(Arrays.asList("b", "a", "c"))).isEqualTo("a");
    }
    public <T extends Comparable<T>> T minimumElement(List<T> list) {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("List should contain at least one element");
        }

        T minElement = list.get(0);

        for (T element : list) {
            if (element.compareTo(minElement) < 0) {
                minElement = element;
            }
        }
        return minElement;
    }
}