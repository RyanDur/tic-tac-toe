package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import factories.BoardFactory;
import factories.GameFactory;
import lang.constants;
import models.Game;
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
    private GameFactory mockGameFactory;
    private Game mockGame;
    private Player mockPlayer;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private BoardFactory boardFactory;
    private String pieceOne = constants.GAME_PIECE_ONE;

    @Before
    public void setup() {
        mockGameFactory = mock(GameFactory.class);
        boardFactory = mock(BoardFactory.class);
        mockGame = mock(Game.class);
        mockPlayer = mock(Player.class);
        when(mockPlayer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(mockGameFactory.createGame(anyInt(), any(BoardFactory.class))).thenReturn(mockGame);

        gameCtrl = new GameCtrlImpl(mockGameFactory, boardFactory);
        gameCtrl.setup();
    }

    @Test
    public void shouldBeAbleToSetupAGame() {
        verify(mockGameFactory).createGame(constants.SIDE, boardFactory);
    }

    @Test
    public void shouldAllowAPlayerToPlaceAPieceOnTheBoard() throws OutOfTurnException, NotVacantException {
        gameCtrl.setPiece(mockPlayer);
        verify(mockGame).set(any(Player.class));
    }

    @Test
    public void shouldAllowForXToGoFirst() throws OutOfTurnException, NotVacantException {
        gameCtrl.setPiece(mockPlayer);
        verify(mockGame).set(any(Player.class));
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
        when(mockGame.getNumOfPieces()).thenReturn(1, 2);
        gameCtrl.setPiece(mockPlayer);
        gameCtrl.setPiece(mockPlayer);
    }

    @Test
    public void shouldNotAllowForXToPlayOutOfTurn() throws OutOfTurnException, NotVacantException {
        exception.expect(OutOfTurnException.class);
        gameCtrl.setPiece(mockPlayer);
        when(mockPlayer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(mockGame.getNumOfPieces()).thenReturn(1);
        gameCtrl.setPiece(mockPlayer);
    }

    @Test
    public void shouldBeAbleToTellIfTheGameIsOverIfThereIsAWinner() {
        when(mockGame.getWinner()).thenReturn(pieceOne);
        when(mockGame.full()).thenReturn(false);
        assertThat(gameCtrl.gameOver(), is(true));
    }

    @Test
    public void shouldBeAbleToTellIfTheGameIsOverIfTheGameIsADraw() {
        when(mockGame.getWinner()).thenReturn(null);
        when(mockGame.full()).thenReturn(true);
        assertThat(gameCtrl.gameOver(), is(true));
    }

    @Test
    public void shouldBeAbleToGetTheWinner() throws OutOfTurnException, NotVacantException {
        when(mockGame.getWinner()).thenReturn(pieceOne);
        when(mockGame.full()).thenReturn(false);
        gameCtrl.setPiece(mockPlayer);
        gameCtrl.gameOver();
        assertThat(gameCtrl.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldNotBeAWinnerIfThereIsADraw() throws OutOfTurnException, NotVacantException {
        when(mockGame.full()).thenReturn(true);
        gameCtrl.setPiece(mockPlayer);
        gameCtrl.gameOver();
        assertThat(gameCtrl.getWinner(), equalTo(null));
    }

    @Test
    public void shouldBeAbleToGetACopyOfTheBoard() {
        String[] expected = new String[constants.SIDE * constants.SIDE];
        when(mockGame.getBoard()).thenReturn(expected);
        assertThat(mockGame.getBoard(), is(equalTo(expected)));
    }
}