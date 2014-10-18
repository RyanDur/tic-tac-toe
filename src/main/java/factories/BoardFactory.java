package factories;

import controllers.GameCtrl;
import models.Player;
import views.Board;

public interface BoardFactory {
    Board createBoard(GameCtrl gameCtrl, Player player1, Player player2);
}
