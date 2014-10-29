package tictactoe;

import lang.constants;
import models.StrategyGameCtrl;
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
    private StrategyGameCtrl game;
    private ComputerPlayer computer;

    @Before
    public void setup() {
        game = mock(StrategyGameCtrl.class);
        computer = new ComputerPlayerImpl(game);
    }

    @Test
    public void shouldBeAbelToeSetThePieceOfAComputerPlayer() {
        computer.setPiece(pieceOne);
        assertThat(computer.getPiece(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldBeAbleToCalculateTheBestMove() {
        when(game.findWinningMove(anyString())).thenReturn(Optional.empty());
        when(game.getBestMove(anyString(), anyString())).thenReturn(Optional.of(new Integer[]{1,2}));
        computer.setPiece(pieceOne);
        InOrder inOrder = inOrder(game);
        String[] board = {};
        computer.calculateBestMove(board);

        inOrder.verify(game).setBoard(board);
        inOrder.verify(game).findWinningMove(pieceOne);
        inOrder.verify(game).findWinningMove(pieceTwo);
        inOrder.verify(game).getBestMove(pieceOne, pieceTwo);
        assertThat(computer.getRow(), is(equalTo(1)));
        assertThat(computer.getColumn(), is(equalTo(2)));
    }

    @Test
    public void shouldBeAbleToFindTheWinningMove() {
        when(game.findWinningMove(pieceOne)).thenReturn(Optional.of(new Integer[]{1,2}));
        when(game.findWinningMove(pieceTwo)).thenReturn(Optional.empty());
        when(game.getBestMove(anyString(), anyString())).thenReturn(Optional.empty());
        computer.setPiece(pieceOne);
        InOrder inOrder = inOrder(game);
        String[] board = {};
        computer.calculateBestMove(board);

        inOrder.verify(game).setBoard(board);
        inOrder.verify(game).findWinningMove(pieceOne);
        inOrder.verify(game, never()).findWinningMove(pieceTwo);
        inOrder.verify(game, never()).getBestMove(pieceOne, pieceTwo);
        assertThat(computer.getRow(), is(equalTo(1)));
        assertThat(computer.getColumn(), is(equalTo(2)));
    }

    @Test
    public void shouldBeAbleToFindTheLosingMove() {
        when(game.findWinningMove(pieceOne)).thenReturn(Optional.empty());
        when(game.findWinningMove(pieceTwo)).thenReturn(Optional.of(new Integer[]{1, 2}));
        when(game.getBestMove(anyString(), anyString())).thenReturn(Optional.empty());
        computer.setPiece(pieceOne);
        InOrder inOrder = inOrder(game);
        String[] board = {};
        computer.calculateBestMove(board);

        inOrder.verify(game).setBoard(board);
        inOrder.verify(game).findWinningMove(pieceOne);
        inOrder.verify(game).findWinningMove(pieceTwo);
        inOrder.verify(game, never()).getBestMove(pieceOne, pieceTwo);
        assertThat(computer.getRow(), is(equalTo(1)));
        assertThat(computer.getColumn(), is(equalTo(2)));
    }
}