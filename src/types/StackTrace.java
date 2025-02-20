package types;

public class StackTrace {

    public static void main(String[] args) {
        try {
            Double finalPrice = calculatePrice();
            System.out.println("Final Price: " + finalPrice);
        } catch (IllegalArgumentException e) { // Catch the correct exception
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static Double calculatePrice() {
        Double basePrice = calculateBasePrice();
        return basePrice * (1 + 0.2);
    }

    public static Double calculateBasePrice() {
        Double netCost = 100D;
        Integer profitConstant = readProfitConstant();

        if (profitConstant == null) {
            throw new IllegalArgumentException("Profit constant cannot be null.");
        }

        return netCost + (0.1 * profitConstant * netCost);
    }

    public static Integer readProfitConstant() {
        return 5;
    }
}