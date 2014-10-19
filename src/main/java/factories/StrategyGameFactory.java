package factories;

import models.Player;
import models.StrategyGame;

public interface StrategyGameFactory {
    StrategyGame createStrategyGame(int side, Player[] players);
}
