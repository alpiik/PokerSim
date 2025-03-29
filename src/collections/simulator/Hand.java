package collections.simulator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class Hand implements Iterable<Card>, Comparable<Hand> {

    private List<Card> cards = new ArrayList<>();

    public void addCard(Card card) {
        cards.add(card);
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    private static final Map<Function<Hand, Boolean>, HandType> HAND_RANKINGS = new LinkedHashMap<>();
    static {
        HAND_RANKINGS.put(Hand::isStraightFlush, HandType.STRAIGHT_FLUSH);
        HAND_RANKINGS.put(Hand::isFourOfAKind, HandType.FOUR_OF_A_KIND);
        HAND_RANKINGS.put(Hand::isFullHouse, HandType.FULL_HOUSE);
        HAND_RANKINGS.put(Hand::isFlush, HandType.FLUSH);
        HAND_RANKINGS.put(Hand::isStraight, HandType.STRAIGHT);
        HAND_RANKINGS.put(Hand::isTrips, HandType.TRIPS);
        HAND_RANKINGS.put(Hand::isTwoPairs, HandType.TWO_PAIRS);
        HAND_RANKINGS.put(Hand::isOnePair, HandType.ONE_PAIR);
    }

    public HandType getHandType() {
        for (var entry : HAND_RANKINGS.entrySet()) {
            if (entry.getKey().apply(this)) {
                return entry.getValue();
            }
        }
        return HandType.HIGH_CARD;
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
        List<Card> sorted = getSortedCards();

        // normal straight
        boolean normalStraight = true;
        for (int i = 1; i < sorted.size(); i++) {
            if (sorted.get(i).getValue().ordinal() != sorted.get(i-1).getValue().ordinal() + 1) {
                normalStraight = false;
                break;
            }
        }

        // wheel straight
        boolean wheelStraight = sorted.size() == 5 &&
                sorted.get(0).getValue() == Card.CardValue.S2 &&
                sorted.get(1).getValue() == Card.CardValue.S3 &&
                sorted.get(2).getValue() == Card.CardValue.S4 &&
                sorted.get(3).getValue() == Card.CardValue.S5 &&
                sorted.get(4).getValue() == Card.CardValue.A;

        return normalStraight || wheelStraight;
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
            counts.put(card.getValue(), counts.getOrDefault(card.getValue(), 0) + 1);
        }
        return counts;
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
        for (int i = thisSorted.size() - 1; i >= 0; i--) {
            int cardCompare = thisSorted.get(i).compareTo(otherSorted.get(i));
            if (cardCompare != 0) {
                return cardCompare;
            }
        }
        return 0;
    }
}