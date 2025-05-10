package gol;

import java.util.HashSet;
import java.util.Set;

public class Game {

    private Set<Point> liveCells;

    public Game() {
        this.liveCells = new HashSet<>();
    }

    public void markAlive(int x, int y) {
        liveCells.add(new Point(x, y));
    }

    public boolean isAlive(int x, int y) {
        return liveCells.contains(new Point(x, y));
    }

    public void toggle(int x, int y) {
        Point cell = new Point(x, y);
        if (liveCells.contains(cell)) {
            liveCells.remove(cell);
        } else {
            liveCells.add(cell);
        }
    }

    public Integer getNeighbourCount(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) {
                    continue;
                }

                if (liveCells.contains(new Point(x + dx, y + dy))) {
                    count++;
                }
            }
        }
        return count;
    }

    public void nextFrame() {
        Set<Point> nextLiveCells = new HashSet<>();
        Set<Point> candidateCells = new HashSet<>();

        for (Point cell : liveCells) {
            candidateCells.add(cell);
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    if (dx == 0 && dy == 0) {
                        continue;
                    }
                    candidateCells.add(new Point(cell.x() + dx, cell.y() + dy));
                }
            }
        }

        for (Point cell : candidateCells) {
            boolean isCurrentlyAlive = liveCells.contains(cell);
            int neighborCount = getNeighbourCount(cell.x(), cell.y());

            if (nextState(isCurrentlyAlive, neighborCount)) {
                nextLiveCells.add(cell);
            }
        }

        this.liveCells = nextLiveCells;
    }

    public void clear() {
        liveCells.clear();
    }

    public boolean nextState(boolean isLiving, int neighborCount) {
        if (isLiving) {
            return neighborCount >= 2 && neighborCount <= 3;
        } else {
            return neighborCount == 3;
        }
    }
}