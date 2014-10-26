package models;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class BoardImpl implements Board {
    private final int side;
    private Player[] board;
    private Player winner;

    public BoardImpl(int side) {
        this.board = new Player[side * side];
        this.side = side;
    }

    @Override
    public void set(int row, int column, Player player) {
        board[calculate(row, column)] = player;
        if (isWinner(row, column, player)) winner = player;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public Player get(int row, int column) {
        return board[calculate(row, column)];
    }

    @Override
    public void setBoard(Player[] board) {
        this.board = board;
    }

    @Override
    public Player[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    private boolean isWinner(int row, int column, Player player) {
        boolean result = check(row(board, player, row)) ||
                check(column(board, player, column));
        if (!result && leftDiagonallyPlaced(row, column))
            result = check(leftDiagonal(board, player));
        if (!result && rightDiagonallyPlaced(row, column))
            result = check(rightDiagonal(board, player));
        return result;
    }

    private boolean leftDiagonallyPlaced(int x, int y) {
        return center(x, y) || topLeft(x, y) || bottomRight(x, y);
    }

    private boolean rightDiagonallyPlaced(int x, int y) {
        return center(x, y) || bottomLeft(x, y) || topRight(x, y);
    }

    private boolean topRight(int x, int y) {
        return x == side - 1 && y == 0;
    }

    private boolean bottomLeft(int x, int y) {
        return x == 0 && y == side - 1;
    }

    private boolean bottomRight(int x, int y) {
        return x == side - 1 && y == side - 1;
    }

    private boolean topLeft(int x, int y) {
        return x == 0 && y == 0;
    }

    private boolean center(int x, int y) {
        return x > 0 && x < side - 1 && y > 0 && y < side - 1;
    }

    private boolean check(IntPredicate predicate) {
        return side == IntStream.range(0, side).
                filter(predicate).count();
    }

    private IntPredicate rightDiagonal(Player[] board, Player player) {
        return i -> board[calculate(i, (side - 1) - i)] == player;
    }

    private IntPredicate leftDiagonal(Player[] board, Player player) {
        return i -> board[calculate(i, i)] == player;
    }

    private IntPredicate column(Player[] board, Player player, int y) {
        return i -> board[calculate(i, y)] == player;
    }

    private IntPredicate row(Player[] board, Player player, int x) {
        return i -> board[calculate(x, i)] == player;
    }

    private int calculate(int x, int y) {
        return (x * side) + y;
    }
}
