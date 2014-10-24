package factories;

import controllers.GameCtrl;
import controllers.PlayerCtrl;
import javafx.scene.input.MouseEvent;
import models.Player;
import views.GameView;

import java.io.IOException;
import java.util.function.Function;

public interface GameViewFactory {
    GameView createGameView(GameCtrl gameCtrl, PlayerCtrl playerCtrl, Function<MouseEvent, Player[]> play) throws IOException;
}
