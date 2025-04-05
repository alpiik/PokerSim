package generics.stack;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StackTest {

    @Test
    public void elementsArePoppedInReverseOrder() {
        Stack<Integer> stack = new Stack<>();

        stack.push(1);
        stack.push(2);

        Integer first = stack.pop();
        Integer second = stack.pop();

        assertThat(first).isEqualTo(2);
        assertThat(second).isEqualTo(1);
    }

    @Test
    public void worksWithDifferentTypes() {
        Stack<String> stringStack = new Stack<>();
        stringStack.push("first");
        stringStack.push("second");

        assertThat(stringStack.pop()).isEqualTo("second");

        Stack<Double> doubleStack = new Stack<>();
        doubleStack.push(1.5);
        doubleStack.push(2.5);

        assertThat(doubleStack.pop()).isEqualTo(2.5);
    }
}