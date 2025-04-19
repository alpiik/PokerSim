package poly.undo;

import java.util.Stack;
import java.util.function.Function;

public class Calculator {

    private double value = 0;

    private Stack<Function<Double, Double>>
            undoStack = new Stack<>();

    public void input(double value) {
        double valueBefore = this.value;

        undoStack.add(x -> valueBefore);
        this.value = value;
    }

    public void add(double addend) {
    undoStack.add(x -> x - addend);
        value += addend;
    }

    public void multiply(double multiplier) {
        undoStack.push(x -> x / multiplier);
        value *= multiplier;
    }

    public double getResult() {

        return value;
    }

    public void undo() {
        if (undoStack.isEmpty()) {
            throw new RuntimeException("Stack is empty");
        }
        value = undoStack.pop().apply(value);
    }
}
