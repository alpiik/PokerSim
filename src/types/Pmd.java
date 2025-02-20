package types;

public class Pmd {

    public static void main(String[] args) {
        boolean[][] matrix = getSampleMatrix();

        printMatrix(matrix);

        System.out.println(containsTrueCell(matrix));
        System.out.println(findFirstTrueCell(matrix));
        System.out.println(countTrueRow(matrix));
    }

    private static void printMatrix(boolean[][] sampleMatrix) {
        for (boolean[] row : sampleMatrix) {
            for (boolean element : row) {
                System.out.print(element ? "X" : "O");
            }
            System.out.println();
        }
    }

    public static boolean containsTrueCell(boolean[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        for (boolean[] row : matrix) {
            for (boolean cell : row) {
                if (cell) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String findFirstTrueCell(boolean[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return "No true";
        }
        for (int n = 0; n < matrix.length; n++) {
            for (int m = 0; m < matrix[n].length; m++) {
                if (matrix[n][m]) {
                    return "Row: " + n + ", Column: " + m;
                }
            }
        }
        return "No true";
    }
    public static int countTrueRow(boolean[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }
        for (boolean[] row : matrix) {
            int count = 0;
            for (boolean cell : row) {
                if (cell) {
                    count++;
                }
            }
            if (count > 0) {
                return count;
            }
        }

        return 0;
    }

    private static boolean[][] getSampleMatrix() {
        boolean[][] matrix = new boolean[3][3];
        matrix[2][1] = true;
        matrix[2][2] = true;
        return matrix;
    }
}
