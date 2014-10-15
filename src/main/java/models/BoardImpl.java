package models;

import java.util.Arrays;

public class BoardImpl implements Board {
    private final Player[][] board;
    private final int height;
    private final int width;

    public BoardImpl(int height, int width) {
        this.height = height;
        this.width = width;
        board = new Player[height][width];
    }

    @Override
    public void set(int x, int y, Player player) {
        board[x][y] = player;
    }

    @Override
    public int getNumOfPieces() {
        return (int) Arrays.stream(board).flatMap(arr ->
                Arrays.stream(arr).filter(player -> player != null)).count();
    }

    @Override
    public boolean isVacant(int x, int y) {
        return board[x][y] == null;
    }

    @Override
    public Player get(int x, int y) {
        return board[x][y];
    }

    @Override
    public boolean winner() {
        int matchLeftDiagonal = 0;
        int matchRightDiagonal = 0;
        for (int row = 0; row < width; row++) {
            if (row < width - 1 && leftDiagonal(row)) matchLeftDiagonal += 1;
            if (((width - 1) - row) != row && rightDiagonal(row)) matchRightDiagonal += 1;
            if (matchLeftDiagonal == width - 1  ||
                    matchRightDiagonal == width - 1 ||
                    checkRowColumnForWin(row)) return true;
        }
        return false;
    }

    @Override
    public boolean draw() {
        return getNumOfPieces() == (height*width) && !winner();
    }

    private boolean checkRowColumnForWin(int row) {
        int matchRow = 0;
        int matchColumn = 0;
        for (int column = 0; column < height-1; column++) {
            if (row < width && row(row, column)) matchRow += 1;
            if (column(row, column)) matchColumn += 1;
        }
        return matchRow == width - 1 || matchColumn == height - 1;
    }

    private boolean rightDiagonal(int row) {
        return matching(get(row, (width - 1) - row), get(1, 1));
    }

    private boolean leftDiagonal(int row) {
        return matching(get(row, row), get(row + 1, row + 1));
    }

    private boolean column(int y, int x) {
        return matching(get(x, y), get(x + 1, y));
    }

    private boolean row(int x, int y) {
        return matching(get(x, y), get(x, y + 1));
    }

    private boolean matching(Player player1, Player player2) {
        return player1 != null && player2 != null && player1 == player2;
    }
}
