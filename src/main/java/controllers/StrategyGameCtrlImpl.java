package controllers;

import com.google.inject.Inject;
import factories.StrategyGameFactory;
import lang.constants;
import models.Player;
import models.StrategyGame;

import java.util.Optional;

public class StrategyGameCtrlImpl implements StrategyGameCtrl {
    private StrategyGameFactory strategyGameFactory;
    private int side;
    private StrategyGame strategyGame;

    @Inject
    public StrategyGameCtrlImpl(StrategyGameFactory strategyGameFactory) {
        this.side = constants.SIDE;
        this.strategyGameFactory = strategyGameFactory;
    }

    @Override
    public void setBoard(Player[] board) {
        strategyGame = strategyGameFactory.createStrategyGame(side, board);
    }

    @Override
    public Optional<Integer[]> findWinningMove(Player player) {
        return strategyGame.findWinningMove(player);
    }

    @Override
    public Optional<Integer[]> getBestMove(Player computer, Player opponent) {
        return strategyGame.getBestMove(computer, opponent);
    }

    @Override
    public void setSide(int side) {
        this.side = side;
    }
}
