package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import models.Player;

public interface PlayerCtrl {
    void setupTwoPlayer();

    void setupOnePlayer(String pieceOne, String pieceTwo);

    Player getPlayer(Player[] board);

    int playerCount();

    Player getComputerPlayer(Player[] board) throws NotVacantException, OutOfBoundsException;
}
