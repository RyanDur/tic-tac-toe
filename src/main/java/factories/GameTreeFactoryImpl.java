package factories;

import models.GameTree;
import models.GameTreeImpl;
import models.StrategyBoard;

public class GameTreeFactoryImpl implements GameTreeFactory {
    @Override
    public GameTree createTree(StrategyBoard board, String player, String opponent, BoardFactory boardFactory) {
        return new GameTreeImpl(board, player, opponent, boardFactory);
    }
}
