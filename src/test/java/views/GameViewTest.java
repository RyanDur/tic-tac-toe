package views;

import controllers.GameCtrl;
import controllers.PlayerCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import lang.constants;
import models.ComputerPlayer;
import models.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;
import org.mockito.InOrder;

import java.io.IOException;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class GameViewTest extends GuiTest {

    private Player player1;
    private Player player2;
    private GameCtrl mockGameCtrl;
    private Player[] board;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Button replay;

    @Override
    protected Parent getRootNode() {
        board = new Player[constants.SIDE * constants.SIDE];
        Player player1 = mock(Player.class);
        player2 = mock(ComputerPlayer.class);
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        GameCtrl mockGameCtrl = mock(GameCtrl.class);
        PlayerCtrl playerCtrl = mock(PlayerCtrl.class);
        Function<MouseEvent, Player[]> play = null;
        when(mockGameCtrl.getBoard()).thenReturn(board);
        try {
            return new GameView(mockGameCtrl, playerCtrl, play);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
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

    @Test
    public void shouldSetupTheGameBeforeGettingTheBoard() {
        InOrder order = inOrder(mockGameCtrl);
        order.verify(mockGameCtrl).setup();
        order.verify(mockGameCtrl).getBoard();
    }

    @Test
    public void shouldGetAppropriateMessageIfXWins() {
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(mockGameCtrl.getWinner()).thenReturn(player1);
        when(mockGameCtrl.gameOver()).thenReturn(false, true);
        click("#cell" + 3);
        verify(player1).getPiece();
    }

    @Test
    public void shouldGetAppropriateMessageIfOWins() {
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(mockGameCtrl.getWinner()).thenReturn(player2);
        click("#cell" + 3);
        when(mockGameCtrl.gameOver()).thenReturn(false, true);
        click("#cell" + 4);
        verify(player2).getPiece();
    }

    private int calc(int x, int y) {
        return (x * constants.SIDE) + y;
    }
}
