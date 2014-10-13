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
        board.place(player.getX(), player.getY(), player);
    }

    private boolean isValid(Player player) {
        int numOfPieces = board.getNumOfPieces();
        String piece = player.getPiece();
        boolean result = true;

        if(numOfPieces == 0) {
            if(piece.equals(constants.GAME_PIECE_TWO)) {
                result = false;
            }
        } else if(numOfPieces % 2 == 0) {
            if(piece.equals(constants.GAME_PIECE_TWO)) {
                result = false;
            }
        } else {
            if(piece.equals(constants.GAME_PIECE_ONE)) {
                result = false;
            }
        }

        return result;
    }
}
