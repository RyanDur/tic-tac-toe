package tictactoe;

import org.junit.Before;
import org.junit.Test;
import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.lang.Constants;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputerPlayerTest {

    private final Character X = Constants.GAME_PIECE_ONE;
    private ComputerPlayer computer;
    private Game game;

    @Before
    public void setup() {
        computer = new ComputerPlayerImpl();
        game = mock(Game.class);
    }

    @Test
    public void shouldBeAbleToCalculateTheBestMove() throws OutOfBoundsException, NotVacantException {
        Game copy = mock(Game.class);
        List<Integer> list = Arrays.asList(1, 2);
        HashSet<List<Integer>> value = new HashSet<>();
        value.add(list);
        when(game.getVacancies()).thenReturn(value);
        when(game.copy()).thenReturn(copy);
        when(copy.isOver()).thenReturn(true);
        when(copy.getWinner()).thenReturn(X);
        computer.setPiece(X);
        assertThat(computer.getMove(game), is(equalTo(list)));
    }
}