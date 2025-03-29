package collections.sorting;

import java.util.*;

public class NumberString implements Comparable<NumberString> {
    public String initialValue;
    public List<List<Character>> streaks;

    public NumberString(String input) {
        initialValue = input;
        List<Character> chars = new ArrayList<>();
        for (char c : input.toCharArray()) {
            chars.add(c);
        }
        this.streaks = getStreakList(chars);
        sortStreaks();
    }

    public String initialValue() {
        return initialValue;
    }

    public static List<List<Character>> getStreakList(List<Character> characters) {
        List<List<Character>> result = new ArrayList<>();
        if (characters.isEmpty()) {
            return result;
        }

        List<Character> currentStreak = new ArrayList<>();
        currentStreak.add(characters.get(0));

        for (int i = 1; i < characters.size(); i++) {
            Character current = characters.get(i);
            if (current.equals(currentStreak.get(0))) {
                currentStreak.add(current);
            } else {
                result.add(currentStreak);
                currentStreak = new ArrayList<>();
                currentStreak.add(current);
            }
        }
        result.add(currentStreak);
        return result;
    }

    private void sortStreaks() {
        streaks.sort(new LengthAndElementReverseComparator());
    }

    @Override
    public int compareTo(NumberString o) {
        int minSize = Math.min(this.streaks.size(), o.streaks.size());
        for (int i = 0; i < minSize; i++) {
            List<Character> thisStreak = this.streaks.get(i);
            List<Character> otherStreak = o.streaks.get(i);
            int lengthCompare = Integer.compare(otherStreak.size(), thisStreak.size());
            if (lengthCompare != 0) {
                return lengthCompare;
            }
            int charCompare = Character.compare(otherStreak.get(0), thisStreak.get(0));
            if (charCompare != 0) {
                return charCompare;
            }
        }
        return Integer.compare(o.streaks.size(), this.streaks.size());
    }
}