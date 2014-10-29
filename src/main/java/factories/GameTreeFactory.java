package factories;

import models.GameTree;
import models.StrategyBoard;

public interface GameTreeFactory {
    GameTree createTree(StrategyBoard board, String player, String opponent, BoardFactory boardFactory);
}
