package factories;

import controllers.GamePlayCtrl;
import javafx.scene.input.MouseEvent;
import models.Player;
import views.*;

import java.io.IOException;
import java.util.function.Function;

public class ViewFactoryImpl implements ViewFactory {
    @Override
    public GameViewImpl createGameView(Player[] board, Function<MouseEvent, Player[]> play) throws IOException {
        return new GameViewImpl(board, play);
    }

    @Override
    public NavigationView createNav(GamePlayCtrl game) throws IOException {
        return new NavigationViewImpl(game);
    }
}
