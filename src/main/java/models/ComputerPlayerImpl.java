package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.BoardFactory;
import factories.StrategyGameFactory;

import java.util.Arrays;
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
            Optional<Integer> found = strategyGame.findWinningMove(this);
            if (!found.isPresent()) found = strategyGame.findWinningMove(getOpponent());
            if (!found.isPresent()) found = strategyGame.findBestMove(this, getOpponent());
            found.ifPresent(setVacancy());
        }
    }

    private Player getOpponent() {
        return Arrays.stream(board).
                filter(player -> player != this && player != null).
                findFirst().get();
    }

    private Consumer<Integer> setVacancy() {
        return vacancy -> {
            try {
                setCoordinates(calcRow(vacancy), calcColumn(vacancy));
            } catch (OutOfBoundsException e) {
                e.printStackTrace();
            }
        };
    }

    private int calcColumn(int vacancy) {
        return vacancy - (calcRow(vacancy) * boundary);
    }

    private int calcRow(int vacancy) {
        int row = 0;
        while (vacancy >= boundary) {
            vacancy -= boundary;
            row++;
        }
        return row;
    }
}