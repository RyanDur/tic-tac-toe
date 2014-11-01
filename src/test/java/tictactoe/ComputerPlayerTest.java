package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class ComputerPlayerTest {

    private final String pieceOne = constants.GAME_PIECE_ONE;
    private ComputerPlayer computer;
    private Board board;

    @Before
    public void setup() {
        computer = new ComputerPlayerImpl();
        board = mock(Board.class);
    }

    @Test
    public void shouldBeAbelToeSetThePieceOfAComputerPlayer() {
        computer.setPiece(pieceOne);
        assertThat(computer.getPiece(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldBeAbleToCalculateTheBestMove() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        Board copy = mock(Board.class);
        when(board.getVacancies()).thenReturn(Arrays.asList(Arrays.asList(1,2)));
        when(board.copy()).thenReturn(copy);
        computer.setPiece(pieceOne);
        computer.calculateBestMove(board);
        verify(copy, times(2)).set(1,2,pieceOne);
    }
}