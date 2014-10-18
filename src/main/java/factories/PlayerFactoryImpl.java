package factories;

import models.Player;
import models.PlayerImpl;

public class PlayerFactoryImpl implements PlayerFactory {
    @Override
    public Player createPlayer(String gamePiece, int side) {
        return new PlayerImpl(gamePiece, side);
    }
}
