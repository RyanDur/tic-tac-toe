package models;

import exceptions.NotVacantException;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class GameImpl implements Game {
    private Player[] board;
    private final int side;
    private Player winner;

    public GameImpl(int side) {
        this.side = side;
        board = new Player[side * side];
    }

    @Override
    public void set(Player player) throws NotVacantException {
        if (!vacant(player.getX(), player.getY())) throw new NotVacantException();
        board[calculate(player.getX(), player.getY())] = player;
        if (isWinner(player)) winner = player;
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public boolean full() {
        return getNumOfPieces() == (side * side);
    }

    @Override
    public Player[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    @Override
    public int getNumOfPieces() {
        return (int) Arrays.stream(board).filter(player -> player != null).count();
    }

    private Player get(int x, int y) {
        return board[calculate(x, y)];
    }

    private boolean vacant(int x, int y) {
        return get(x, y) == null;
    }

    private int calculate(int x, int y) {
        return (x * side) + y;
    }

    private boolean isWinner(Player player) {
        boolean result = check(row(player)) || check(column(player));
        if (!result && leftDiagonallyPlaced(player)) {
            result = check(leftDiagonal(player));
        }
        if (!result && rightDiagonallyPlaced(player)) {
            result = check(rightDiagonal(player));
        }
        return result;
    }

    private boolean leftDiagonallyPlaced(Player player) {
        return center(player) || topLeft(player) || bottomRight(player);
    }

    private boolean rightDiagonallyPlaced(Player player) {
        return center(player) || bottomLeft(player) || topRight(player);
    }

    private boolean topRight(Player player) {
        return player.getX() == side - 1 && player.getY() == 0;
    }

    private boolean bottomLeft(Player player) {
        return player.getX() == 0 && player.getY() == side - 1;
    }

    private boolean bottomRight(Player player) {
        return player.getX() == side - 1 && player.getY() == side - 1;
    }

    private boolean topLeft(Player player) {
        return player.getX() == 0 && player.getY() == 0;
    }

    private boolean center(Player player) {
        int x = player.getX();
        int y = player.getY();
        return x > 0 && x < side - 1 && y > 0 && y < side - 1;
    }

    private boolean check(IntPredicate predicate) {
        return side == IntStream.range(0, side).
                filter(predicate).count();
    }

    private IntPredicate rightDiagonal(Player player) {
        return i -> get(i, (side - 1) - i) == player;
    }

    private IntPredicate leftDiagonal(Player player) {
        return i -> get(i, i) == player;
    }

    private IntPredicate column(Player player) {
        return i -> get(i, player.getY()) == player;
    }

    private IntPredicate row(Player player) {
        return i -> get(player.getX(), i) == player;
    }

    protected void setBoard(Player[] players) {
        board = players;
    }
}