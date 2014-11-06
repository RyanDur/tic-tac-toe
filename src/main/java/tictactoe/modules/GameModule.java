package tictactoe.modules;

import com.google.inject.AbstractModule;
import tictactoe.Game;
import tictactoe.GameImpl;
import tictactoe.ComputerPlayer;
import tictactoe.ComputerPlayerImpl;

public class GameModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Game.class).to(GameImpl.class);
        bind(ComputerPlayer.class).to(ComputerPlayerImpl.class);
    }
}
