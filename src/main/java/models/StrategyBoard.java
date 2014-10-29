package models;

import tictactoe.Board;

import java.util.List;
import java.util.Optional;

public interface StrategyBoard extends Board {
    List<Integer[]> getVacancies();

    Optional<Integer[]> winningMove(String player);

    List<Integer[]> filterMoves(String player);
}
