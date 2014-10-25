package factories;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.Player;
import views.elements.Board;
import views.elements.Menu;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface ViewFactory {
    Board createBoard(Player[] board, Function<MouseEvent, Player[]> play);

    Menu createMenu(BiConsumer<String, String> onePlayer, EventHandler<MouseEvent> twoPlayer);
}
