package models;

import java.util.Optional;

public interface StrategyGame extends Game {
    Optional<Integer[]> findWinningMove(Player player);

    Optional<Integer[]> getBestMove(Player computer, Player human);
}
