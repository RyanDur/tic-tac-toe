package controllers;

import exceptions.OutOfTurnException;
import factories.BoardFactory;
import lang.constants;
import models.Board;
import models.Player;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.mockito.Mockito.*;

public class GameCtrlTest {
    private GameCtrl gameCtrl;
    private BoardFactory boardFactory;
    private Board mockBoard;
    private Player player;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        boardFactory = mock(BoardFactory.class);
        mockBoard = mock(Board.class);
        gameCtrl = new GameCtrlImpl(boardFactory);
        player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(mockBoard.getNumOfPieces()).thenReturn(0);
        when(boardFactory.createBoard(anyInt(), anyInt())).thenReturn(mockBoard);
        gameCtrl.setup();
    }

    @Test
    public void shouldBeAbleToSetupAGame() {
        verify(boardFactory).createBoard(constants.HEIGHT, constants.WIDTH);
    }

    @Test
    public void shouldAllowAPlayerToPlaceAPieceOnTheBoard() throws OutOfTurnException {
        gameCtrl.setPiece(player);
        verify(mockBoard).place(anyInt(), anyInt(), any(Player.class));
    }

    @Test
    public void shouldAllowForXToGoFirst() throws OutOfTurnException {
        gameCtrl.setPiece(player);
        verify(mockBoard).place(anyInt(), anyInt(), any(Player.class));
    }

    @Test
    public void shouldNotAllowForOToGoFirst() throws OutOfTurnException {
        exception.expect(OutOfTurnException.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        gameCtrl.setPiece(player);
    }

    @Test
    public void shouldNotAllowForOToPlayOutOfTurn() throws OutOfTurnException {
        exception.expect(OutOfTurnException.class);
        gameCtrl.setPiece(player);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(mockBoard.getNumOfPieces()).thenReturn(1, 2);
        gameCtrl.setPiece(player);
        gameCtrl.setPiece(player);
    }

    @Test
    public void shouldNotAllowForXToPlayOutOfTurn() throws OutOfTurnException {
        exception.expect(OutOfTurnException.class);
        gameCtrl.setPiece(player);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(mockBoard.getNumOfPieces()).thenReturn(1);
        gameCtrl.setPiece(player);
    }
}
