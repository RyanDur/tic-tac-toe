package models;

import java.util.Optional;

public interface StrategyGameCtrl {
    void setBoard(String[] board);

    Optional<Integer[]> findWinningMove(String computer);

    Optional<Integer[]> getBestMove(String computer, String opponent);
}
