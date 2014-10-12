package factories;

import models.Board;

public interface BoardFactory {
    Board createBoard(int height, int width);
}
