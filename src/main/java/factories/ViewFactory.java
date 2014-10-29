package factories;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import views.elements.Board;
import views.elements.Menu;

import java.util.function.Consumer;
import java.util.function.Function;

public interface ViewFactory {
    Board createBoard(String[] board, Function<MouseEvent, String[]> play);

    Menu createMenu(Consumer<String> onePlayer, EventHandler<MouseEvent> twoPlayer);
}
