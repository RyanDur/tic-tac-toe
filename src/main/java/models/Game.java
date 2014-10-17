package models;

import exceptions.NotVacantException;

public interface Game {
    void set(Player player) throws NotVacantException;

    int getNumOfPieces();

    Player getWinner();

    boolean full();
}
