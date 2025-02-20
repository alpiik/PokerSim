package types;

import java.util.Arrays;
import java.util.Random;

public class Code {

    public static void main(String[] args) {

        int[] numbers = {1, 3, -2, 9};

        System.out.println(sum(numbers)); // 11
    }

    public static int sum(int[] numbers) {
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        return sum;
    }

    public static double average(int[] numbers) {
        if (numbers == null || numbers.length == 0) {
            return 0;
        }

        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }

        return Double.valueOf(sum) / Double.valueOf(numbers.length);
    }

    public static Integer minimumElement(int[] integers) {
        if (integers == null || integers.length == 0) {
            return null;
        }

        int min = integers[0];
        for (int i = 1; i < integers.length; i++) {
            if (integers[i] < min) {
                min = integers[i];
            }
        }
        return min;
    }

    public static String asString(int[] elements) {
        if (elements == null) {
            return "null";
        }

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < elements.length; i++) {
            result.append(elements[i]);
            if (i < elements.length - 1) {
                result.append(", ");
            }
        }
        return result.toString();
    }

    public static Character mode(String input) {
        if (input.length() == 0) {
            return null;
        }
        char mostChar = input.charAt(0);
        int count = 0;
        for (int i = 1; i < input.length(); i++) {
            char observable = input.charAt(i);
            int counter = 0;

            for (int j = 0; j < input.length(); j++) {
                if (observable == input.charAt(j)) {
                    counter++;
                }
            }
            if (counter > count) {
                count = counter;
                mostChar = observable;
            }
        }

        return mostChar;
    }

    public static String squareDigits(String s) {
        if (s == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        for (char c : s.toCharArray()) {
            if (Character.isDigit(c)) {
                int digit = Integer.parseInt(Character.toString(c));
                int squared = digit * digit;
                result.append(squared);
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    public static int isolatedSquareCount() {
        boolean[][] matrix = getSampleMatrix();
        int isolatedCount = 0;
        for (int n = 0; n < matrix.length; n++) {
            for (int m = 0; m < matrix[n].length; m++) {
                if (isIsolated(n, m)) {
                    isolatedCount++;
                }
            }
        }
        return isolatedCount;
    }

    public static boolean isIsolated(int row, int col) {
        boolean[][] matrix = getSampleMatrix();
        printMatrix(matrix);
        if (row < 0 || row >= matrix.length || col < 0 || col >= matrix[0].length) {
            return false;
        }

        for (int n = row - 1; n <= row + 1; n++) {
            for (int m = col - 1; m <= col + 1; m++) {
                if (n == row && m == col) {
                    continue;
                }

                if (n >= 0 && n < matrix.length && m >= 0 && m < matrix[0].length && matrix[n][m]) {
                    return false; // Pigem on true väärtusega naaber
                }
            }
        }
        return true; // Ühtegi true väärtusega naabrit ei ole :)
    }

    private static void printMatrix(boolean[][] matrix) {
        for (boolean[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }
    }

    private static boolean[][] getSampleMatrix() {
        boolean[][] matrix = new boolean[10][10];

        Random r = new Random(5);
        for (boolean[] row : matrix) {
            for (int i = 0; i < row.length; i++) {
                row[i] = r.nextInt(5) < 2;
            }
        }

        return matrix;
    }
}
