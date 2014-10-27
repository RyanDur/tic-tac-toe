package factories;

import models.GameTree;
import models.GameTreeImpl;
import models.Player;
import models.StrategyBoard;

public class GameTreeFactoryImpl implements GameTreeFactory {
    @Override
    public GameTree createTree(StrategyBoard board, Player player, Player opponent, BoardFactory boardFactory) {
        return new GameTreeImpl(board, player, opponent, boardFactory);
    }
}
