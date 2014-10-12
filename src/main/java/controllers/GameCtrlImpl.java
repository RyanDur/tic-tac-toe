package controllers;

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
    public void setPiece(Player player) {
        board.place(player.getX(), player.getY(), player);
    }
}
