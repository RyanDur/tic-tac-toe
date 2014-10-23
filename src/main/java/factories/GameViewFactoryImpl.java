package factories;

import controllers.GameCtrl;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import models.Player;
import views.GameView;

import java.io.IOException;

public class GameViewFactoryImpl implements GameViewFactory {
    @Override
    public GameView createGameView(GameCtrl gameCtrl, Player player1, Player player2, Label messages, Button reset, Button replay) throws IOException {
        return new GameView(gameCtrl, player1, player2, messages, reset, replay);
    }
}