package factories;

import models.Game;
import models.GameImpl;

public class GameFactoryImpl implements GameFactory {
    @Override
    public Game createGame(int side) {
        return new GameImpl(side);
    }
}
