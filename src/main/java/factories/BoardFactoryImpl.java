package factories;

import models.Board;
import models.BoardImpl;

public class BoardFactoryImpl implements BoardFactory {
    @Override
    public Board createBoard(int side) {
        return new BoardImpl(side);
    }
}
