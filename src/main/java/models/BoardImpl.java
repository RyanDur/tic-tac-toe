package models;

import exceptions.NotVacantException;

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
    public void set(int row, int column, String player) throws NotVacantException {
        int spot = calculate(row, column);
        if (board[spot] != null) throw new NotVacantException();
        board[spot] = player;
        if (isWinner(row, column, player)) winner = player;
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
        return Arrays.stream(getBoard())
                .filter(piece -> piece != null)
                .count() == board.length;
    }

    private boolean isWinner(int row, int column, String player) {
        return check(row(board, player, row)) ||
                check(column(board, player, column)) ||
                check(leftDiagonal(board, player)) ||
                check(rightDiagonal(board, player));
    }

    private boolean check(IntPredicate predicate) {
        return side == IntStream.range(0, side).
                filter(predicate).count();
    }

    private IntPredicate rightDiagonal(String[] board, String player) {
        return i -> player.equals(board[calculate(i, (side - 1) - i)]);
    }

    private IntPredicate leftDiagonal(String[] board, String player) {
        return i -> player.equals(board[calculate(i, i)]);
    }

    private IntPredicate column(String[] board, String player, int y) {
        return i -> player.equals(board[calculate(i, y)]);
    }

    private IntPredicate row(String[] board, String player, int x) {
        return i -> player.equals(board[calculate(x, i)]);
    }

    private int calculate(int x, int y) {
        return (x * side) + y;
    }
}
