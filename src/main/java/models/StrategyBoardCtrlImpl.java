package models;

import com.google.inject.Inject;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.BoardFactory;
import factories.GameTreeFactory;
import lang.constants;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

public class StrategyBoardCtrlImpl implements StrategyBoardCtrl {
    private BoardFactory boardFactory;
    private GameTreeFactory gameTreeFactory;
    private StrategyBoard strategyBoard;
    private int side;
    private Random random;

    @Inject
    public StrategyBoardCtrlImpl(BoardFactory boardFactory, GameTreeFactory gameTreeFactory) {
        this.boardFactory = boardFactory;
        this.gameTreeFactory = gameTreeFactory;
        random = new Random();
    }

    @Override
    public void setBoard(int side, String[] board) {
        this.side = side;
        strategyBoard = getStrategyBoard(board);
    }

    @Override
    public GameTree getTree(String player, String opponent, Integer[] move) {
        StrategyBoard copy = getStrategyBoard(strategyBoard.getBoard());
        try {
            copy.set(move[0], move[1], player);
        } catch (NotVacantException | OutOfTurnException | OutOfBoundsException e) {
            e.printStackTrace();
        }
        return gameTreeFactory.createTree(copy, player, opponent, boardFactory);
    }

    @Override
    public Optional<Integer[]> winningMove(String player) {
        return strategyBoard.winningMove(player);
    }

    @Override
    public List<Integer[]> filterMoves(String player) {
        return strategyBoard.filterMoves(player);
    }

    @Override
    public boolean boardEmpty() {
        return countPieces(strategyBoard.getBoard()) == 0;
    }

    @Override
    public boolean toFewPieces() {
        return countPieces(strategyBoard.getBoard()) < side-1;
    }

    @Override
    public Optional<Integer[]> centerOrCorner() {
        Integer[] center = constants.CENTER;
        try {
            if(strategyBoard.get(center[0], center[1])  == null) return Optional.of(center);
        } catch (OutOfBoundsException e) {
            e.printStackTrace();
        }
        return getCorner();
    }

    @Override
    public Optional<Integer[]> getCorner() {
        List<Integer[]> corners = constants.CORNERS;
        return Optional.of(corners.get(random.nextInt(corners.size())));
    }

    @Override
    public boolean noBest(Optional<Integer[]> move) {
        return !move.isPresent() && !boardEmpty();
    }

    @Override
    public Optional<Integer[]> anyMove() {
        return Optional.of(strategyBoard.getVacancies().get(0));
    }

    private int countPieces(String[] board) {
        return (int) Arrays.stream(board).filter(player -> player != null).count();
    }

    private StrategyBoard getStrategyBoard(String[] board) {
        return boardFactory.createBoard(side, board);
    }

}
