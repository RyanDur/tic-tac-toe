package controllers;

import factories.PlayerFactory;
import lang.constants;
import models.Player;

public class PlayerCtrlImpl implements PlayerCtrl {
    private int side;
    private PlayerFactory playerFactory;
    private StrategyGameCtrl strategyGameCtrl;
    private Player player1;
    private Player player2;

    public PlayerCtrlImpl(int side, PlayerFactory playerFactory, StrategyGameCtrl strategyGameCtrl) {
        this.side = side;
        this.playerFactory = playerFactory;
        this.strategyGameCtrl = strategyGameCtrl;
    }

    @Override
    public void setupTwoPlayer() {
        player1 = playerFactory.createPlayer(constants.GAME_PIECE_ONE, side);
        player2 = playerFactory.createPlayer(constants.GAME_PIECE_TWO, side);
    }

    @Override
    public void setupOnePlayer(String pieceOne, String pieceTwo) {
        player1 = playerFactory.createPlayer(pieceOne, side);
        player2 = playerFactory.createComputerPlayer(pieceTwo, side, player1, strategyGameCtrl);
    }

    @Override
    public Player[] getMove(Player[] players) {
        if(players.length % 2 == 0) return new Player[]{player1};
        return new Player[]{player2};
    }

    @Override
    public Player getPlayer(Player[] board) {
        return null;
    }

    @Override
    public int playerCount() {
        return 0;
    }
}
