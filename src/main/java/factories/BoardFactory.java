package factories;

import models.Board;
import models.Player;
import models.StrategyBoard;

public interface BoardFactory {
    Board createBoard(int side);

    StrategyBoard createBoard(int side, Player[] board);
}
