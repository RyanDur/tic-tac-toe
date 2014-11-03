package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;

public interface Game {
    void setup();

    void reset();

    void setComputer(Character piece);

    boolean over();

    void set(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException;

    Character getWinner();

    Character[] getBoard();
}
