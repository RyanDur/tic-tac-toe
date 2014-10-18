package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import factories.PlayerFactory;
import javafx.scene.Parent;
import lang.constants;
import models.Player;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.impl.ContainsNodesMatcher.contains;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GameViewTest extends GuiTest {

    private Player player1;
    private Player player2;
    private GameCtrl mockGameCtrl;
    private Player[] board;

    @Override
    protected Parent getRootNode() {
        board = new Player[constants.SIDE * constants.SIDE];
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        mockGameCtrl = mock(GameCtrl.class);
        when(mockGameCtrl.getBoard()).thenReturn(new Player[constants.SIDE * constants.SIDE]);
        PlayerFactory mockPlayerFactory = mock(PlayerFactory.class);
        when(mockPlayerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1, player2);
        try {
            return new GameView(mockGameCtrl, mockPlayerFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveABoardThatReflectsTheBoardForTheGame() {
        verifyThat("#board", contains(board.length, ".label"));
    }

    @Test
    public void shouldBeAbleToChooseAPlaceOnTheBoard() throws OutOfTurnException, NotVacantException {
        int x = 1;
        int y = 1;
        String piece = "X";
        String id = "#cell" + calc(x, y);
        board[calc(x, y)] = player1;
        when(player1.getPiece()).thenReturn(piece);
        when(mockGameCtrl.getBoard()).thenReturn(board);

        click(id);
        verify(mockGameCtrl).setPiece(player1);
    }

    @Test
    public void shouldMakeSureToAlternateBetweenPlayers() throws OutOfTurnException, NotVacantException {
        int x1 = 1;
        int y1 = 1;
        int x2 = 2;
        int y2 = 2;
        String piece1 = "X";
        String piece2 = "O";
        String id1 = "#cell" + calc(x1, y1);
        String id2 = "#cell" + calc(x2, y2);
        board[calc(x1, y1)] = player1;
        when(player1.getPiece()).thenReturn(piece1);
        when(player2.getPiece()).thenReturn(piece2);
        when(mockGameCtrl.getBoard()).thenReturn(board);

        click(id1);
        verify(mockGameCtrl).setPiece(player1);
        board[calc(x2, y2)] = player2;
        click(id2);
        verify(mockGameCtrl).setPiece(player2);
    }

    @Test
    public void shouldMakeSureTheSpacesAreNotChangedByClickIfGameOver() throws OutOfTurnException, NotVacantException {
        int x1 = 1;
        int y1 = 1;
        int x2 = 2;
        int y2 = 2;
        String piece1 = "X";
        String piece2 = "O";
        String id1 = "#cell" + calc(x1, y1);
        String id2 = "#cell" + calc(x2, y2);
        board[calc(x1, y1)] = player1;
        when(player1.getPiece()).thenReturn(piece1);
        when(player2.getPiece()).thenReturn(piece2);
        when(mockGameCtrl.getBoard()).thenReturn(board);

        click(id1);
        when(mockGameCtrl.gameOver()).thenReturn(true);
        verify(mockGameCtrl).setPiece(player1);
        board[calc(x2, y2)] = player2;
        click(id2);
        verify(mockGameCtrl, never()).setPiece(player2);
    }

    private int calc(int x, int y) {
        return (x * constants.SIDE) + y;
    }
}
