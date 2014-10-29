package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;

public interface Board {

    void set(int row, int column, String player) throws NotVacantException, OutOfTurnException;

    void setBoard(String[] board);

    String[] getBoard();

    String getWinner();

    String get(Integer row, Integer column);

    boolean full();

    int getNumberOfPieces();
}
