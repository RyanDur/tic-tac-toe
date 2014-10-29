package factories;

import tictactoe.Board;
import models.StrategyBoard;

public interface BoardFactory {
    Board createBoard(int side);

    StrategyBoard createBoard(int side, String[] board);
}
