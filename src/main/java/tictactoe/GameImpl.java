package tictactoe;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;

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
    public void setup() {
        board.setup(constants.SIDE);
        if (computersTurn()) computerMove();
    }

    @Override
    public void reset() {
        setComputer(null);
    }

    @Override
    public void setComputer(Character piece) {
        computer.setPiece(piece);
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
        return board.numOfPieces() % 2 == 0 ? constants.GAME_PIECE_ONE : constants.GAME_PIECE_TWO;
    }

    private boolean computersTurn() {
        return getPiece().equals(computer.getPiece());
    }

    private void computerMove() {
        board = computer.calculateBestMove(board);
    }
}
