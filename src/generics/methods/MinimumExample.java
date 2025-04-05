package generics.methods;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinimumExample {

    @Test
    public void findMinimumExample() {
        assertThat(min(1, 2)).isEqualTo(1);
        assertThat(min(2, 1)).isEqualTo(1);

        assertThat(min("a", "b")).isEqualTo("a");
        assertThat(min("b", "a")).isEqualTo("a");
    }
    public <T extends Comparable<T>> T min(T a, T b) {
        return a.compareTo(b) < 0 ? a : b;
    }
}