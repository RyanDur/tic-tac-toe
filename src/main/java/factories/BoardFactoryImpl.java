package factories;

import tictactoe.Board;
import tictactoe.BoardImpl;
import models.*;

public class BoardFactoryImpl implements BoardFactory {
    @Override
    public Board createBoard(int side) {
        return new BoardImpl(side);
    }

    @Override
    public StrategyBoard createBoard(int side, String[] board) {
        return new StrategyBoardImpl(side, board);
    }
}
