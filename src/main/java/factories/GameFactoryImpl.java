package factories;

import models.Game;
import models.GameImpl;

public class GameFactoryImpl implements GameFactory {
    @Override
    public Game createGame(int side, BoardFactory boardFactory) {
        return new GameImpl(side, boardFactory);
    }
}
