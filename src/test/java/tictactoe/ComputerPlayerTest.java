package tictactoe;

import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
    public void shouldBeAbleToCalculateTheBestMove() {
        Board copy = mock(Board.class);
        when(board.getVacancies()).thenReturn(Arrays.asList(Arrays.asList(1,2)));
        when(board.copy()).thenReturn(copy);
        computer.setPiece(pieceOne);
        List<Integer> move = computer.calculateBestMove(board).get();
        assertThat(move.get(0), is(equalTo(1)));
        assertThat(move.get(1), is(equalTo(2)));
    }
}