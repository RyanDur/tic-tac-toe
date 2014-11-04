package tictactoe.modules;

import com.google.inject.AbstractModule;
import tictactoe.game.Game;
import tictactoe.game.GameImpl;
import tictactoe.views.elements.*;

public class TicTacToeModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GameModule());
        bind(Game.class).to(GameImpl.class);
        bind(Header.class).to(HeaderImpl.class);
        bind(Menu.class).to(MenuImpl.class);
        bind(Board.class).to(BoardImpl.class);
    }
}
