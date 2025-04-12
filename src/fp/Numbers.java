package fp;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

public class Numbers {

    private final List<Integer> numbers = List.of(1, 3, 4, 51, 24, 5);

    @Test
    public void findsOddNumbers() {
        List<Integer> oddNumbers = numbers.stream()
                .filter(n -> n % 2 != 0)
                .toList();
        System.out.println("Paaritud arvud: " + oddNumbers);
        assertThat(oddNumbers).containsExactly(1, 3, 51, 5);
    }
    @Test
    public void findsOddNumbersOver10() {
        List<Integer> oddNumbersOver10 = numbers.stream()
                .filter(n -> n % 2 != 0)
                .filter(n -> n > 10)
                .toList();
        System.out.println("Paaritud arvud > 10: " + oddNumbersOver10);
        assertThat(oddNumbersOver10).containsExactly(51);
    }
    @Test
    public void findsSquaredOddNumbers() {
        List<Integer> oddNumbersSquared = numbers.stream()
                .filter(n -> n % 2 != 0)
                .map(n -> n * n)
                .toList();
        System.out.println("Paaritute arvude ruudud: " + oddNumbersSquared);
        assertThat(oddNumbersSquared).containsExactly(1, 9, 2601, 25);
    }
    @Test
    public void findsSumOfOddNumbers() {
        int sumOfOddNumbers = numbers.stream()
                .filter(n -> n % 2 != 0)
                .mapToInt(Integer::intValue)
                .sum();
        System.out.println("Paaritute arvude summa: " + sumOfOddNumbers);
        assertThat(sumOfOddNumbers).isEqualTo(60);
    }
}