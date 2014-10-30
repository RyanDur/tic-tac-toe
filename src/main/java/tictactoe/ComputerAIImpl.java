package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;

import java.util.Optional;

public class ComputerAIImpl implements ComputerAI {
    private Board board;

    @Override
    public void setBoard(Board board) {
        this.board = board;
    }

    @Override
    public Optional<Integer[]> findWinningMove(String piece) {
        return board.getVacancies().stream()
                .filter(move -> playMove(piece, move).getWinner() != null)
                .findFirst();
    }

    @Override
    public Optional<Integer[]> getBestMove(String piece, String opponent) {
        return null;
    }

    private Board playMove(String piece, Integer[] move) {
        Board copy = board.copy();
        try {
            copy.set(move[0], move[1], piece);
        } catch (NotVacantException | OutOfBoundsException | OutOfTurnException e) {
            e.printStackTrace();
        }
        return copy;
    }
}
