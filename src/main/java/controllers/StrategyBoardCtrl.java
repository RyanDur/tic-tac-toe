package controllers;

import models.Player;

import java.util.Optional;

public interface StrategyBoardCtrl {
    void setBoard(Player[] board);

    Optional<Integer[]> findWinningMove(Player computer);

    Optional<Integer[]> getBestMove(Player computer, Player opponent);

    void setSide(int side);
}
