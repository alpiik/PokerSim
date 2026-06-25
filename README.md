# Poker Odds Calculator

A self-contained web page that calculates heads-up Texas Hold'em winning odds using a Monte Carlo simulation. No build step, no server, no dependencies beyond a CDN-hosted copy of Vue.

## Usage

Open `poker-odds.html` in any modern browser.

1. Pick 2 hole cards for Player 1 and Player 2 (value + suit for each).
2. Optionally fill in community cards (flop/turn/river). Leave them blank to simulate from preflop.
3. Set the number of iterations (more iterations = more accurate, but slower).
4. Click **Calculate Odds**.

The page reports:
- Win / tie / win percentages for each player
- A breakdown of how often each player ends up with a given final hand type (pair, flush, full house, etc.)

Each card can only be used once across both hands and the community cards — a standard 52-card deck has exactly one of each card (e.g. only one 2♣), so picking the same card twice will show a "Duplicate card" error.

## How it works

`poker-odds.html` contains everything:
- A small Vue 3 app (loaded from cdnjs) for the UI — card pickers, controls, and results display.
- A plain-JS poker hand evaluator (best 5-of-7 cards) and Monte Carlo dealer that randomly fills in the remaining deck thousands of times to estimate equity.

Styling lives in `styles/poker-odds.css`.

## Project structure

```
poker-odds.html       Main page (Vue UI + odds calculation logic)
styles/poker-odds.css Styling for the page
src/                  Java implementation of the poker hand model
  Card.java           A single playing card (value + suit)
  Hand.java           A hand of cards; determines hand type (pair, flush, etc.)
  HandType.java       Enum of poker hand rankings
  Helpers.java         Utility helpers
  Simulator.java       Java-side Monte Carlo simulator (calculateProbabilities / getWinningOdds)
  HandTests.java       Unit tests for Hand
```

`Simulator.java` and the JavaScript in `poker-odds.html` both implement the same Monte Carlo approach independently:
- `calculateProbabilities()` deals random 5-card hands from a full deck and returns the percentage breakdown by hand type.
- `getWinningOdds(hand1, hand2)` deals a random 5-card community board, lets each hand pick its best 5-of-7 cards, and returns the percentage of the time hand1 wins (ties split the pot).
