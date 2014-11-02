package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ComputerPlayerTest {

    private final String X = constants.GAME_PIECE_ONE;
    private final String O = constants.GAME_PIECE_TWO;
    private ComputerPlayer computer;
    private Board board;

    @Before
    public void setup() {
        computer = new ComputerPlayerImpl();
        board = mock(Board.class);
    }

    @Test
    public void shouldBeAbelToeSetThePieceOfAComputerPlayer() {
        computer.setPiece(X);
        assertThat(computer.getPiece(), is(equalTo(X)));
    }

    @Test
    public void shouldBeAbleToCalculateTheBestMove() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        Board copy = mock(Board.class);
        List<Integer> list = Arrays.asList(1, 2);
        HashSet<List<Integer>> value = new HashSet<>();
        value.add(list);
        when(board.getVacancies()).thenReturn(value);
        when(board.copy()).thenReturn(copy);
        computer.setPiece(X);
        computer.calculateBestMove(board);
        verify(copy, times(3)).set(1, 2, X);
    }
}