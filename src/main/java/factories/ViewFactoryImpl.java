package factories;

import views.GameViewImpl;
import views.MenuViewImpl;
import views.NavigationView;

import java.io.IOException;

public class ViewFactoryImpl implements ViewFactory {
    @Override
    public GameViewImpl createGameView() throws IOException {
        return new GameViewImpl();
    }

    @Override
    public NavigationView createNav() throws IOException {
        return new MenuViewImpl();
    }
}
