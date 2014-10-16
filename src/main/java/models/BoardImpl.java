package models;

import java.util.Arrays;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class BoardImpl implements Board {
    private final Player[] board;
    private final int height;
    private final int width;
    private boolean winner;

    public BoardImpl(int height, int width) {
        this.height = height;
        this.width = width;
        board = new Player[height * width];
    }

    @Override
    public void set(Player player) {
        board[(player.getX() * width) + player.getY()] = player;
        if (isWinner(player)) winner = true;
    }

    @Override
    public int getNumOfPieces() {
        return (int) Arrays.stream(board).filter(player -> player != null).count();
    }

    @Override
    public boolean isVacant(int x, int y) {
        return board[(x * width) + y] == null;
    }

    @Override
    public Player get(int x, int y) {
        return board[(x * width) + y];
    }

    @Override
    public boolean winner() {
        return winner;
    }

    @Override
    public boolean full() {
        return getNumOfPieces() == (height * width);
    }

    private boolean isWinner(Player player) {
        boolean result = check(row(player)) || check(column(player));
        if (!result) {
            if (leftDiagonallyPlaced(player)) {
                result = check(leftDiagonal(player));
            }
            if (rightDiagonallyPlaced(player)) {
                result = check(rightDiagonal(player));
            }
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
        return player.getX() == width - 1 && player.getY() == 0;
    }

    private boolean bottomLeft(Player player) {
        return player.getX() == 0 && player.getY() == width - 1;
    }

    private boolean bottomRight(Player player) {
        return player.getX() == width - 1 && player.getY() == width - 1;
    }

    private boolean topLeft(Player player) {
        return player.getX() == 0 && player.getY() == 0;
    }

    private boolean center(Player player) {
        int x = player.getX();
        int y = player.getY();
        return x > 0 && x < width - 1 && y > 0 && y < width - 1;
    }

    private boolean check(IntPredicate predicate) {
        return width == IntStream.range(0, width).
                filter(predicate).count();
    }

    private IntPredicate rightDiagonal(Player player) {
        return i -> get(i, (width - 1) - i) == player;
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
}