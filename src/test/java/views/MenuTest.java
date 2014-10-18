package views;

import controllers.GameCtrl;
import factories.BoardFactory;
import factories.BoardFactoryImpl;
import factories.PlayerFactory;
import javafx.scene.Parent;
import lang.constants;
import models.Player;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.loadui.testfx.Assertions.assertNodeExists;
import static org.mockito.Matchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MenuTest extends GuiTest{

    private Player player1;
    private Player player2;
    private GameCtrl mockGameCtrl;

    @Override
    protected Parent getRootNode() {
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        mockGameCtrl = mock(GameCtrl.class);
        when(mockGameCtrl.getBoard()).thenReturn(new Player[constants.SIDE * constants.SIDE]);
        PlayerFactory mockPlayerFactory = mock(PlayerFactory.class);
        when(mockPlayerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1, player2);
        BoardFactory boardFactory = new BoardFactoryImpl();
        try {
            return new Menu(mockGameCtrl, mockPlayerFactory, boardFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldPresentABoardToPlayOnWhenFirstInvoked() {
        assertNodeExists("#" + constants.BOARD_ID);
    }
}
