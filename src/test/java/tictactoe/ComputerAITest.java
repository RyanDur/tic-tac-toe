package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputerAITest {

    private Integer[] topMiddle = new Integer[]{0, 1};
    private Integer[] topRight = new Integer[]{0, 2};
    private Integer[] middleRight = new Integer[]{1, 2};
    private Integer[] bottomMiddle = new Integer[]{2, 1};
    private Integer[] bottomRight = new Integer[]{2, 2};
    private Board board = mock(Board.class);
    private String pieceOne = constants.GAME_PIECE_ONE;

    // X| |
    // -----
    // O|X|
    // -----
    // O| |
    @Test
    public void shouldBeAbleToGetTheWinningMoveForTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        List<Integer[]> vacancies = Arrays.<Integer[]>asList(topMiddle, topRight, middleRight, bottomMiddle, bottomRight);
        when(board.getVacancies()).thenReturn(vacancies);

        Board copy = mock(Board.class);
        when(board.copy()).thenReturn(copy);
        when(copy.getWinner()).thenReturn(null, null, null, null, pieceOne);

        ComputerAI ai = new ComputerAIImpl();
        ai.setBoard(board);
        assertThat(ai.findWinningMove(pieceOne).get(), is(equalTo(bottomRight)));
    }
}