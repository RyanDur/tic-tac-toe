package factories;

import views.GameViewImpl;
import views.NavigationView;

import java.io.IOException;

public interface ViewFactory {
    GameViewImpl createGameView() throws IOException;

    NavigationView createNav() throws IOException;
}
