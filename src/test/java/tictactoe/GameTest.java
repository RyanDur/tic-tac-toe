package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class GameTest {

    private final String pieceOne = constants.GAME_PIECE_ONE;
    private final String pieceTwo = constants.GAME_PIECE_TWO;
    private Game game;
    private Board board;
    private ComputerPlayer computer;

    @Before
    public void setup() {
        board = mock(Board.class);
        when(board.getBoard()).thenReturn(new String[constants.SIDE * constants.SIDE]);
        computer = mock(ComputerPlayer.class);
        game = new GameImpl(board, computer);
    }

    @Test
    public void shouldBeAbleToAllowTheComputerToPlayFirstIfItIsX() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(computer.getPiece()).thenReturn(pieceOne);
        game.setup();
        verify(computer).calculateBestMove(any(Board.class));
        verify(board).set(0, 0, pieceOne);
    }

    @Test
    public void shouldNotBeAbleToAllowTheComputerToPlayFirstIfItIsO() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(computer.getPiece()).thenReturn(pieceTwo);
        game.setup();
        verify(computer, never()).calculateBestMove(any(Board.class));
        verify(board, never()).set(anyInt(), anyInt(), anyString());
    }

    @Test
    public void shouldBeAbleToCheckIfGameIsOver() {
        game.setup();
        game.over();
        verify(board).gameOver();
    }

    @Test
    public void shouldBeAbleToGetTheWinner() {
        game.setup();
        game.getWinner();
        verify(board).getWinner();
    }

    @Test
    public void shouldBeAbleToGetTheBoard() {
        game.setup();
        game.getBoard();
        verify(board).getBoard();
    }

    @Test
    public void shouldSetPlayerXOnFirstMoveForTwoPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.setup();
        int row = 1;
        int column = 2;
        game.set(row, column);
        verify(board).set(row, column, pieceOne);
    }

    @Test
    public void shouldSetPlayerOOnSecondMoveForTwoPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.setup();
        int row = 1;
        int column = 2;
        when(board.numOfPieces()).thenReturn(1);
        game.set(row, column);
        verify(board).set(row, column, pieceTwo);
    }

    @Test
    public void shouldSetPlayer1ThenComputerPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        InOrder inOrder = inOrder(board, computer);
        when(computer.getPiece()).thenReturn(pieceTwo);
        game.setComputer(pieceTwo);
        game.setup();
        int row = 1;
        int column  = 2;

        when(board.numOfPieces()).thenReturn(0,1);
        game.set(row, column);

        inOrder.verify(board).set(row, column, pieceOne);
        inOrder.verify(board).copy();
        inOrder.verify(computer).calculateBestMove(any(Board.class));
        inOrder.verify(board).set(0, 0, pieceTwo);
    }

    @Test
    public void shouldNotCallComputerIfGameOver() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(board.getWinner()).thenReturn(pieceOne);
        InOrder inOrder = inOrder(board, computer);
        int row = 1;
        int column = 2;
        game.setup();

        when(board.numOfPieces()).thenReturn(0,1);
        game.set(row, column);

        inOrder.verify(board).set(row, column, pieceOne);
        inOrder.verify(board,never()).copy();
        inOrder.verify(computer, never()).calculateBestMove(any(Board.class));
        inOrder.verify(board, never()).set(anyInt(), anyInt(), anyString());
    }

    @Test
    public void shouldCheckToSeeIfTheComputerPlayerShouldGoIfGameReset() throws OutOfBoundsException, NotVacantException {
        when(board.getWinner()).thenReturn(pieceOne);
        when(computer.getPiece()).thenReturn(pieceOne);
        game.setup();
        game.setup();
        verify(computer, times(2)).calculateBestMove(any(Board.class));
    }

    @Test
    public void shouldBeAbleToRestTheGame() throws OutOfBoundsException, NotVacantException {
        game.setup();
        game.setup();
        verify(board, times(2)).setup(constants.SIDE);
    }
}