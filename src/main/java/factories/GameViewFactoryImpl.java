package factories;

import controllers.GameCtrl;
import controllers.PlayerCtrl;
import javafx.scene.input.MouseEvent;
import models.Player;
import views.GameView;

import java.io.IOException;
import java.util.function.Function;

public class GameViewFactoryImpl implements GameViewFactory {
    @Override
    public GameView createGameView(GameCtrl gameCtrl, PlayerCtrl playerCtrl, Function<MouseEvent, Player[]> play) throws IOException {
        return new GameView(gameCtrl, playerCtrl, play);
    }
}
