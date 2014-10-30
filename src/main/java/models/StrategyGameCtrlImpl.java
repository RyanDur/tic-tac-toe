package models;

import com.google.inject.Inject;
import tictactoe.Board;

import java.util.Optional;

public class StrategyGameCtrlImpl implements StrategyGameCtrl {
    private StrategyBoardCtrl strategyBoardCtrl;

    @Inject
    public StrategyGameCtrlImpl(StrategyBoardCtrl strategyBoardCtrl) {
        this.strategyBoardCtrl = strategyBoardCtrl;
    }

    @Override
    public void setBoard(Board board) {
    }

    @Override
    public Optional<Integer[]> findWinningMove(String player) {
        return strategyBoardCtrl.winningMove(player);
    }

    @Override
    public Optional<Integer[]> getBestMove(String computer, String opponent) {
        if(strategyBoardCtrl.boardEmpty()) return strategyBoardCtrl.getCorner();
        if(strategyBoardCtrl.toFewPieces()) return strategyBoardCtrl.centerOrCorner();
        Optional<Integer[]> move = bestMoveOf(computer, opponent);
        if(strategyBoardCtrl.noBest(move)) return strategyBoardCtrl.anyMove();
        return move;
    }

    private Optional<Integer[]> bestMoveOf(String computer, String opponent) {
        return strategyBoardCtrl.filterMoves(opponent).stream()
                .max((move1, move2) -> strategyBoardCtrl.getTree(computer, opponent, move1).getMaxValue() -
                        strategyBoardCtrl.getTree(computer, opponent, move2).getMaxValue());
    }
}