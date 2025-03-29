package collections.sorting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NumberString implements Comparable<NumberString> {
    public String initialValue;
    public List<List<Character>> streaks;

    public NumberString(String input) {

        initialValue = input;
        List<Character> characters = new ArrayList<>();
        for (Character c : input.toCharArray()) {
            characters.add(c);

        }
        Collections.sort(characters);
        streaks = getStreakList(characters);
        Collections.sort(streaks, new LengthAndElementReverseComparator());
    }

    public String initialValue() {

        return initialValue;
    }

    public static List<List<Character>> getStreakList(List<Character> characters) {
        if (characters.isEmpty()) {
            return List.of();
        }
        List<List<Character>> streak = new ArrayList<>();

        Character previous = null;
        for (Character character : characters) {
            if (!character.equals(previous)) {
                streak.add(new ArrayList<>());
            }

            streak.getLast().add(character);
            previous = character;
        }


        return streak;
    }

    @Override
    public int compareTo(NumberString other) {
        int length = Math.min(streaks.size(), other.streaks.size());
        for (int i = 0; i < length; i++) {
            List<Character> a = streaks.get(i);
            List<Character> b = other.streaks.get(i);

            if (a.size() != b.size()) {
                return a.size() - b.size();
            }
            else if (a.getFirst() != b.getFirst()) {
                return a.getFirst() - b.getFirst();
            }
        }
        return streaks.size() - other.streaks.size();
    }
}