package exceptions.basic;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MinimumElementTests {

    @Test
    public void findsMinimumFromArrayOfNumbers() {

        assertThat(Code.minimumElement(new int[] { 1, 3 })).isEqualTo(1);

        assertThat(Code.minimumElement(new int[] { 1, 0 })).isEqualTo(0);
    }

    @Test
    public void throwsExceptionForEmptyArray() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Code.minimumElement(new int[] {})
        );
        assertThat(exception.getMessage()).isEqualTo("Massiiv ei tohi olla null või tühi.");
    }

    @Test
    public void throwsExceptionForNullInput() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> Code.minimumElement(null)
        );
        assertThat(exception.getMessage()).isEqualTo("Massiiv ei tohi olla null või tühi.");
    }
}
