package models;

import controllers.StrategyCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

import java.util.Optional;
import java.util.function.Consumer;

public class ComputerPlayerImpl extends PlayerImpl implements ComputerPlayer {
    private Player opponent;
    private StrategyCtrl strategyCtrl;

    public ComputerPlayerImpl(String gamePiece, int boundary, Player opponent, StrategyCtrl strategyCtrl) {
        super(gamePiece, boundary);
        this.opponent = opponent;
        this.strategyCtrl = strategyCtrl;
    }

    @Override
    public void calculateBestMove(Player[] board) throws OutOfBoundsException, NotVacantException {
        strategyCtrl.setBoard(board);
        Optional<Integer[]> found = strategyCtrl.findWinningMove(this);
        if (!found.isPresent()) found = strategyCtrl.findWinningMove(opponent);
        if (!found.isPresent()) found = strategyCtrl.getBestMove(this, opponent);
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