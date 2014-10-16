package models;

import java.util.Arrays;
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
    public void set(int x, int y, Player player) {
        board[(x * width) + y] = player;
        if (isWinner(x, y, player)) {
            winner = true;
        }
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

    private boolean isWinner(int x, int y, Player player) {
        boolean result = row(x, player) || column(y, player);
        if (!result && diagonallyPlaced(x, y)) {
            result = leftDiagonal(player) || rightDiagonal(player);
        }
        return result;
    }

    private boolean diagonallyPlaced(int x, int y) {
        return center(x, y) || topLeft(x, y) || bottomRight(x, y) || bottomLeft(x, y) || topRight(x, y);
    }

    private boolean topRight(int x, int y) {
        return x == width - 1 && y == 0;
    }

    private boolean bottomLeft(int x, int y) {
        return x == 0 && y == width - 1;
    }

    private boolean bottomRight(int x, int y) {
        return x == width - 1 && y == width - 1;
    }

    private boolean topLeft(int x, int y) {
        return x == 0 && y == 0;
    }

    private boolean center(int x, int y) {
        return x > 0 && x < width - 1 && y > 0 && y < width - 1;
    }

    private boolean rightDiagonal(Player player) {
        return width == IntStream.range(0, width).
                filter(i -> get(i, (width - 1) - i) == player).count();
    }

    private boolean leftDiagonal(Player player) {
        return width == IntStream.range(0, width).
                filter(i -> get(i, i) == player).count();
    }

    private boolean column(int y, Player player) {
        return width == IntStream.range(0, width).
                filter(i -> get(i, y) == player).count();
    }

    private boolean row(int x, Player player) {
        return width == IntStream.range(0, width).
                filter(i -> get(x, i) == player).count();
    }
}
