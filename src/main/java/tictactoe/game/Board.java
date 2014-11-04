package tictactoe.game;

import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;

import java.util.List;

public interface Board {

    void set(int row, int column, Character player) throws NotVacantException, OutOfTurnException, OutOfBoundsException;

    Character[] getBoard();

    Character getWinner();

    java.util.Set<List<Integer>> getVacancies();

    int numOfPieces();

    Board copy();

    void setup(int side);

    boolean gameOver();
}
