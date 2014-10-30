package modules;

import com.google.inject.AbstractModule;
import tictactoe.Game;
import tictactoe.GameImpl;
import factories.ViewFactory;
import factories.ViewFactoryImpl;
import views.elements.Header;
import views.elements.HeaderImpl;

public class TicTacToeModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GameModule());
        bind(Game.class).to(GameImpl.class);
        bind(ViewFactory.class).to(ViewFactoryImpl.class);
        bind(Header.class).to(HeaderImpl.class);
    }
}
