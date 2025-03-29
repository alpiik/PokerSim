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
        if (isStraightFlush()) {
            return HandType.STRAIGHT_FLUSH;
        }
        else if (isFourOfAKind()) {
            return HandType.FOUR_OF_A_KIND;
        }
        else if (isFullHouse()) {
            return HandType.FULL_HOUSE;
        }
        else if (isFlush()) {
            return HandType.FLUSH;
        }
        else if (isStraight()) {
            return HandType.STRAIGHT;
        }
        else if (isTrips()) {
            return HandType.TRIPS;
        }
        else if (isTwoPairs()) {
            return HandType.TWO_PAIRS;
        }
        else if (isOnePair()) {
            return HandType.ONE_PAIR;
        }
        else {
            return HandType.HIGH_CARD;
        }
    }

    private boolean isStraightFlush() {
        return isFlush() && isStraight();
    }

    private boolean isFourOfAKind() {
        return getValueCounts().values().stream().anyMatch(count -> count == 4);
    }

    private boolean isFullHouse() {
        Map<Card.CardValue, Integer> counts = getValueCounts();
        return counts.containsValue(3) && counts.containsValue(2);
    }

    private boolean isFlush() {
        return cards.stream().map(Card::getSuit).distinct().count() == 1;
    }

    private boolean isStraight() {
        if (cards.size() != 5) {
            return false;
        }
        List<Card> sorted = getSortedCards();
        return isNormalStraight(sorted) || isWheelStraight(sorted);
    }

    private boolean isNormalStraight(List<Card> sorted) {
        for (int i = 1; i < sorted.size(); i++) {
            if (sorted.get(i).getValue().ordinal() != sorted.get(i-1).getValue().ordinal() + 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isWheelStraight(List<Card> sorted) {
        return sorted.get(0).getValue() == Card.CardValue.S2 &&
                sorted.get(1).getValue() == Card.CardValue.S3 &&
                sorted.get(2).getValue() == Card.CardValue.S4 &&
                sorted.get(3).getValue() == Card.CardValue.S5 &&
                sorted.get(4).getValue() == Card.CardValue.A;
    }

    private boolean isTrips() {
        return getValueCounts().values().stream().anyMatch(count -> count == 3);
    }

    private boolean isTwoPairs() {
        return getValueCounts().values().stream().filter(count -> count == 2).count() == 2;
    }

    private boolean isOnePair() {
        return getValueCounts().values().stream().anyMatch(count -> count == 2);
    }

    private Map<Card.CardValue, Integer> getValueCounts() {
        Map<Card.CardValue, Integer> counts = new HashMap<>();
        for (Card card : cards) {
            counts.merge(card.getValue(), 1, Integer::sum);
        }
        return counts;
    }

    private List<Card> getSortedCards() {
        return cards.stream()
                .sorted()
                .collect(Collectors.toList());
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
            }
            if (!thisWheel && otherWheel) {
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
}