package collections.simulator;

import java.util.*;
import java.util.stream.Collectors;

public class Hand implements Iterable<Card>, Comparable<Hand> {
    private final List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    public HandType getHandType() {
        final Map<Card.CardValue, Integer> valueCounts = getValueCounts();
        final List<Integer> counts = getSortedCounts(valueCounts);

        if (isFlush() && isStraight()) {
            return HandType.STRAIGHT_FLUSH;
        }
        if (hasCount(counts, 4)) {
            return HandType.FOUR_OF_A_KIND;
        }
        if (isFullHouse(counts)) {
            return HandType.FULL_HOUSE;
        }
        if (isFlush()) {
            return HandType.FLUSH;
        }
        if (isStraight()) {
            return HandType.STRAIGHT;
        }
        if (hasCount(counts, 3)) {
            return HandType.TRIPS;
        }
        if (isTwoPairs(counts)) {
            return HandType.TWO_PAIRS;
        }
        if (hasCount(counts, 2)) {
            return HandType.ONE_PAIR;
        }
        return HandType.HIGH_CARD;
    }

    private boolean isFlush() {
        return cards.stream().map(Card::getSuit).distinct().count() == 1;
    }

    private boolean isStraight() {
        if (cards.size() != 5) {
            return false;
        }

        List<Card> sorted = getSortedCards();

        boolean normalStraight = true;
        for (int i = 1; i < sorted.size(); i++) {
            if (sorted.get(i).getValue().ordinal() != sorted.get(i-1).getValue().ordinal() + 1) {
                normalStraight = false;
                break;
            }
        }
        if (normalStraight) {
            return true;
        }

        return isWheelStraight(sorted);
    }

    private boolean isWheelStraight(List<Card> sortedCards) {
        return sortedCards.get(0).getValue() == Card.CardValue.S2 &&
                sortedCards.get(1).getValue() == Card.CardValue.S3 &&
                sortedCards.get(2).getValue() == Card.CardValue.S4 &&
                sortedCards.get(3).getValue() == Card.CardValue.S5 &&
                sortedCards.get(4).getValue() == Card.CardValue.A;
    }

    private Map<Card.CardValue, Integer> getValueCounts() {
        Map<Card.CardValue, Integer> counts = new HashMap<>();
        for (Card card : cards) {
            counts.merge(card.getValue(), 1, Integer::sum);
        }
        return counts;
    }

    private List<Integer> getSortedCounts(Map<Card.CardValue, Integer> valueCounts) {
        List<Integer> counts = new ArrayList<>(valueCounts.values());
        counts.sort(Collections.reverseOrder());
        return counts;
    }

    private boolean hasCount(List<Integer> counts, int target) {
        return !counts.isEmpty() && counts.get(0) == target;
    }

    private boolean isFullHouse(List<Integer> counts) {
        return counts.size() >= 2 && counts.get(0) == 3 && counts.get(1) == 2;
    }

    private boolean isTwoPairs(List<Integer> counts) {
        return counts.size() >= 2 && counts.get(0) == 2 && counts.get(1) == 2;
    }

    private List<Card> getSortedCards() {
        return cards.stream()
                .sorted()
                .collect(Collectors.toList());
    }

    public boolean contains(Card card) {
        return cards.contains(card);
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public Iterator<Card> iterator() {
        return cards.iterator();
    }

    @Override
    public int compareTo(Hand other) {
        int typeCompare = this.getHandType().compareTo(other.getHandType());
        if (typeCompare != 0) {
            return typeCompare;
        }
        List<Card> thisSorted = this.getSortedCards();
        List<Card> otherSorted = other.getSortedCards();
        if (this.getHandType() == HandType.STRAIGHT) {
            boolean thisWheel = isWheelStraight(thisSorted);
            boolean otherWheel = isWheelStraight(otherSorted);

            if (thisWheel && !otherWheel) {
                return -1;
            } else if (!thisWheel && otherWheel) {
                return 1;
            }
        }
        for (int i = thisSorted.size() - 1; i >= 0; i--) {
            int cardCompare = thisSorted.get(i).compareTo(otherSorted.get(i));
            if (cardCompare != 0) {
                return cardCompare;
            }
        }
        return 0;
    }
}