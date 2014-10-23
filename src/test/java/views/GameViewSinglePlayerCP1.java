package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import lang.constants;
import models.ComputerPlayer;
import models.Player;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.*;

public class GameViewSinglePlayerCP1 extends GuiTest{
    private Player[] board;
    private Player player1;
    private ComputerPlayer player2;
    private GameCtrl mockGameCtrl;

    @Override
    protected Parent getRootNode() {
        board = new Player[constants.SIDE * constants.SIDE];
        player1 = mock(Player.class);
        player2 = mock(ComputerPlayer.class);
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        mockGameCtrl = mock(GameCtrl.class);
        Button reset = new Button();
        Button replay = new Button();
        Label messages = mock(Label.class);
        board[calc(1, 1)] = player2;
        when(mockGameCtrl.getBoard()).thenReturn(board);
        try {
            return new GameView(mockGameCtrl, player1, player2, messages, reset, replay);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldAllowTheComputerToPlayFirst() throws NotVacantException, OutOfBoundsException {
        verify(player2).calculateBestMove(board);
        verifyThat("#cell4", hasText(constants.GAME_PIECE_ONE));
    }

    @Test
    public void shouldAllowTheHumanPlayerToGoSecond() {
        int x = 0;
        int y = 0;
        String id = "#cell" + calc(x, y);
        board[calc(x, y)] = player1;
        when(mockGameCtrl.getBoard()).thenReturn(board);
        click(id);
        verifyThat(id, hasText(constants.GAME_PIECE_TWO));
    }

    @Test
    public void shouldAllowTheComputerPlayerToGoThird() throws NotVacantException, OutOfBoundsException {
        int x1 = 2;
        int y1 = 2;
        String id1 = "#cell" + calc(x1, y1);
        board[calc(x1, y1)] = player1;
        when(mockGameCtrl.getBoard()).thenReturn(board);
        click(id1);
        verify(player2, times(2)).calculateBestMove(board);
    }

    @Test
    public void shouldAllowTheHumanPlayerToGoFourth() throws NotVacantException, OutOfBoundsException {
        int x1 = 2;
        int y1 = 2;
        String id1 = "#cell" + calc(x1, y1);
        int x2 = 2;
        int y2 = 0;
        String id2 = "#cell" + calc(x2, y2);
        board[calc(x1, y1)] = player1;
        when(mockGameCtrl.getBoard()).thenReturn(board);
        click(id1);
        board[calc(x2, y2)] = player1;
        when(mockGameCtrl.getBoard()).thenReturn(board);
        click(id2);
        verifyThat(id2, hasText(constants.GAME_PIECE_TWO));
        verify(player2, times(3)).calculateBestMove(board);
    }

    private int calc(int x, int y) {
        return (x * constants.SIDE) + y;
    }
}
