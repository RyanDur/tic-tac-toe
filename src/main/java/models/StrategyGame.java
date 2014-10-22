package models;

import java.util.Optional;

public interface StrategyGame extends Game {
    boolean boardEmpty();

    Optional<Integer[]> findWinningMove(Player player);

    Optional<Integer[]> getBestMove(Player computer, Player human);

    Integer[] getCorner();
}
