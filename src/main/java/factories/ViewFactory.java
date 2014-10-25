package factories;

import javafx.scene.input.MouseEvent;
import models.Player;
import views.GameView;
import views.NavigationView;

import java.io.IOException;
import java.util.function.Function;

public interface ViewFactory {
    GameView createGameView(Player[] board, Function<MouseEvent, Player[]> play) throws IOException;

    NavigationView createNav() throws IOException;
}
