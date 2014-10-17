package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import models.Player;

public interface GameCtrl {
    void setup();

    void setPiece(Player player) throws OutOfTurnException, NotVacantException;

    boolean gameOver();

    Player getWinner();

    Player[] getBoard();
}
