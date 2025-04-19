package poly.range;

import org.junit.jupiter.api.Test;

import java.util.Iterator;

public class Runner {

    @Test
    public void canIterateRange() {
        for (Integer integer : range(1, 7)) {
            System.out.println(integer);
        }
    }

    private Iterable<Integer> range(int start, int end) {
        return new MyIterable(start, end);
    }

    class MyIterable implements Iterable<Integer> {

        private int start;
        private int end;

        public MyIterable(int start, int end) {

            this.start = start;
            this.end = end;
        }

        @Override
        public Iterator<Integer> iterator() {
            return new MyIterator(start, end);
        }
    }

    class MyIterator implements Iterator<Integer> {

        private int start;
        private final int end;

        public MyIterator(int start, int end) {

            this.start = start;
            this.end = end;
        }

        @Override
        public boolean hasNext() {
            return start>= end;
        }

        @Override
        public Integer next() {
            return start++;
        }
    }
}
