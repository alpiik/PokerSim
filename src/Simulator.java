package collections.simulator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Simulator {

    private static final int CARDS_ON_BOARD = 5;

    private final long iterations;
    private final Random random = new Random();

    public Simulator(double iterations) {
        this.iterations = Math.round(iterations);
    }

    /**
     * Deals a random 5-card hand from a full deck, `iterations` times, and
     * returns what percentage of the time each hand type occurred.
     */
    public Map<HandType, Double> calculateProbabilities() {
        Map<HandType, Long> counts = new EnumMap<>(HandType.class);
        for (HandType type : HandType.values()) {
            counts.put(type, 0L);
        }

        List<Card> fullDeck = buildDeck(List.of());

        for (long i = 0; i < iterations; i++) {
            List<Card> deck = new ArrayList<>(fullDeck);
            Collections.shuffle(deck, random);

            Hand hand = new Hand();
            for (int c = 0; c < CARDS_ON_BOARD; c++) {
                hand.addCard(deck.get(c));
            }

            HandType type = hand.getHandType();
            counts.put(type, counts.get(type) + 1);
        }

        Map<HandType, Double> probabilities = new EnumMap<>(HandType.class);
        for (Map.Entry<HandType, Long> entry : counts.entrySet()) {
            probabilities.put(entry.getKey(), entry.getValue() * 100.0 / iterations);
        }
        return probabilities;
    }

    /**
     * Texas Hold'em heads-up equity: deals a random 5-card community board
     * `iterations` times, lets each player pick their best possible 5-card
     * hand out of their hole cards plus the board, and returns the
     * percentage of the time player1's hand wins (ties split the pot).
     */
    public double getWinningOdds(Hand player1hand, Hand player2hand) {
        List<Card> player1Cards = cardsOf(player1hand);
        List<Card> player2Cards = cardsOf(player2hand);

        List<Card> knownCards = new ArrayList<>(player1Cards);
        knownCards.addAll(player2Cards);

        List<Card> remainingDeck = buildDeck(knownCards);

        double player1Wins = 0;

        for (long i = 0; i < iterations; i++) {
            List<Card> deck = new ArrayList<>(remainingDeck);
            Collections.shuffle(deck, random);

            List<Card> board = deck.subList(0, CARDS_ON_BOARD);

            Hand bestPlayer1 = bestHand(player1Cards, board);
            Hand bestPlayer2 = bestHand(player2Cards, board);

            int comparison = bestPlayer1.compareTo(bestPlayer2);
            if (comparison > 0) {
                player1Wins += 1;
            } else if (comparison == 0) {
                player1Wins += 0.5;
            }
        }

        return player1Wins * 100.0 / iterations;
    }

    private Hand bestHand(List<Card> holeCards, List<Card> board) {
        List<Card> allCards = new ArrayList<>(holeCards);
        allCards.addAll(board);

        Hand best = null;
        for (List<Card> combo : combinationsOf(allCards, CARDS_ON_BOARD)) {
            Hand candidate = new Hand();
            combo.forEach(candidate::addCard);
            if (best == null || candidate.compareTo(best) > 0) {
                best = candidate;
            }
        }
        return best;
    }

    private List<List<Card>> combinationsOf(List<Card> cards, int size) {
        List<List<Card>> result = new ArrayList<>();
        combine(cards, size, 0, new ArrayList<>(), result);
        return result;
    }

    private void combine(List<Card> cards, int size, int start, List<Card> current, List<List<Card>> result) {
        if (current.size() == size) {
            result.add(new ArrayList<>(current));
            return;
        }
        for (int i = start; i < cards.size(); i++) {
            current.add(cards.get(i));
            combine(cards, size, i + 1, current, result);
            current.remove(current.size() - 1);
        }
    }

    private List<Card> buildDeck(List<Card> excluding) {
        List<Card> deck = new ArrayList<>();
        for (Card.CardValue value : Card.CardValue.values()) {
            for (Card.CardSuit suit : Card.CardSuit.values()) {
                Card card = new Card(value, suit);
                if (!excluding.contains(card)) {
                    deck.add(card);
                }
            }
        }
        return deck;
    }

    private List<Card> cardsOf(Hand hand) {
        List<Card> cards = new ArrayList<>();
        for (Card card : hand) {
            cards.add(card);
        }
        return cards;
    }
}
