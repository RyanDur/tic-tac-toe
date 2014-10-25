package factories;

import views.GameViewImpl;
import views.MenuView;

import java.io.IOException;

public interface ViewFactory {
    GameViewImpl createGameView() throws IOException;

    MenuView createMenu() throws IOException;
}
