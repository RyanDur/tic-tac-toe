package factories;

import models.Player;
import models.StrategyGame;
import models.StrategyGameImpl;

public class StrategyGameFactoryImpl implements StrategyGameFactory {
    @Override
    public StrategyGame createStrategyGame(int side, Player[] board, BoardFactory boardFactory) {
        return new StrategyGameImpl(side, board, boardFactory);
    }
}
