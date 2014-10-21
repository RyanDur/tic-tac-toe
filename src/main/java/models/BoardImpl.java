package models;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardImpl implements Board {
    private final int side;
    private Player[] board;
    private Integer[] lastMove;

    public BoardImpl(int side) {
        this.board = new Player[side * side];
        this.side = side;
    }

    @Override
    public void set(int row, int column, Player player) {
        lastMove = new Integer[]{row, column};
        board[calculate(row, column)] = player;
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

    @Override
    public boolean isWinner(int row, int column, Player player) {
        boolean result = check(row(board, player, row)) ||
                check(column(board, player, column));
        if (!result && leftDiagonallyPlaced(row, column))
            result = check(leftDiagonal(board, player));
        if (!result && rightDiagonallyPlaced(row, column))
            result = check(rightDiagonal(board, player));
        return result;
    }

    @Override
    public List<Integer[]> getVacancies() {
        List<Integer> list = IntStream.range(0, board.length).
                filter(index -> board[index] == null).boxed().
                collect(Collectors.toList());
        return list.stream().
                map(num -> new Integer[]{calcRow(num), calcColumn(num)}).
                collect(Collectors.toList());
    }

    @Override
    public Integer[] lastMove() {
        return lastMove;
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

    private int calcColumn(int vacancy) {
        return vacancy - (calcRow(vacancy) * side);
    }

    private int calcRow(int vacancy) {
        int row = 0;
        while (vacancy >= side) {
            vacancy -= side;
            row++;
        }
        return row;
    }
}
