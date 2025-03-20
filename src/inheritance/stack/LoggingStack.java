package inheritance.stack;

import java.util.Stack;

public class LoggingStack extends Stack<Integer> {

    @Override
    public Integer push(Integer item) {
        System.out.println("Pushing: " + item);
        return super.push(item);
    }

    @Override
    public Integer pop() {
        Integer item = super.pop();
        System.out.println("Popping: " + item);
        return item;
    }

    public void pushAll(Integer... numbers) {
        for (Integer number : numbers) {
            this.push(number);
        }
    }
}