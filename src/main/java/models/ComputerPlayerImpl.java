package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.BoardFactory;
import factories.StrategyGameFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

public class ComputerPlayerImpl extends PlayerImpl implements ComputerPlayer {
    private int boundary;
    private StrategyGameFactory strategyGameFactory;
    private BoardFactory boardFactory;
    private StrategyGame strategyGame;
    private Player[] board;

    public ComputerPlayerImpl(String gamePiece, int boundary, StrategyGameFactory strategyGameFactory, BoardFactory boardFactory) {
        super(gamePiece, boundary);
        this.boundary = boundary;
        this.strategyGameFactory = strategyGameFactory;
        this.boardFactory = boardFactory;
    }

    @Override
    public void setBoard(Player[] board) {
        this.board = board;
        strategyGame = strategyGameFactory.createStrategyGame(boundary, board, boardFactory);
    }

    @Override
    public void calculateBestMove() throws OutOfBoundsException, NotVacantException {
        if (strategyGame.boardEmpty()) setCoordinates(boundary - 1, boundary - 1);
        else {
            Optional<Integer[]> found = strategyGame.findWinningMove(this);
            if (!found.isPresent()) found = strategyGame.findWinningMove(getOpponent());
            if (!found.isPresent()) {
                List<Integer[]> moves = strategyGame.getBestMove(this, getOpponent());
            }
            found.ifPresent(setMove());
        }
    }

    private Player getOpponent() {
        return Arrays.stream(board).
                filter(player -> player != this && player != null).
                findFirst().get();
    }

    private Consumer<Integer[]> setMove() {
        return vacancy -> {
            try {
                setCoordinates(vacancy[0], vacancy[1]);
            } catch (OutOfBoundsException e) {
                e.printStackTrace();
            }
        };
    }
}