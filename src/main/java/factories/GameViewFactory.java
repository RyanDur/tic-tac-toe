package factories;

import controllers.GameCtrl;
import models.Player;
import views.GameView;

public interface GameViewFactory {
    GameView createGameView(GameCtrl gameCtrl, Player player1, Player player2);
}
