package modules;

import com.google.inject.AbstractModule;
import tictactoe.ComputerPlayer;
import tictactoe.ComputerPlayerImpl;
import factories.BoardFactory;
import factories.BoardFactoryImpl;

public class GameModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ComputerPlayerModule());
        bind(BoardFactory.class).to(BoardFactoryImpl.class);
        bind(ComputerPlayer.class).to(ComputerPlayerImpl.class);
    }
}
