package generics.pair;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Runner {

    @Test
    public void pairExample() {
        Pair<String, Integer> pair = new Pair<>("key", 1);

        String key = pair.getFirst();
        Integer value = pair.getSecond();

        assertThat(key).isEqualTo("key");
        assertThat(value).isEqualTo(1);
    }
}