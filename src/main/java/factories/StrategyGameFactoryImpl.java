package factories;

import com.google.inject.Inject;
import models.Player;
import models.StrategyGame;
import models.StrategyGameImpl;

public class StrategyGameFactoryImpl implements StrategyGameFactory {

    private BoardFactory boardFactory;

    @Inject
    public StrategyGameFactoryImpl(BoardFactory boardFactory) {
        this.boardFactory = boardFactory;
    }

    @Override
    public StrategyGame createStrategyGame(int side, Player[] board) {
        return new StrategyGameImpl(side, board, boardFactory);
    }
}
