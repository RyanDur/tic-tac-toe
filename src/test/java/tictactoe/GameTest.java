package tictactoe;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;
import tictactoe.lang.Constants;

import java.util.Arrays;

import static org.mockito.Mockito.*;

public class GameTest {

    private final Character pieceOne = Constants.GAME_PIECE_ONE;
    private final Character pieceTwo = Constants.GAME_PIECE_TWO;
    private Game game;
    private Board board;
    private ComputerPlayer computer;

    @Before
    public void setup() {
        board = mock(Board.class);
        when(board.getBoard()).thenReturn(new Character[Constants.SIDE * Constants.SIDE]);
        computer = mock(ComputerPlayer.class);
        game = new GameImpl(board, computer);
        game.setup(pieceOne);
    }

    @Test
    public void shouldBeAbleToAllowTheComputerToPlayFirstIfItIsX() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(computer.getMove(any(Board.class))).thenReturn(Arrays.asList(1,2));
        when(computer.getPiece()).thenReturn(pieceOne);
        game.setup(pieceOne);
        verify(computer).getMove(any(Board.class));
    }

    @Test
    public void shouldNotBeAbleToAllowTheComputerToPlayFirstIfItIsO() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(computer.getPiece()).thenReturn(pieceTwo);
        verify(computer, never()).getMove(any(Board.class));
        verify(board, never()).set(anyInt(), anyInt(), anyChar());
    }

    @Test
    public void shouldBeAbleToCheckIfGameIsOver() {
        game.over();
        verify(board).gameOver();
    }

    @Test
    public void shouldBeAbleToGetTheWinner() {
        game.getWinner();
        verify(board).getWinner();
    }

    @Test
    public void shouldBeAbleToGetTheBoard() {
        game.getBoard();
        verify(board).getBoard();
    }

    @Test
    public void shouldSetPlayerXOnFirstMoveForTwoPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        int row = 1;
        int column = 2;
        game.set(row, column);
        verify(board).set(row, column, pieceOne);
    }

    @Test
    public void shouldSetPlayerOOnSecondMoveForTwoPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        int row = 1;
        int column = 2;
        when(board.numOfPieces()).thenReturn(1);
        game.set(row, column);
        verify(board).set(row, column, pieceTwo);
    }

    @Test
    public void shouldSetPlayer1ThenComputerPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        when(computer.getMove(any(Board.class))).thenReturn(Arrays.asList(1,2));
        InOrder inOrder = inOrder(board, computer);
        when(computer.getPiece()).thenReturn(pieceTwo);
        int row = 1;
        int column  = 2;

        when(board.numOfPieces()).thenReturn(0,1);
        game.set(row, column);
        inOrder.verify(board).set(row, column, pieceOne);
        inOrder.verify(computer).getMove(any(Board.class));
    }

    @Test
    public void shouldNotCallComputerIfGameOver() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(board.getWinner()).thenReturn(pieceOne);
        InOrder inOrder = inOrder(board, computer);
        int row = 1;
        int column = 2;

        when(board.numOfPieces()).thenReturn(0,1);
        game.set(row, column);

        inOrder.verify(board).set(row, column, pieceOne);
        inOrder.verify(computer, never()).getMove(any(Board.class));
    }

    @Test
    public void shouldCheckToSeeIfTheComputerPlayerShouldGoIfGameReset() throws OutOfBoundsException, NotVacantException {
        when(computer.getMove(any(Board.class))).thenReturn(Arrays.asList(1,2));
        when(board.getWinner()).thenReturn(pieceOne);
        when(computer.getPiece()).thenReturn(pieceOne);
        game.setup(pieceOne);
        game.setup(pieceOne);
        verify(computer, times(2)).getMove(any(Board.class));
    }

    @Test
    public void shouldBeAbleToRestTheGame() throws OutOfBoundsException, NotVacantException {
        game.setup(pieceOne);
        verify(board, times(2)).setup(Constants.SIDE);
    }
}