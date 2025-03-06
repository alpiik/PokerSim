package oo.hide;

public class Fibonacci {
    private int previous = 0;
    private int current = 1;
    private int count = 0;
    public int nextValue() {
        if (count == 0) {
            count++;
            return previous;
        } else if (count == 1) {
            count++;
            return current;
        } else {
            int next = previous + current;
            previous = current;
            current = next;
            count++;
            return next;
        }
    }
}