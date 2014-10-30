package models;

import tictactoe.Board;

import java.util.Optional;

public interface StrategyGameCtrl {
    void setBoard(Board board);

    Optional<Integer[]> findWinningMove(String computer);

    Optional<Integer[]> getBestMove(String computer, String opponent);
}
