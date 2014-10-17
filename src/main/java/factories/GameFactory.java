package factories;

import models.Game;

public interface GameFactory {
    Game createGame(int side);
}
