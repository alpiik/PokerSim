package generics.list;

public class BoxExample {

    public static void main(String[] args) {
        Box<? extends Number> box = new Box<>(0);

        box = new Box<Double>(1.5);
        box = new Box<Integer>(1);
        System.out.println(box.contents);
    }
    static class Box<T> {
        public Box(T contents) {
            this.contents = contents;
        }

        T contents;
    }
}