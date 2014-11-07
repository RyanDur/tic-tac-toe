package tictactoe;

import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;

import java.util.List;
import java.util.Set;

public interface Game {

    void setup(Character piece, int side);

    void set(int row, int column) throws NotVacantException, OutOfBoundsException;

    boolean isOver();

    Character[] getBoard();

    Character getWinner();

    Set<List<Integer>> getVacancies();

    Game copy();
}
