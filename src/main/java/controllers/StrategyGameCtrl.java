package controllers;

import models.GameTree;
import models.Player;

import java.util.List;
import java.util.Optional;

public interface StrategyGameCtrl {
    void setBoard(int side, Player[] board);

    GameTree getTree(Player player, Player opponent, Integer[] move);

    Optional<Integer[]> winningMove(Player player);

    List<Integer[]> filterMoves(Player computer);

    boolean boardEmpty();

    Optional<Integer[]> getCorner();

    boolean toFewPieces();

    Optional<Integer[]> centerOrCorner();

    boolean noBest(Optional<Integer[]> move);

    Optional<Integer[]> anyMove();
}
