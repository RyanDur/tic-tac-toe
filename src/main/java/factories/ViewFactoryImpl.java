package factories;

import javafx.scene.input.MouseEvent;
import models.Player;
import views.GameViewImpl;
import views.NavigationView;
import views.NavigationViewImpl;

import java.io.IOException;
import java.util.function.Function;

public class ViewFactoryImpl implements ViewFactory {
    @Override
    public GameViewImpl createGameView(Player[] board, Function<MouseEvent, Player[]> play) throws IOException {
        return new GameViewImpl(board, play);
    }

    @Override
    public NavigationView createNav() throws IOException {
        return new NavigationViewImpl();
    }
}
