package models;

import exceptions.NotVacantException;

public interface Board {

    void set(int row, int column, String player) throws NotVacantException;

    void setBoard(String[] board);

    String[] getBoard();

    String getWinner();

    String get(Integer row, Integer column);

    boolean full();

    int getNumberOfPieces();
}
