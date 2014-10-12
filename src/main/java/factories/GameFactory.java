package factories;

import models.Game;
import models.Player;

public interface GameFactory {
    Game createGame(Player player1, Player player2);
}
