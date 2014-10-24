package controllers;

import com.google.inject.Inject;
import factories.PlayerFactory;
import lang.constants;
import models.ComputerPlayer;
import models.Player;

public class PlayerCtrlImpl implements PlayerCtrl {
    private int side;
    private PlayerFactory playerFactory;
    private StrategyGameCtrl strategyGameCtrl;
    private Player player1;
    private Player player2;

    @Inject
    public PlayerCtrlImpl(PlayerFactory playerFactory, StrategyGameCtrl strategyGameCtrl) {
        this.side = constants.SIDE;
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
    public Player getPlayer(Player[] board) {
        if (player2 instanceof ComputerPlayer) return player1;
        return (board.length % 2 != 0) ? player2 : player1;
    }

    @Override
    public int playerCount() {
        if (player1 == null) return 0;
        return player2 instanceof ComputerPlayer ? 1 : 2;
    }

    @Override
    public ComputerPlayer getComputerPlayer(Player[] board) {
        return (player2 instanceof ComputerPlayer) ? (ComputerPlayer) player2 : null;        
    }
}