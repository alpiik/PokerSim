package junit;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTests {

    @Test
    public void testIsSpecial() {
        assertThat(Code.isSpecial(0));  // tõene
        assertThat(Code.isSpecial(1));  // tõene
        assertThat(Code.isSpecial(2));  // tõene
        assertThat(Code.isSpecial(3));  // tõene
        assertThat(Code.isSpecial(4));  // väär
        assertThat(Code.isSpecial(11)); // tõene
        assertThat(Code.isSpecial(15)); // väär
        assertThat(Code.isSpecial(36)); // tõene
        assertThat(Code.isSpecial(37)); // väär
    }

    @Test
    public void findsLongestStreak() {
        assertThat(Code.longestStreak("")).isEqualTo(0);

        assertThat(Code.longestStreak("a")).isEqualTo(1);

        assertThat(Code.longestStreak("abc")).isEqualTo(1);

        assertThat(Code.longestStreak("abbb")).isEqualTo(3);

        assertThat(Code.longestStreak("abbcccaaaad")).isEqualTo(4);
    }

    @Test
    public void findsModeFromCharactersInString() {

        assertThat(Code.mode(null)).isNull();
        assertThat(Code.mode("")).isNull();
        assertThat(Code.mode("abcb")).isEqualTo('b');
        assertThat(Code.mode("cbbc")).isEqualTo('c');

    }

    @Test
    public void removesDuplicates() {
        assertThat(Code.removeDuplicates(arrayOf(1, 1))).isEqualTo(arrayOf(1));

        assertThat(Code.removeDuplicates(arrayOf(1, 2, 1, 3, 2))).isEqualTo(arrayOf(1, 2, 3));

        assertThat(Code.removeDuplicates(arrayOf(1, 2, 3))).isEqualTo(arrayOf(1, 2, 3));

        assertThat(Code.removeDuplicates(arrayOf(100, 0, 3, 100, 0, 4, 562, 4)))
                .isEqualTo(arrayOf(100, 0, 3, 4, 562));
    }

    @Test
    public void sumsIgnoringDuplicates() {
        assertThat(Code.sumIgnoringDuplicates(arrayOf(1, 1))).isEqualTo(1);

        assertThat(Code.sumIgnoringDuplicates(arrayOf(1, 2, 1, 3, 2))).isEqualTo(6);

        assertThat(Code.sumIgnoringDuplicates(arrayOf(1, 2, 3))).isEqualTo(6);
    }

    private int[] arrayOf(int... numbers) {
        return numbers;
    }
}
