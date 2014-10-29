package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;

public interface Game {
    void setComputer(String piece);

    boolean over();

    void set(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException;

    String getWinner();

    String[] getBoard();

    void setup();

    void reset();
}
