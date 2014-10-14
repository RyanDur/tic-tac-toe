package models;

public class BoardInpl implements Board {
    private final int height;
    private final int width;
    private final Player[][] board;

    public BoardInpl(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Player[width][height];
    }

    @Override
    public void set(int x, int y, Player player) {
        board[x][y] = player;
    }

    @Override
    public int getNumOfPieces() {
        return 0;
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
