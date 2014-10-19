package models;

import exceptions.OutOfBoundsException;
import factories.StrategyGameFactory;

public class ComputerPlayerImpl extends PlayerImpl implements ComputerPlayer {
    private int boundary;
    private StrategyGameFactory strategyGameFactory;

    public ComputerPlayerImpl(String gamePiece, int boundary, StrategyGameFactory strategyGameFactory) {
        super(gamePiece, boundary);
        this.boundary = boundary;
        this.strategyGameFactory = strategyGameFactory;
    }

    @Override
    public void setBoard(Player[] players) throws OutOfBoundsException {
        StrategyGame strategyGame = strategyGameFactory.createStrategyGame(boundary, players);
        if(strategyGame.boardEmpty()) setCoordinates(boundary-1, boundary-1);
        else strategyGame.setBestMove(this);
    }
}
