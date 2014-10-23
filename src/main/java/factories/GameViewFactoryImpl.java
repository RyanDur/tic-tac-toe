package factories;

import controllers.GameCtrl;
import javafx.scene.layout.HBox;
import models.Player;
import views.GameView;

import java.io.IOException;

public class GameViewFactoryImpl implements GameViewFactory {
    @Override
    public GameView createGameView(GameCtrl gameCtrl, Player player1, Player player2, HBox header) throws IOException {
        return new GameView(gameCtrl, player1, player2, header);
    }
}
