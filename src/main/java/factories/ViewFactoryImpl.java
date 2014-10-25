package factories;

import views.GameViewImpl;
import views.MenuViewImpl;
import views.MenuView;

import java.io.IOException;

public class ViewFactoryImpl implements ViewFactory {
    @Override
    public GameViewImpl createGameView() throws IOException {
        return new GameViewImpl();
    }

    @Override
    public MenuView createMenu() throws IOException {
        return new MenuViewImpl();
    }
}
