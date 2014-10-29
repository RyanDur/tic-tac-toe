package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
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
    public void set(int row, int column, String piece) throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        if (!validTurn(piece)) throw new OutOfTurnException();
        if (get(row, column) != null) throw new NotVacantException();
        board[calc(row, column)] = piece;
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
    public String get(Integer row, Integer column) throws OutOfBoundsException {
        if (outOfBounds(row, column)) throw new OutOfBoundsException();
        return board[calc(row, column)];
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
        return check(row(piece, row)) ||
                check(column(piece, column)) ||
                check(leftDiagonal(piece)) ||
                check(rightDiagonal(piece));
    }

    private boolean check(IntPredicate vector) {
        return side == IntStream.range(0, side).
                filter(vector).count();
    }

    private IntPredicate rightDiagonal(String piece) {
        return index -> piece.equals(board[calc(index, (side - 1) - index)]);
    }

    private IntPredicate leftDiagonal(String piece) {
        return index -> piece.equals(board[calc(index, index)]);
    }

    private IntPredicate column(String piece, int column) {
        return index -> piece.equals(board[calc(index, column)]);
    }

    private IntPredicate row(String piece, int row) {
        return index -> piece.equals(board[calc(row, index)]);
    }

    private int calc(int x, int y) {
        return (x * side) + y;
    }

    private boolean validTurn(String piece) {
        int numOfPieces = getNumberOfPieces();
        return (numOfPieces % 2 == 0 && piece.equals(constants.GAME_PIECE_ONE)) ||
                (numOfPieces % 2 != 0 && piece.equals(constants.GAME_PIECE_TWO));
    }

    private boolean outOfBounds(Integer row, Integer column) {
        return row > side-1 || column > side-1 || row < 0 || column < 0;
    }
}
