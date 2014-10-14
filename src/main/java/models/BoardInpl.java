package models;

import java.util.Arrays;

public class BoardInpl implements Board {
    private final Player[][] board;

    public BoardInpl(int width, int height) {
        board = new Player[width][height];
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
        return false;
    }

    @Override
    public Player get(int x, int y) {
        return board[x][y];
    }
}
