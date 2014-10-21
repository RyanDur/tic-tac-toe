package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

import java.util.List;
import java.util.Optional;

public interface StrategyGame extends Game {
    boolean boardEmpty();

    Optional<Integer[]> findWinningMove(Player player);

    Optional<Integer[]> findBestMove(Player player, Player opponent) throws NotVacantException, OutOfBoundsException;

    List<Integer[]> filterMoves(Player player);
}
