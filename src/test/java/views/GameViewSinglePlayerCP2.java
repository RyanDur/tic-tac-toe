package views;

import controllers.GameCtrl;
import controllers.PlayerCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lang.constants;
import models.ComputerPlayer;
import models.Player;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;
import java.util.function.Function;

import static org.mockito.Mockito.*;

public class GameViewSinglePlayerCP2 extends GuiTest {

    private Player[] board;
    private ComputerPlayer player2;

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
    public void shouldNotInvokeTheComputerPlayerIfItIsO() throws NotVacantException, OutOfBoundsException {
        verify(player2, never()).calculateBestMove(board);
    }

    private int calc(int x, int y) {
        return (x * constants.SIDE) + y;
    }
}
