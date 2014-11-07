package tictactoe;

import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;

import java.util.List;

public interface Game {

    void set(int row, int column) throws NotVacantException, OutOfBoundsException;

    Character[] getBoard();

    Character getWinner();

    java.util.Set<List<Integer>> getVacancies();

    Game copy();

    void setup(Character piece, int side);

    boolean isOver();
}
