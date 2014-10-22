package models;

import java.util.List;
import java.util.Optional;

public interface StrategyGame extends Game {
    boolean boardEmpty();

    Optional<Integer[]> findWinningMove(Player player);

    List<Integer[]> getBestMove(Player computer, Player human);
}
