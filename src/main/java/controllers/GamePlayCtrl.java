package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import models.Player;

public interface GamePlayCtrl {
    void twoPlayer();

    void onePlayer(String player1, String player2);

    boolean over();

    void set(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException;

    Player getWinner();

    Player[] getBoard();

    void setup();

    void reset();
}
