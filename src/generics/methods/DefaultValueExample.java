package generics.methods;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultValueExample {

    @Test
    public void exampleWithoutHelperMethodConstantExists() {
        Integer constant = getConstantForZone("A");
        if (constant == null) {
            constant = 1;
        }
        Integer result = 100 * constant;
        assertThat(result).isEqualTo(200);
    }

    @Test
    public void exampleWithoutHelperMethodConstantDoesNotExists() {
        Integer constant = getConstantForZone("B");
        if (constant == null) {
            constant = 1;
        }
        Integer result = 100 * constant;
        assertThat(result).isEqualTo(100);
    }

    @Test
    public void exampleWithHelperForIntegers() {
        Integer constant = defaultIfNull(getConstantForZone("A"), 1);
        assertThat(100 * constant).isEqualTo(200);

        constant = defaultIfNull(getConstantForZone("B"), 1);
        assertThat(100 * constant).isEqualTo(100);
    }

    @Test
    public void exampleWithHelperForDoubles() {
        Double constant = defaultIfNull(getConstantForZoneAsDouble("A"), 1.0);
        assertThat(100 * constant).isEqualTo(150);

        constant = defaultIfNull(getConstantForZoneAsDouble("B"), 1.0);
        assertThat(100 * constant).isEqualTo(100);
    }
    public <T> T defaultIfNull(T value, T defaultValue) {
        return value != null ? value : defaultValue;
    }

    private Integer getConstantForZone(String key) {
        if ("A".equals(key)) {
            return 2;
        } else {
            return null;
        }
    }

    private Double getConstantForZoneAsDouble(String key) {
        if ("A".equals(key)) {
            return 1.5;
        } else {
            return null;
        }
    }
}