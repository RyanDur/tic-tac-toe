package factories;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import views.elements.*;
import views.elements.Board;
import views.elements.BoardImpl;

import java.util.function.Consumer;
import java.util.function.Function;

public class ViewFactoryImpl implements ViewFactory {
    @Override
    public Board createBoard(String[] board, Function<MouseEvent, String[]> play) {
        return new BoardImpl(board, play);
    }

    @Override
    public Menu createMenu(Consumer<String> onePlayer, EventHandler<MouseEvent> twoPlayer) {
        return new MenuImpl(onePlayer, twoPlayer);
    }
}
