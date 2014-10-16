package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import factories.BoardFactory;
import lang.constants;
import models.Board;
import models.Player;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class GameCtrlTest {
    private GameCtrl gameCtrl;
    private BoardFactory mockBoardFactory;
    private Board mockBoard;
    private Player mockPlayer;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        mockBoardFactory = mock(BoardFactory.class);
        mockBoard = mock(Board.class);
        mockPlayer = mock(Player.class);
        when(mockPlayer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(mockBoard.getNumOfPieces()).thenReturn(0);
        when(mockBoard.isVacant(anyInt(), anyInt())).thenReturn(true);
        when(mockBoardFactory.createBoard(anyInt(), anyInt())).thenReturn(mockBoard);

        gameCtrl = new GameCtrlImpl(mockBoardFactory);
        gameCtrl.setup();
    }

    @Test
    public void shouldBeAbleToSetupAGame() {
        verify(mockBoardFactory).createBoard(constants.HEIGHT, constants.WIDTH);
    }

    @Test
    public void shouldAllowAPlayerToPlaceAPieceOnTheBoard() throws OutOfTurnException, NotVacantException {
        gameCtrl.setPiece(mockPlayer);
        verify(mockBoard).set(anyInt(), anyInt(), any(Player.class));
    }

    @Test
    public void shouldAllowForXToGoFirst() throws OutOfTurnException, NotVacantException {
        gameCtrl.setPiece(mockPlayer);
        verify(mockBoard).set(anyInt(), anyInt(), any(Player.class));
    }

    @Test
    public void shouldNotAllowForOToGoFirst() throws OutOfTurnException, NotVacantException {
        exception.expect(OutOfTurnException.class);
        when(mockPlayer.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        gameCtrl.setPiece(mockPlayer);
    }

    @Test
    public void shouldNotAllowForOToPlayOutOfTurn() throws OutOfTurnException, NotVacantException {
        exception.expect(OutOfTurnException.class);
        gameCtrl.setPiece(mockPlayer);
        when(mockPlayer.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(mockBoard.getNumOfPieces()).thenReturn(1, 2);
        gameCtrl.setPiece(mockPlayer);
        gameCtrl.setPiece(mockPlayer);
    }

    @Test
    public void shouldNotAllowForXToPlayOutOfTurn() throws OutOfTurnException, NotVacantException {
        exception.expect(OutOfTurnException.class);
        gameCtrl.setPiece(mockPlayer);
        when(mockPlayer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(mockBoard.getNumOfPieces()).thenReturn(1);
        gameCtrl.setPiece(mockPlayer);
    }

    @Test
    public void shouldNotBeAbleToPlaceAPieceOnASpaceThatHasAlreadyBeenTaken() throws OutOfTurnException, NotVacantException {
        exception.expect(NotVacantException.class);
        when(mockBoard.isVacant(anyInt(), anyInt())).thenReturn(false);
        gameCtrl.setPiece(mockPlayer);
    }

    @Test
    public void shouldBeAbleToTellIfTheGameIsOverIfThereIsAWinner() {
        when(mockBoard.winner()).thenReturn(true);
        when(mockBoard.full()).thenReturn(false);
        assertThat(gameCtrl.gameOver(), is(true));
    }

    @Test
    public void shouldBeAbleToTellIfTheGameIsOverIfTheGameIsADraw() {
        when(mockBoard.winner()).thenReturn(false);
        when(mockBoard.full()).thenReturn(true);
        assertThat(gameCtrl.gameOver(), is(true));
    }

    @Test
    public void shouldBeAbleToGetTheWinner() throws OutOfTurnException, NotVacantException {
        when(mockBoard.winner()).thenReturn(true);
        when(mockBoard.full()).thenReturn(false);
        gameCtrl.setPiece(mockPlayer);
        gameCtrl.gameOver();
        assertThat(gameCtrl.getWinner(), is(equalTo(mockPlayer)));
    }

    @Test
    public void shouldNotBeAWinnerIfThereIsADraw() throws OutOfTurnException, NotVacantException {
        when(mockBoard.winner()).thenReturn(false);
        when(mockBoard.full()).thenReturn(true);
        gameCtrl.setPiece(mockPlayer);
        gameCtrl.gameOver();
        assertThat(gameCtrl.getWinner(), equalTo(null));
    }

    @Test
    public void shouldBeNoWinnerIfStartingANewGame() throws OutOfTurnException, NotVacantException {
        when(mockBoard.winner()).thenReturn(true);
        when(mockBoard.full()).thenReturn(false);
        gameCtrl.setPiece(mockPlayer);
        gameCtrl.gameOver();
        assertThat(gameCtrl.getWinner(), is(equalTo(mockPlayer)));
        gameCtrl.setup();
        assertThat(gameCtrl.getWinner(), equalTo(null));
    }
}