package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import factories.GameFactory;
import lang.constants;
import models.Game;
import models.Player;

public class GameCtrlImpl implements GameCtrl {
    private GameFactory gameFactory;
    private Game game;

    public GameCtrlImpl(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    @Override
    public void setup() {
        game = gameFactory.createGame(constants.SIDE);
    }

    @Override
    public void setPiece(Player player) throws OutOfTurnException, NotVacantException {
        if (!validTurn(player)) throw new OutOfTurnException();
        game.set(player);
    }

    @Override
    public boolean gameOver() {
        return game.getWinner() != null || game.full();
    }

    @Override
    public Player getWinner() {
        return game.getWinner();
    }

    private boolean validTurn(Player player) {
        int numOfPieces = game.getNumOfPieces();
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
