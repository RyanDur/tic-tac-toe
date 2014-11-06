package tictactoe.modules;

import com.google.inject.AbstractModule;
import tictactoe.GamePlay;
import tictactoe.GamePlayImpl;
import tictactoe.views.elements.*;

public class TicTacToeModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GameModule());
        bind(GamePlay.class).to(GamePlayImpl.class);
        bind(Header.class).to(HeaderImpl.class);
        bind(Menu.class).to(MenuImpl.class);
        bind(Board.class).to(BoardImpl.class);
    }
}
