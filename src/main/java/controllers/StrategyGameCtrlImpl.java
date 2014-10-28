package controllers;

import com.google.inject.Inject;
import lang.constants;
import models.Player;

import java.util.Optional;

public class StrategyGameCtrlImpl implements StrategyGameCtrl {
    private int side;
    private StrategyBoardCtrl strategyBoardCtrl;

    @Inject
    public StrategyGameCtrlImpl(StrategyBoardCtrl strategyBoardCtrl) {
        this.strategyBoardCtrl = strategyBoardCtrl;
        this.side = constants.SIDE;
    }

    @Override
    public void setBoard(String[] board) {
        strategyBoardCtrl.setBoard(side, board);
    }

    @Override
    public Optional<Integer[]> findWinningMove(Player player) {
        return strategyBoardCtrl.winningMove(player);
    }

    @Override
    public Optional<Integer[]> getBestMove(Player computer, Player opponent) {
        if(strategyBoardCtrl.boardEmpty()) return strategyBoardCtrl.getCorner();
        if(strategyBoardCtrl.toFewPieces()) return strategyBoardCtrl.centerOrCorner();
        Optional<Integer[]> move = bestMoveOf(computer, opponent);
        if(strategyBoardCtrl.noBest(move)) return strategyBoardCtrl.anyMove();
        return move;
    }

    private Optional<Integer[]> bestMoveOf(Player computer, Player opponent) {
        return strategyBoardCtrl.filterMoves(opponent).stream()
                .max((move1, move2) -> strategyBoardCtrl.getTree(computer, opponent, move1).getMaxValue() -
                        strategyBoardCtrl.getTree(computer, opponent, move2).getMaxValue());
    }
}