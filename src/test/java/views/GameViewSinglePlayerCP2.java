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

import static org.mockito.Mockito.*;

public class GameViewSinglePlayerCP2 extends GuiTest {

    private Player[] board;
    private Player player1;
    private ComputerPlayer player2;
    private GameCtrl mockGameCtrl;

    @Override
    protected Parent getRootNode() {
        board = new Player[constants.SIDE * constants.SIDE];
        player1 = mock(Player.class);
        player2 = mock(ComputerPlayer.class);
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        mockGameCtrl = mock(GameCtrl.class);
        Button reset = new Button();
        Button replay = new Button();
        Label messages = mock(Label.class);
        when(mockGameCtrl.getBoard()).thenReturn(board);
        try {
            return new GameView(mockGameCtrl, player1, player2, messages, reset, replay);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldNotInvokeTheComputerPlayerIfItIsO() throws NotVacantException, OutOfBoundsException {
        verify(player2, never()).calculateBestMove(board);
    }

    private int calc(int x, int y) {
        return (x * constants.SIDE) + y;
    }
}
