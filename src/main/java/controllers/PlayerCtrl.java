package controllers;

import models.ComputerPlayer;
import models.Player;

public interface PlayerCtrl {
    void setupTwoPlayer();

    void setupOnePlayer(String pieceOne, String pieceTwo);

    Player getPlayer(Player[] board);

    int playerCount();

    ComputerPlayer getComputerPlayer(Player[] board);
}
