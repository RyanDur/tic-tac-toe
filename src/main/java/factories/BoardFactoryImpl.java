package factories;

import models.*;

public class BoardFactoryImpl implements BoardFactory {
    @Override
    public Board createBoard(int side) {
        return new BoardImpl(side);
    }

    @Override
    public StrategyBoard createBoard(int side, StrategyBoard board) {
        return new StrategyBoardImpl(side, board.getBoard());
    }
}
