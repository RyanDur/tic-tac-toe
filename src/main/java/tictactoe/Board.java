package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;

import java.util.List;

public interface Board {

    void set(int row, int column, String player) throws NotVacantException, OutOfTurnException, OutOfBoundsException;

    String[] getBoard();

    String getWinner();

    List<List<Integer>> getVacancies();

    int numOfPieces();

    Board copy();

    void setup(int side);
}
