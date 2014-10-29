package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;

public interface Board {

    void set(int row, int column, String player) throws NotVacantException, OutOfTurnException, OutOfBoundsException;

    void setBoard(String[] board);

    String[] getBoard();

    String getWinner();

    String get(Integer row, Integer column) throws OutOfBoundsException;

    boolean full();

    int getNumberOfPieces();
}
