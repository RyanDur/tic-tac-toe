package controllers;

import models.Player;

public interface PlayerCtrl {
    Player[] setupTwoPlayer();

    Player[] setupOnePlayer(String pieceOne, String pieceTwo);
}
