package models;

import models.GameTree;

import java.util.List;
import java.util.Optional;

public interface StrategyBoardCtrl {
    void setBoard(int side, String[] board);

    GameTree getTree(String player, String opponent, Integer[] move);

    Optional<Integer[]> winningMove(String player);

    List<Integer[]> filterMoves(String computer);

    boolean boardEmpty();

    Optional<Integer[]> getCorner();

    boolean toFewPieces();

    Optional<Integer[]> centerOrCorner();

    boolean noBest(Optional<Integer[]> move);

    Optional<Integer[]> anyMove();
}
