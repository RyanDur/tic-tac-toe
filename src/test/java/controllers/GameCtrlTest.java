package controllers;

import factories.BoardFactory;
import models.Board;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class GameCtrlTest {
    private GameCtrl gameCtrl;
    private BoardFactory boardFactory;
    private Board mockBoard;

    @Before
    public void setup() {
        boardFactory = mock(BoardFactory.class);
        mockBoard = mock(Board.class);
        gameCtrl = new GameCtrlImpl(boardFactory);

        when(boardFactory.createBoard()).thenReturn(mockBoard);
        gameCtrl.setup();
    }

    @Test
    public void shouldBeAbleToSetupAGame() {
        verify(boardFactory).createBoard();
    }

    @Test
    public void shouldAllowAPlayerToPlaceAPieceOnTheBoard() {
        Player player = mock(Player.class);
        gameCtrl.setPiece(player);
        verify(mockBoard).place(anyInt(), anyInt(), any(Player.class));
    }
}
