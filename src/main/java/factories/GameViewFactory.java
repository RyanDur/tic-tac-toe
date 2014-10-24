package factories;

import javafx.scene.input.MouseEvent;
import models.Player;
import views.GameView;

import java.io.IOException;
import java.util.function.Function;

public interface GameViewFactory {
    GameView createGameView(Player[] board, Function<MouseEvent, Player[]> play) throws IOException;
}
