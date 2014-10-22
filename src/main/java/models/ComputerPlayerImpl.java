package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.StrategyGameFactory;

import java.util.Optional;
import java.util.function.Consumer;

public class ComputerPlayerImpl extends PlayerImpl implements ComputerPlayer {
    private int boundary;
    private Player opponent;
    private StrategyGameFactory strategyGameFactory;

    public ComputerPlayerImpl(String gamePiece, int boundary, Player opponent, StrategyGameFactory strategyGameFactory) {
        super(gamePiece, boundary);
        this.boundary = boundary;
        this.opponent = opponent;
        this.strategyGameFactory = strategyGameFactory;
    }

    @Override
    public void calculateBestMove(Player[] board) throws OutOfBoundsException, NotVacantException {
        StrategyGame strategyGame = strategyGameFactory.createStrategyGame(boundary, board);
        if (strategyGame.boardEmpty()) {
            Integer[] corner = strategyGame.getCorner();
            setCoordinates(corner[0], corner[1]);
        } else {
            Optional<Integer[]> found = strategyGame.findWinningMove(this);
            if (!found.isPresent()) found = strategyGame.findWinningMove(opponent);
            if (!found.isPresent()) found = strategyGame.getBestMove(this, opponent);
            found.ifPresent(setMove());
        }
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