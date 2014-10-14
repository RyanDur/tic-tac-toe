package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import factories.BoardFactory;
import lang.constants;
import models.Board;
import models.Player;

public class GameCtrlImpl implements GameCtrl {
    private BoardFactory boardFactory;
    private Board board;
    private int height;
    private int width;

    public GameCtrlImpl(BoardFactory boardFactory) {
        height = constants.HEIGHT;
        width = constants.WIDTH;
        this.boardFactory = boardFactory;
    }

    @Override
    public void setup() {
        board = boardFactory.createBoard(height, width);
    }

    @Override
    public void setPiece(Player player) throws OutOfTurnException, NotVacantException {
        if (!isValid(player)) throw new OutOfTurnException();
        if (!board.isVacant(player.getX(), player.getY())) throw new NotVacantException();
        board.set(player.getX(), player.getY(), player);
    }

    @Override
    public boolean gameOver() {
        return rowsColumns();
    }

    private boolean rowsColumns() {
        int matchRow = 0;
        int matchColumn = 0;
        int matchLeftDiagonal = 0;

        for (int row = 0; row < width; row++) {
            if (matching(board.get(row, row), board.get(row+1, row+1))) matchLeftDiagonal += 1;
            for (int column = 0; column < height; column++) {
                if (row < width && row(row, column)) matchRow += 1;
                if (column < height && column(row, column)) matchColumn += 1;
            }
            if (matchRow == width-1 || matchColumn == height-1) return true;
            matchRow = 0;
            matchColumn = 0;
        }
        if (matchLeftDiagonal == width-1) return true;
        
        return false;
    }

    private boolean column(int x, int y) {
        return matching(board.get(y, x), board.get(y + 1, x));
    }

    private boolean row(int x, int y) {
        return matching(board.get(x, y), board.get(x, y + 1));
    }

    private boolean matching(Player player1, Player player2) {
        return player1 != null && player2 != null && player1 == player2;
    }

    private boolean isValid(Player player) {
        int numOfPieces = board.getNumOfPieces();
        String piece = player.getPiece();
        boolean result = true;

        if (numOfPieces == 0) {
            if (piece.equals(constants.GAME_PIECE_TWO)) {
                result = false;
            }
        } else if (numOfPieces % 2 == 0) {
            if (piece.equals(constants.GAME_PIECE_TWO)) {
                result = false;
            }
        } else {
            if (piece.equals(constants.GAME_PIECE_ONE)) {
                result = false;
            }
        }

        return result;
    }
}
