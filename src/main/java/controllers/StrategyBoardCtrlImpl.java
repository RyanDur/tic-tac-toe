package controllers;

import com.google.inject.Inject;
import factories.BoardFactory;
import factories.GameTreeFactory;
import lang.constants;
import models.GameTree;
import models.Player;
import models.StrategyBoard;

import java.util.Optional;

public class StrategyBoardCtrlImpl implements StrategyBoardCtrl {
    private int side;
    private GameTreeFactory gameTreeFactory;
    private BoardFactory boardFactory;
    private StrategyBoard strategyBoard;

    @Inject
    public StrategyBoardCtrlImpl(GameTreeFactory gameTreeFactory, BoardFactory boardFactory) {
        this.boardFactory = boardFactory;
        this.side = constants.SIDE;
        this.gameTreeFactory = gameTreeFactory;
    }

    @Override
    public void setBoard(Player[] board) {
        strategyBoard = boardFactory.createBoard(side, board);
    }

    @Override
    public Optional<Integer[]> findWinningMove(Player player) {
        return strategyBoard.winningMove(player);
    }

    @Override
    public Optional<Integer[]> getBestMove(Player computer, Player opponent) {
        return Optional.of(strategyBoard.filterMoves(computer).stream().max((move1, move2) -> {
            StrategyBoard board1 = getStrategyBoard(computer, move1);
            StrategyBoard board2 = getStrategyBoard(computer, move2);
            return getTree(computer, opponent, board1).getValue() -
                    getTree(computer, opponent, board2).getValue();
        }).get());
    }

    private GameTree getTree(Player player, Player opponent, StrategyBoard board) {
        return gameTreeFactory.createTree(board, player, opponent, boardFactory);
    }

    private StrategyBoard getStrategyBoard(Player computer, Integer[] move1) {
        StrategyBoard board1 = boardFactory.createBoard(side, strategyBoard.getBoard());
        board1.set(move1[0], move1[1], computer);
        return board1;
    }

    @Override
    public void setSide(int side) {
        this.side = side;
    }
}