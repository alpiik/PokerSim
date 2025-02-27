package types;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StackTrace {

    private static final Logger LOGGER = Logger.getLogger(StackTrace.class.getName());

    public static void main(String[] args) {
        try {
            double finalPrice = calculatePrice();
            System.out.println("Final Price: " + finalPrice);
        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.SEVERE, "Error: " + e.getMessage(), e);
        }
    }

    public static double calculatePrice() {
        double basePrice = calculateBasePrice();
        return basePrice * (1 + 0.2);
    }

    public static double calculateBasePrice() {
        int profitConstant = readProfitConstant();

        double netCost = 100.0;
        return netCost + (0.1 * profitConstant * netCost);
    }

    public static int readProfitConstant() {
        return 5;
    }
}