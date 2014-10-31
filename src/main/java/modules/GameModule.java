package modules;

import com.google.inject.AbstractModule;
import tictactoe.Board;
import tictactoe.BoardImpl;
import tictactoe.ComputerPlayer;
import tictactoe.ComputerPlayerImpl;

public class GameModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new ComputerPlayerModule());
        bind(Board.class).to(BoardImpl.class);
        bind(ComputerPlayer.class).to(ComputerPlayerImpl.class);
    }
}
