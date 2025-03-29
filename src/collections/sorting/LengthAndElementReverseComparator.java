package collections.sorting;

import java.util.Comparator;
import java.util.List;

public class LengthAndElementReverseComparator implements Comparator<List<Character>> {
    @Override
    public int compare(List<Character> a, List<Character> b) {
        int lengthCompare = Integer.compare(b.size(), a.size());
        if (lengthCompare != 0) {
            return lengthCompare;
        }
        return Character.compare(b.get(0), a.get(0));
    }
}