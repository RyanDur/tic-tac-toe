package factories;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.Player;
import views.elements.GameView;
import views.elements.GameViewImpl;
import views.elements.MenuView;
import views.elements.MenuViewImpl;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class ViewFactoryImpl implements ViewFactory {
    @Override
    public GameView createGameView(Player[] board, Function<MouseEvent, Player[]> play) {
        return new GameViewImpl(board, play);
    }

    @Override
    public MenuView createMenu(BiConsumer<String, String> onePlayer, EventHandler<MouseEvent> twoPlayer) {
        return new MenuViewImpl(onePlayer, twoPlayer);
    }
}
