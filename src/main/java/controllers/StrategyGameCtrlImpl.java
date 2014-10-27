package controllers;

import factories.BoardFactory;
import factories.GameTreeFactory;
import models.GameTree;
import models.Player;
import models.StrategyBoard;

import java.util.List;
import java.util.Optional;

public class StrategyGameCtrlImpl implements StrategyGameCtrl {
    private BoardFactory boardFactory;
    private GameTreeFactory gameTreeFactory;
    private StrategyBoard strategyBoard;
    private int side;

    public StrategyGameCtrlImpl(BoardFactory boardFactory, GameTreeFactory gameTreeFactory) {
        this.boardFactory = boardFactory;
        this.gameTreeFactory = gameTreeFactory;
    }

    @Override
    public void setBoard(int side, Player[] board) {
        this.side = side;
        strategyBoard = getStrategyBoard(board);
    }

    @Override
    public GameTree getTree(Player player, Player opponent, Integer[] move) {
        StrategyBoard copy = getStrategyBoard(strategyBoard.getBoard());
        copy.set(move[0], move[1], player);
        return gameTreeFactory.createTree(copy, player, opponent, boardFactory);
    }

    @Override
    public Optional<Integer[]> winningMove(Player player) {
        return strategyBoard.winningMove(player);
    }

    @Override
    public List<Integer[]> filterMoves(Player player) {
        return strategyBoard.filterMoves(player);
    }

    private StrategyBoard getStrategyBoard(Player[] board) {
        return boardFactory.createBoard(side, board);
    }
}
