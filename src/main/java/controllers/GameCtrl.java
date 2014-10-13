package controllers;

import exceptions.OutOfTurnException;
import models.Player;

public interface GameCtrl {
    void setup();

    void setPiece(Player player) throws OutOfTurnException;
}
