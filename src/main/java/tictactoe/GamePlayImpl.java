package tictactoe;

import com.google.inject.Inject;
import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;
import tictactoe.lang.Constants;

import java.util.List;

public class GamePlayImpl implements GamePlay {
    private ComputerPlayer computer;
    private Game game;

    @Inject
    public GamePlayImpl(Game game, ComputerPlayer computer) {
        this.game = game;
        this.computer = computer;
    }

    @Override
    public void setup(Character piece) {
        computer.setPiece(piece);
        game.setup(Constants.SIDE);
        if (computersTurn()) computerMove();
    }

    @Override
    public boolean isOver() {
        return game.isOver();
    }

    @Override
    public void set(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.set(row, column, getPiece());
        if (!isOver() && computersTurn()) computerMove();
    }

    @Override
    public Character getWinner() {
        return game.getWinner();
    }

    @Override
    public Character[] getBoard() {
        return game.getBoard();
    }

    private Character getPiece() {
        return game.numOfPieces() % 2 == 0 ? Constants.GAME_PIECE_ONE : Constants.GAME_PIECE_TWO;
    }

    private boolean computersTurn() {
        return getPiece().equals(computer.getPiece());
    }

    private void computerMove() {
        try {
            List<Integer> move = computer.getMove(game);
            set(move.get(0), move.get(1));
        } catch (OutOfBoundsException | NotVacantException | OutOfTurnException e) {
            e.printStackTrace();
        }
    }
}
