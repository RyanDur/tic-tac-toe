package models;

import exceptions.NotVacantException;

public interface Board {

    void set(int row, int column, Player player) throws NotVacantException;

    void setBoard(Player[] board);

    Player[] getBoard();

    Player getWinner();
}
