package factories;

import models.GameTree;
import models.Player;
import models.StrategyBoard;

public interface GameTreeFactory {
    GameTree createTree(StrategyBoard board, Player player, Player opponent, BoardFactory boardFactory);
}
