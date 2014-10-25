package factories;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.Player;
import views.elements.GameView;
import views.elements.MenuView;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface ViewFactory {
    GameView createGameView(Player[] board, Function<MouseEvent, Player[]> play);

    MenuView createMenu(BiConsumer<String, String> onePlayer, EventHandler<MouseEvent> twoPlayer);
}
