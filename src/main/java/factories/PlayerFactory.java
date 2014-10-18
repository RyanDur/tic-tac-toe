package factories;

import models.Player;

public interface PlayerFactory {
    Player createPlayer(String gamePiece, int side);
}
