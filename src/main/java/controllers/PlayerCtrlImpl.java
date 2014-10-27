package controllers;

import com.google.inject.Inject;
import factories.PlayerFactory;
import lang.constants;
import models.Player;

public class PlayerCtrlImpl implements PlayerCtrl {
    private int side;
    private PlayerFactory playerFactory;
    private StrategyBoardCtrl strategyBoardCtrl;
    private Player player1;
    private Player player2;

    @Inject
    public PlayerCtrlImpl(PlayerFactory playerFactory, StrategyBoardCtrl strategyBoardCtrl) {
        this.side = constants.SIDE;
        this.playerFactory = playerFactory;
        this.strategyBoardCtrl = strategyBoardCtrl;
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
        player2 = playerFactory.createComputerPlayer(pieceTwo, side, player1, strategyBoardCtrl);
        return new Player[]{player1, player2};
    }
}