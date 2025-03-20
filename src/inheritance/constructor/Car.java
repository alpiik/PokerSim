package inheritance.constructor;

public class Car extends Vehicle {
    public Car() {
        System.out.println("constructing Car");
    }

    public Car(String regNumber) {
        System.out.println("constructing Car with number " + regNumber);
    }
}