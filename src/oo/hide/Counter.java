package oo.hide;

public class Counter {
    private int currentValue;
    private int step;
    public Counter(int start, int step) {
        this.currentValue = start;
        this.step = step;
    }
    public int nextValue() {
        int valueToReturn = currentValue;
        currentValue += step;
        return valueToReturn;
    }
}