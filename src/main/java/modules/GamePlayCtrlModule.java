package modules;

import com.google.inject.AbstractModule;
import tictactoe.ComputerPlayer;
import tictactoe.ComputerPlayerImpl;
import factories.BoardFactory;
import factories.BoardFactoryImpl;

public class GamePlayCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ComputerCtrlModule());
        bind(BoardFactory.class).to(BoardFactoryImpl.class);
        bind(ComputerPlayer.class).to(ComputerPlayerImpl.class);
    }
}
