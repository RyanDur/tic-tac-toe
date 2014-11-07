package tictactoe.modules;

import com.google.inject.AbstractModule;
import tictactoe.ComputerPlayer;
import tictactoe.ComputerPlayerImpl;

public class GameModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ComputerPlayer.class).to(ComputerPlayerImpl.class);
    }
}
