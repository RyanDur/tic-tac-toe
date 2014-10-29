package tictactoe;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.BoardFactory;
import lang.constants;

@Singleton
public class GameImpl implements Game {
    private BoardFactory boardFactory;
    private ComputerPlayer computer;
    private Board board;

    @Inject
    public GameImpl(BoardFactory boardFactory, ComputerPlayer computer) {
        this.boardFactory = boardFactory;
        this.computer = computer;
    }

    @Override
    public void setComputer(String piece) {
        computer.setPiece(piece);
    }

    @Override
    public boolean over() {
        return getWinner() != null || board.full();
    }

    @Override
    public void set(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        board.set(row, column, getPiece());
        if (!over() && computersTurn()) computerMove();
    }

    @Override
    public String getWinner() {
        return board.getWinner();
    }

    @Override
    public String[] getBoard() {
        return board.getBoard();
    }

    @Override
    public void setup() {
        board = boardFactory.createBoard(constants.SIDE);
        if (computersTurn()) computerMove();
    }

    @Override
    public void reset() {
        setup();
    }

    private boolean computersTurn() {
        return getPiece().equals(computer.getPiece());
    }

    private String getPiece() {
        return board.getNumberOfPieces() % 2 == 0 ? constants.GAME_PIECE_ONE : constants.GAME_PIECE_TWO;
    }

    private void computerMove() {
        try {
            computer.calculateBestMove(getBoard());
            board.set(computer.getRow(), computer.getColumn(), computer.getPiece());
        } catch (NotVacantException | OutOfTurnException e) {
            e.printStackTrace();
        }
    }
}
