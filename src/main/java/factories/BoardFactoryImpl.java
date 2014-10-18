package factories;

import controllers.GameCtrl;
import models.Player;
import views.Board;

public class BoardFactoryImpl implements BoardFactory {
    @Override
    public Board createBoard(GameCtrl gameCtrl, Player player1, Player player2) {
        return new Board(gameCtrl, player1, player2);
    }
}
