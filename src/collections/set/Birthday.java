package collections.set;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class Birthday {

    @Test
    public void runCode() {
        int trials = 10000;
        int totalPeople = 0;

        for (int i = 0; i < trials; i++) {
            totalPeople += findFirstCollision();
        }

        double average = (double) totalPeople / trials;
        System.out.println("Keskmine inimeste arv, kus kahel inimesel on sama sünnipäev: " + average);
    }

    private int findFirstCollision() {
        Random r = new Random();
        Set<Integer> birthdays = new HashSet<>();
        int count = 0;

        while (true) {
            int randomDayOfYear = r.nextInt(365);
            count++;

            if (birthdays.contains(randomDayOfYear)) {
                return count;
            }

            birthdays.add(randomDayOfYear);
        }
    }
}