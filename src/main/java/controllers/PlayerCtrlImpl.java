package controllers;

import com.google.inject.Inject;
import factories.PlayerFactory;
import lang.constants;
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
    public Player[] setupTwoPlayer() {
        player1 = playerFactory.createPlayer(constants.GAME_PIECE_ONE, side);
        player2 = playerFactory.createPlayer(constants.GAME_PIECE_TWO, side);
        return new Player[]{player1, player2};
    }

    @Override
    public Player[] setupOnePlayer(String pieceOne, String pieceTwo) {
        player1 = playerFactory.createPlayer(pieceOne, side);
        player2 = playerFactory.createComputerPlayer(pieceTwo, side, player1, strategyGameCtrl);
        return new Player[]{player1, player2};
    }
}