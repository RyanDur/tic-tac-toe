package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import lang.constants;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class BoardImpl implements Board {
    private final int side;
    private String[] board;
    private String winner;

    public BoardImpl(int side) {
        this.board = new String[side * side];
        this.side = side;
    }

    @Override
    public void set(int row, int column, String piece) throws NotVacantException, OutOfTurnException {
        if (!validTurn(piece)) throw new OutOfTurnException();
        int spot = calculate(row, column);
        if (board[spot] != null) throw new NotVacantException();
        board[spot] = piece;
        if (isWinner(row, column, piece)) winner = piece;
    }

    @Override
    public String getWinner() {
        return winner;
    }

    @Override
    public void setBoard(String[] board) {
        this.board = board;
    }

    @Override
    public String[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    @Override
    public String get(Integer row, Integer column) {
        return board[calculate(row, column)];
    }

    @Override
    public boolean full() {
        return getNumberOfPieces() == board.length;
    }

    @Override
    public int getNumberOfPieces() {
        return (int) Arrays.stream(getBoard())
                .filter(piece -> piece != null)
                .count();
    }

    private boolean isWinner(int row, int column, String piece) {
        return check(row(board, piece, row)) ||
                check(column(board, piece, column)) ||
                check(leftDiagonal(board, piece)) ||
                check(rightDiagonal(board, piece));
    }

    private boolean check(IntPredicate predicate) {
        return side == IntStream.range(0, side).
                filter(predicate).count();
    }

    private IntPredicate rightDiagonal(String[] board, String piece) {
        return i -> piece.equals(board[calculate(i, (side - 1) - i)]);
    }

    private IntPredicate leftDiagonal(String[] board, String piece) {
        return i -> piece.equals(board[calculate(i, i)]);
    }

    private IntPredicate column(String[] board, String piece, int y) {
        return i -> piece.equals(board[calculate(i, y)]);
    }

    private IntPredicate row(String[] board, String piece, int x) {
        return i -> piece.equals(board[calculate(x, i)]);
    }

    private int calculate(int x, int y) {
        return (x * side) + y;
    }

    private boolean validTurn(String piece) {
        int numOfPieces = getNumberOfPieces();
        boolean result = true;

        if (numOfPieces == 0) {
            if (piece.equals(constants.GAME_PIECE_TWO)) result = false;
        } else if (numOfPieces % 2 == 0) {
            if (piece.equals(constants.GAME_PIECE_TWO)) result = false;
        } else if (piece.equals(constants.GAME_PIECE_ONE)) result = false;

        return result;
    }
}
