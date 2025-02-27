package junit;

public class Code {


    public static boolean isSpecial(int candidate) {
        if (candidate % 11 == 0) {
            return true;
        }
        int remainder = candidate % 11;
        if (remainder <= 3 || remainder >= 8) {
            return true;
        }
        return false;
    }

    public static int longestStreak(String inputString) {
        if (inputString == null || inputString.isEmpty()) {
            return 0;
        }

        int maksimumStreak = 1;
        int hetkeStreak = 1;

        for (int i = 1; i < inputString.length(); i++) {
            if (inputString.charAt(i) == inputString.charAt(i - 1)) {
                hetkeStreak++;
                if (hetkeStreak > maksimumStreak) {
                    maksimumStreak = hetkeStreak;
                }
            } else {
                hetkeStreak = 1;
            }
        }

        return maksimumStreak;
    }

    public static Character mode(String inputString) {
        if (inputString == null || inputString.length() == 0) {
            return null;
        }
        char mostChar = inputString.charAt(0);
        int maxCount = getCharacterCount(inputString, mostChar);

        for (int i = 1; i < inputString.length(); i++) {
            char currentChar = inputString.charAt(i);
            int currentCount = getCharacterCount(inputString, currentChar);

            if (currentCount > maxCount) {
                maxCount = currentCount;
                mostChar = currentChar;
            }
        }

        return mostChar;
    }

    public static int getCharacterCount(String allCharacters, char targetCharacter) {
        if (allCharacters == null) {
            return 0;
        }
        int count = 0;
        for (int i = 0; i < allCharacters.length(); i++) {
            if (allCharacters.charAt(i) == targetCharacter) {
                count++;
            }
        }
        return count;
    }

    public static int[] removeDuplicates(int[] integers) {
        if (integers == null || integers.length == 0) {
            return new int[0];
        }

        int[] temp = new int[integers.length];
        int count = 0;

        for (int i = 0; i < integers.length; i++) {
            if (!contains(temp, count, integers[i])) {
                temp[count] = integers[i];
                count++;
            }
        }

        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = temp[i];
        }
        return result;
    }

    private static boolean contains(int[] arr, int size, int value) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == value) {
                return true;
            }
        }
        return false;
    }

    public static int sumIgnoringDuplicates(int[] integers) {
        if (integers == null || integers.length == 0) {
            return 0;
        }

        int[] uniqueElements = new int[integers.length];
        int count = 0;
        int sum = 0;

        for (int i = 0; i < integers.length; i++) {
            if (!arrayContains(uniqueElements, count, integers[i])) {
                uniqueElements[count] = integers[i];
                sum += integers[i];
                count++;
            }
        }

        return sum;
    }

    private static boolean arrayContains(int[] arr, int size, int value) {
        for (int i = 0; i < size; i++) {
            if (arr[i] == value) {
                return true;
            }
        }
        return false;
    }


}
