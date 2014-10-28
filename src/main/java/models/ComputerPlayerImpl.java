package models;

import controllers.StrategyGameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

import java.util.Optional;
import java.util.function.Consumer;

public class ComputerPlayerImpl extends PlayerImpl implements ComputerPlayer {
    private Player opponent;
    private StrategyGameCtrl strategyGameCtrl;

    public ComputerPlayerImpl(String gamePiece, int boundary, Player opponent, StrategyGameCtrl strategyGameCtrl) {
        super(gamePiece, boundary);
        this.opponent = opponent;
        this.strategyGameCtrl = strategyGameCtrl;
    }

    @Override
    public void calculateBestMove(String[] board) throws OutOfBoundsException, NotVacantException {
        strategyGameCtrl.setBoard(board);
        Optional<Integer[]> found = strategyGameCtrl.findWinningMove(this);
        if (!found.isPresent()) found = strategyGameCtrl.findWinningMove(opponent);
        if (!found.isPresent()) found = strategyGameCtrl.getBestMove(this, opponent);
        found.ifPresent(setMove());
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