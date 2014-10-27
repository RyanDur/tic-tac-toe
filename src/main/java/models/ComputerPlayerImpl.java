package models;

import controllers.StrategyBoardCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

import java.util.Optional;
import java.util.function.Consumer;

public class ComputerPlayerImpl extends PlayerImpl implements ComputerPlayer {
    private Player opponent;
    private StrategyBoardCtrl strategyBoardCtrl;

    public ComputerPlayerImpl(String gamePiece, int boundary, Player opponent, StrategyBoardCtrl strategyBoardCtrl) {
        super(gamePiece, boundary);
        this.opponent = opponent;
        this.strategyBoardCtrl = strategyBoardCtrl;
    }

    @Override
    public void calculateBestMove(Player[] board) throws OutOfBoundsException, NotVacantException {
        strategyBoardCtrl.setBoard(board);
        Optional<Integer[]> found = strategyBoardCtrl.findWinningMove(this);
        if (!found.isPresent()) found = strategyBoardCtrl.findWinningMove(opponent);
        if (!found.isPresent()) found = strategyBoardCtrl.getBestMove(this, opponent);
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