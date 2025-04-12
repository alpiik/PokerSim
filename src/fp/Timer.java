package fp;

import org.junit.jupiter.api.Test;

public class Timer {

    @Test
    public void measuresExecutionTime() {
        System.out.println("Measuring methodToBeTimed:");
        executeAndTime(() -> methodToBeTimed());

        System.out.println("\nMeasuring short sleep:");
        executeAndTime(() -> {
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.err.println("Sleep interrupted");
            }
        });
    }
    private void executeAndTime(Runnable code) {
        long start = System.currentTimeMillis();
        code.run();
        long durationMillis = System.currentTimeMillis() - start;
        double durationSeconds = durationMillis / 1000.0;
        String result = String.format("%.3f sek", durationSeconds);
        System.out.println("Execution took: " + result);
    }
    public void howToMeasureExecutionTimeExample() {
        long start = System.currentTimeMillis();
        for (long i = 0; i < 1e9; i++) {
        }
        double passedMills = System.currentTimeMillis() - start;
        String result = String.format("%.3f sek", passedMills / 1000.0);
        System.out.println(result);
    }
    private void methodToBeTimed() {
        for (long i = 0; i < 1e9; i++) {
        }
    }
}