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
    private Player current;
    private Player winner;

    public GameCtrlImpl(BoardFactory boardFactory) {
        this.boardFactory = boardFactory;
    }

    @Override
    public void setup() {
        board = boardFactory.createBoard(constants.HEIGHT, constants.WIDTH);
    }

    @Override
    public void setPiece(Player player) throws OutOfTurnException, NotVacantException {
        if (!isValidTurn(player)) throw new OutOfTurnException();
        if (!board.isVacant(player.getX(), player.getY())) throw new NotVacantException();
        current = player;
        board.set(player.getX(), player.getY(), player);
    }

    @Override
    public boolean gameOver() {
        boolean won = board.winner();
        if (won) winner = current;
        return  won || board.full();
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    private boolean isValidTurn(Player player) {
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
