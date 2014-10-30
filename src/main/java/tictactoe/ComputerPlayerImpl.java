package tictactoe;

import com.google.inject.Inject;
import lang.constants;

import java.util.Optional;
import java.util.function.Consumer;

public class ComputerPlayerImpl implements ComputerPlayer {
    private ComputerAI ai;
    private int row;
    private int column;
    private String piece;
    private String opponent;

    @Inject
    public ComputerPlayerImpl(ComputerAI ai) {
        this.ai = ai;
    }

    @Override
    public void calculateBestMove(Board board) {
        ai.setBoard(board);
        Optional<Integer[]> found = ai.findWinningMove(piece);
        if (!found.isPresent()) found = ai.findWinningMove(opponent);
        if (!found.isPresent()) found = ai.getBestMove(piece, opponent);
        found.ifPresent(setMove());
    }

    @Override
    public void setPiece(String piece) {
        this.piece = piece;
        setOpponent(piece);
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public String getPiece() {
        return piece;
    }

    private Consumer<Integer[]> setMove() {
        return vacancy -> {
            row = vacancy[0];
            column = vacancy[1];
        };
    }

    private void setOpponent(String piece) {
        opponent = piece.equals(constants.GAME_PIECE_ONE) ?
                constants.GAME_PIECE_TWO : constants.GAME_PIECE_ONE;
    }
}
