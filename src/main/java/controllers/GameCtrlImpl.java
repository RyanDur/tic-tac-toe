package controllers;

import exceptions.OutOfTurnException;
import factories.BoardFactory;
import models.Board;
import models.Player;

public class GameCtrlImpl implements GameCtrl {
    private BoardFactory boardFactory;
    private Board board;
    private int height;
    private int width;

    public GameCtrlImpl(BoardFactory boardFactory) {
        height = 3;
        width = 3;
        this.boardFactory = boardFactory;
    }

    @Override
    public void setup() {
        board = boardFactory.createBoard(height, width);
    }

    @Override
    public void setPiece(Player player) throws OutOfTurnException {
        if (!isValid(player)) throw new OutOfTurnException();
        board.place(player.getX(), player.getY(), player);
    }

    private boolean isValid(Player player) {
        int numOfPieces = board.getNumOfPieces();
        boolean result = true;

        if(numOfPieces == 0) {
            if(!player.getPiece().equals("X")) {
                result = false;
            }
        }

        return result;
    }
}
