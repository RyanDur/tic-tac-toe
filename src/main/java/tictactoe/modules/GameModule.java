package tictactoe.modules;

import com.google.inject.AbstractModule;
import tictactoe.game.Board;
import tictactoe.game.BoardImpl;
import tictactoe.game.ComputerPlayer;
import tictactoe.game.ComputerPlayerImpl;

public class GameModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(Board.class).to(BoardImpl.class);
        bind(ComputerPlayer.class).to(ComputerPlayerImpl.class);
    }
}
