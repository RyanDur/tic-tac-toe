package tictactoe;

import tictactoe.exceptions.InvalidMoveException;

import java.util.List;
import java.util.Set;

public interface Game {

    void setup(Character piece, int side);

    void set(List<Integer> move) throws InvalidMoveException;

    boolean isOver();

    Character[] getBoard();

    Character getWinner();

    Set<List<Integer>> getVacancies();

    Game copy();
}
