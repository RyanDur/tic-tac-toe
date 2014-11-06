package tictactoe;

import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;

public interface GamePlay {
    void setup(Character piece);

    boolean isOver();

    void set(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException;

    Character getWinner();

    Character[] getBoard();
}
