package tictactoe;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;
import tictactoe.lang.Constants;

import java.util.List;

@Singleton
public class GameImpl implements Game {
    private ComputerPlayer computer;
    private Board board;

    @Inject
    public GameImpl(Board board, ComputerPlayer computer) {
        this.board = board;
        this.computer = computer;
    }

    @Override
    public void setup(Character piece) {
        computer.setPiece(piece);
        board.setup(Constants.SIDE);
        if (computersTurn()) computerMove();
    }

    @Override
    public boolean over() {
        return board.gameOver();
    }

    @Override
    public void set(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        board.set(row, column, getPiece());
        if (!over() && computersTurn()) computerMove();
    }

    @Override
    public Character getWinner() {
        return board.getWinner();
    }

    @Override
    public Character[] getBoard() {
        return board.getBoard();
    }

    private Character getPiece() {
        return board.numOfPieces() % 2 == 0 ? Constants.GAME_PIECE_ONE : Constants.GAME_PIECE_TWO;
    }

    private boolean computersTurn() {
        return getPiece().equals(computer.getPiece());
    }

    private void computerMove() {
        try {
            List<Integer> move = computer.calculateBestMove(board);
            board.set(move.get(0), move.get(1), computer.getPiece());
        } catch (OutOfBoundsException | NotVacantException | OutOfTurnException e) {
            e.printStackTrace();
        }
    }
}
