package tictactoe;

import lang.constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class ComputerPlayerTest {

    private final String pieceOne = constants.GAME_PIECE_ONE;
    private final String pieceTwo = constants.GAME_PIECE_TWO;
    private ComputerAI ai;
    private ComputerPlayer computer;
    private Board board;

    @Before
    public void setup() {
        ai = mock(ComputerAI.class);
        computer = new ComputerPlayerImpl(ai);
        board = mock(Board.class);
    }

    @Test
    public void shouldBeAbelToeSetThePieceOfAComputerPlayer() {
        computer.setPiece(pieceOne);
        assertThat(computer.getPiece(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldBeAbleToCalculateTheBestMove() {
        when(ai.findWinningMove(anyString())).thenReturn(Optional.empty());
        when(ai.getBestMove(anyString(), anyString())).thenReturn(Optional.of(new Integer[]{1, 2}));
        computer.setPiece(pieceOne);
        InOrder inOrder = inOrder(ai);
        computer.calculateBestMove(board);

        inOrder.verify(ai).setBoard(board);
        inOrder.verify(ai).findWinningMove(pieceOne);
        inOrder.verify(ai).findWinningMove(pieceTwo);
        inOrder.verify(ai).getBestMove(pieceOne, pieceTwo);
        assertThat(computer.getRow(), is(equalTo(1)));
        assertThat(computer.getColumn(), is(equalTo(2)));
    }

    @Test
    public void shouldBeAbleToFindTheWinningMove() {
        when(ai.findWinningMove(pieceOne)).thenReturn(Optional.of(new Integer[]{1, 2}));
        when(ai.findWinningMove(pieceTwo)).thenReturn(Optional.empty());
        when(ai.getBestMove(anyString(), anyString())).thenReturn(Optional.empty());
        computer.setPiece(pieceOne);
        InOrder inOrder = inOrder(ai);
        computer.calculateBestMove(board);

        inOrder.verify(ai).setBoard(board);
        inOrder.verify(ai).findWinningMove(pieceOne);
        inOrder.verify(ai, never()).findWinningMove(pieceTwo);
        inOrder.verify(ai, never()).getBestMove(pieceOne, pieceTwo);
        assertThat(computer.getRow(), is(equalTo(1)));
        assertThat(computer.getColumn(), is(equalTo(2)));
    }

    @Test
    public void shouldBeAbleToFindTheLosingMove() {
        when(ai.findWinningMove(pieceOne)).thenReturn(Optional.empty());
        when(ai.findWinningMove(pieceTwo)).thenReturn(Optional.of(new Integer[]{1, 2}));
        when(ai.getBestMove(anyString(), anyString())).thenReturn(Optional.empty());
        computer.setPiece(pieceOne);
        InOrder inOrder = inOrder(ai);
        computer.calculateBestMove(board);

        inOrder.verify(ai).setBoard(board);
        inOrder.verify(ai).findWinningMove(pieceOne);
        inOrder.verify(ai).findWinningMove(pieceTwo);
        inOrder.verify(ai, never()).getBestMove(pieceOne, pieceTwo);
        assertThat(computer.getRow(), is(equalTo(1)));
        assertThat(computer.getColumn(), is(equalTo(2)));
    }
}