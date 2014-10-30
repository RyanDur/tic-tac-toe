package tictactoe;

import java.util.Optional;

public interface ComputerAI {
    void setBoard(Board board);

    Optional<Integer[]> findWinningMove(String piece);

    Optional<Integer[]> getBestMove(String piece, String opponent);
}
