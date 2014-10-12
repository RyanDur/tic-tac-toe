package controllers;

import factories.BoardFactory;
import models.Board;
import models.Player;

public class GameCtrlImpl implements GameCtrl {
    private BoardFactory boardFactory;
    private Board board;

    public GameCtrlImpl(BoardFactory boardFactory) {
        this.boardFactory = boardFactory;
    }

    @Override
    public void setup() {
        board = boardFactory.createBoard();
    }

    @Override
    public void setPiece(Player player) {
        board.place(player.getX(), player.getY(), player);
    }
}
