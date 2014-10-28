package controllers;

import com.google.inject.Inject;
import lang.constants;
import models.Player;

import java.util.Optional;

public class StrategyCtrlImpl implements StrategyCtrl {
    private int side;
    private StrategyGameCtrl strategyGame;

    @Inject
    public StrategyCtrlImpl(StrategyGameCtrl strategyGame) {
        this.strategyGame = strategyGame;
        this.side = constants.SIDE;
    }

    @Override
    public void setBoard(Player[] board) {
        strategyGame.setBoard(side, board);
    }

    @Override
    public Optional<Integer[]> findWinningMove(Player player) {
        return strategyGame.winningMove(player);
    }

    @Override
    public Optional<Integer[]> getBestMove(Player computer, Player opponent) {
        if(strategyGame.boardEmpty()) return strategyGame.getCorner();
        if(strategyGame.toFewPieces()) return strategyGame.centerOrCorner();
        Optional<Integer[]> move = bestMoveOf(computer, opponent);
        if(strategyGame.noBest(move)) return strategyGame.anyMove();
        return move;
    }

    private Optional<Integer[]> bestMoveOf(Player computer, Player opponent) {
        return strategyGame.filterMoves(opponent).stream()
                .max((move1, move2) -> strategyGame.getTree(computer, opponent, move1).getMaxValue() -
                        strategyGame.getTree(computer, opponent, move2).getMaxValue());
    }
}