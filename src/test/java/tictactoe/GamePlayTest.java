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

public class GamePlayTest {

    private final Character pieceOne = Constants.GAME_PIECE_ONE;
    private final Character pieceTwo = Constants.GAME_PIECE_TWO;
    private GamePlay gamePlay;
    private Game game;
    private ComputerPlayer computer;

    @Before
    public void setup() {
        game = mock(Game.class);
        when(game.getBoard()).thenReturn(new Character[Constants.SIDE * Constants.SIDE]);
        computer = mock(ComputerPlayer.class);
        gamePlay = new GamePlayImpl(game, computer);
        gamePlay.setup(pieceOne);
    }

    @Test
    public void shouldBeAbleToAllowTheComputerToPlayFirstIfItIsX() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(game.numOfPieces()).thenReturn(0, 1);
        when(computer.getMove(any(Game.class))).thenReturn(Arrays.asList(1,2));
        when(computer.getPiece()).thenReturn(pieceOne);
        gamePlay.setup(pieceOne);
        verify(computer).getMove(any(Game.class));
    }

    @Test
    public void shouldNotBeAbleToAllowTheComputerToPlayFirstIfItIsO() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(computer.getPiece()).thenReturn(pieceTwo);
        verify(computer, never()).getMove(any(Game.class));
        verify(game, never()).set(anyInt(), anyInt(), anyChar());
    }

    @Test
    public void shouldBeAbleToCheckIfGameIsOver() {
        gamePlay.isOver();
        verify(game).isOver();
    }

    @Test
    public void shouldBeAbleToGetTheWinner() {
        gamePlay.getWinner();
        verify(game).getWinner();
    }

    @Test
    public void shouldBeAbleToGetTheBoard() {
        gamePlay.getBoard();
        verify(game).getBoard();
    }

    @Test
    public void shouldSetPlayerXOnFirstMoveForTwoPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        int row = 1;
        int column = 2;
        gamePlay.set(row, column);
        verify(game).set(row, column, pieceOne);
    }

    @Test
    public void shouldSetPlayerOOnSecondMoveForTwoPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        int row = 1;
        int column = 2;
        when(game.numOfPieces()).thenReturn(1);
        gamePlay.set(row, column);
        verify(game).set(row, column, pieceTwo);
    }

    @Test
    public void shouldSetPlayer1ThenComputerPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        when(computer.getMove(any(Game.class))).thenReturn(Arrays.asList(1,2));
        InOrder inOrder = inOrder(game, computer);
        when(computer.getPiece()).thenReturn(pieceTwo);
        int row = 1;
        int column  = 2;

        when(game.numOfPieces()).thenReturn(0, 1, 2);
        gamePlay.set(row, column);
        inOrder.verify(game).set(row, column, pieceOne);
        inOrder.verify(computer).getMove(any(Game.class));
    }

    @Test
    public void shouldNotCallComputerIfGameOver() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(game.getWinner()).thenReturn(pieceOne);
        InOrder inOrder = inOrder(game, computer);
        int row = 1;
        int column = 2;

        when(game.numOfPieces()).thenReturn(0, 1, 2);
        gamePlay.set(row, column);

        inOrder.verify(game).set(row, column, pieceOne);
        inOrder.verify(computer, never()).getMove(any(Game.class));
    }

    @Test
    public void shouldCheckToSeeIfTheComputerPlayerShouldGoIfGameReset() throws OutOfBoundsException, NotVacantException {
        when(computer.getMove(any(Game.class))).thenReturn(Arrays.asList(1,2));
        when(game.getWinner()).thenReturn(pieceOne);
        when(computer.getPiece()).thenReturn(pieceOne);
        when(game.numOfPieces()).thenReturn(0,1);
        gamePlay.setup(pieceOne);
        when(game.numOfPieces()).thenReturn(0, 1);
        gamePlay.setup(pieceOne);
        verify(computer, times(2)).getMove(any(Game.class));
    }

    @Test
    public void shouldBeAbleToRestTheGame() throws OutOfBoundsException, NotVacantException {
        gamePlay.setup(pieceOne);
        verify(game, times(2)).setup(Constants.SIDE);
    }
}