package models;

import java.util.Arrays;

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
        return row(x, player) || column(y, player) || leftDiagonal(player) || rightDiagonal(player);
    }

    private boolean rightDiagonal(Player player) {
        int count = 0;
        for(int i = width-1; i < board.length; i += width-1) {
            if(board[i] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == width;
    }

    private boolean leftDiagonal(Player player) {
        int count = 0;
        for(int i = 0; i < board.length; i += width+1) {
            if(board[i] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == width;
    }

    private boolean column(int y, Player player) {
        int count = 0;
        for(int i = 0; i < width; i++) {
            if(board[(i * width) + y] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == width;
    }

    private boolean row(int x, Player player) {
        int count = 0;
        for(int y = (x * width); y < (width+ (x * width)); y++) {
            if(board[y] == player) {
                count++;
            } else {
                break;
            }
        }
        return count == width;
    }
}
