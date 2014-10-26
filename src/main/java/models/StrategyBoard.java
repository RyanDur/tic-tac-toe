package models;

import java.util.List;
import java.util.Optional;

public interface StrategyBoard extends Board {
    List<Integer[]> getVacancies();

    Optional<Integer[]> winningMove(Player player);

    List<Integer[]> filterMoves(Player player);

    boolean detectCatsGame(Player player, Player opponent);
}
