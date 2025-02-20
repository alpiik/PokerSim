package types;

import java.util.logging.Level;
import java.util.logging.Logger;

public class StackTrace {

    private static final Logger logger = Logger.getLogger(StackTrace.class.getName());

    public static void main(String[] args) {
        try {
            Double finalPrice = calculatePrice();
            System.out.println("Final Price: " + finalPrice);
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error: " + e.getMessage(), e);
        }
    }

    public static Double calculatePrice() {
        Double basePrice = calculateBasePrice();
        return basePrice * (1 + 0.2);
    }

    public static Double calculateBasePrice() {
        Integer profitConstant = readProfitConstant();

        if (profitConstant == null) {
            throw new IllegalArgumentException("Profit constant cannot be null.");
        }

        Double netCost = 100D;
        return netCost + (0.1 * profitConstant * netCost);
    }

    public static Integer readProfitConstant() {
        return 5;
    }
}