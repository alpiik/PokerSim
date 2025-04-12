package fp;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

public class Map {

    @Test
    public void transformsListElements() {
        var numbers = List.of(1, 2, 3, 4);
        assertThat(map(numbers, x -> x + 1)).containsExactly(2, 3, 4, 5);
    }
    private List<Integer> map(List<Integer> list, Function<Integer, Integer> mapper) {
        List<Integer> mappedList = new ArrayList<>();
        for (Integer element : list) {
            Integer transformedElement = mapper.apply(element);
            mappedList.add(transformedElement);
        }
        return mappedList;
    }
}
