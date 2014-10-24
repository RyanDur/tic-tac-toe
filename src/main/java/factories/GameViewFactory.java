package factories;

import controllers.GameCtrl;
import controllers.PlayerCtrl;
import javafx.scene.input.MouseEvent;
import views.GameView;

import java.util.function.Consumer;

public interface GameViewFactory {
    GameView createGameView(GameCtrl gameCtrl, PlayerCtrl playerCtrl, Consumer<MouseEvent> play);
}
