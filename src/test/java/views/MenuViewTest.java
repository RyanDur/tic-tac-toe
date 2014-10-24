package views;

import controllers.GameCtrl;
import factories.GameViewFactory;
import factories.GameViewFactoryImpl;
import factories.PlayerFactory;
import javafx.scene.Parent;
import lang.constants;
import models.ComputerPlayer;
import models.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.exceptions.NoNodesFoundException;
import org.loadui.testfx.exceptions.NoNodesVisibleException;

import java.io.IOException;

import static org.loadui.testfx.controls.impl.ContainsNodesMatcher.contains;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MenuViewTest extends GuiTest{

    private final String twoPlayerId = "#two_player";
    private final String onePlayer = "1 Player";
    private final String onePlayerId = "#one_player";
    private final String menuId = "#menu";
    private final String gameId = "#game";
    private PlayerFactory playerFactory;
    private String twoPlayer = "2 Player";

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private final GameCtrl gameCtrl = mock(GameCtrl.class);

    @Override
    protected Parent getRootNode() {
        playerFactory = mock(PlayerFactory.class);
        GameViewFactory gameViewFactory = new GameViewFactoryImpl();
        try {
            return new MenuView(gameCtrl, playerFactory, gameViewFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldHaveAOnePlayerButton() {
        verifyThat(onePlayerId, hasText(onePlayer));
    }

    @Test
    public void shouldHaveATwoPlayerButton() {
        verifyThat(twoPlayerId, hasText(twoPlayer));
    }

    @Test
    public void shouldCreateTwoPlayersIfTwoPlayerIsChosen() {
        click(twoPlayer);
        verify(playerFactory, times(2)).createPlayer(anyString(), anyInt());
    }

    @Test
    public void shouldChangeTheButtonsToChooseXOrOIfOnePlayerIsChosen() {
        click(onePlayer);
        verifyThat(onePlayerId, hasText(constants.GAME_PIECE_ONE));
        verifyThat(twoPlayerId, hasText(constants.GAME_PIECE_TWO));
    }

    @Test
    public void shouldMakeTheComputerPlayerXWhenOIsChosen() {
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);

        verify(playerFactory).createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
//        verify(playerFactory).createComputerPlayer(anyString(), anyInt(), any(Player.class));
//        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_ONE, constants.SIDE, null);
    }

    @Test
    public void shouldMakeTheComputerPlayerOWhenXIsChosen() {
        click(onePlayer);
        click(constants.GAME_PIECE_ONE);
        Player human = mock(Player.class);
        when(playerFactory.createPlayer(anyString(),anyInt())).thenReturn(human);

        verify(playerFactory).createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
//        verify(playerFactory).createComputerPlayer(anyString(), anyInt(), any(Player.class));
//        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_TWO, constants.SIDE, null);
    }

    @Test
    public void shouldRemoveLeftButtonBeforeStartingTheGame() {
        exception.expect(NoNodesFoundException.class);
        Player player1 = mock(Player.class);
        ComputerPlayer player2 = mock(ComputerPlayer.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1);
//        when(playerFactory.createComputerPlayer(anyString(), anyInt(), any(Player.class))).thenReturn(player2);
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        find(onePlayerId);
    }

    @Test
    public void shouldRemoveRightButtonBeforeStartingTheGame() {
        exception.expect(NoNodesFoundException.class);
        Player player1 = mock(Player.class);
        ComputerPlayer player2 = mock(ComputerPlayer.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1);
//        when(playerFactory.createComputerPlayer(anyString(), anyInt(), any(Player.class))).thenReturn(player2);
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        find(twoPlayerId);
    }

    @Test
    public void shouldPlaceGameIntoViewWhenStartingInOnePlayer() {
        Player player1 = mock(Player.class);
        ComputerPlayer player2 = mock(ComputerPlayer.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1);
//        when(playerFactory.createComputerPlayer(anyString(), anyInt(), any(Player.class))).thenReturn(player2);
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        verifyThat(menuId, contains(gameId));
    }

    @Test
    public void shouldPlaceGameIntoViewWhenStartingInTwoPlayer() {
        Player player1 = mock(Player.class);
        ComputerPlayer player2 = mock(ComputerPlayer.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1);
//        when(playerFactory.createComputerPlayer(anyString(), anyInt(), any(Player.class))).thenReturn(player2);
        click(twoPlayer);
        verifyThat(menuId, contains(gameId));
    }

    @Test
    public void shouldNotHaveVisibleHeaderReplayButtonOnMenuPage() {
        exception.expect(NoNodesVisibleException.class);
        find("#replay");
    }

    @Test
    public void shouldNotHaveVisibleHeaderResetButtonOnMenuPage() {
        exception.expect(NoNodesVisibleException.class);
        find("#reset");
    }

    @Test
    public void whenGameOverHeaderReplayButtonShouldBeVisible() {
        when(gameCtrl.gameOver()).thenReturn(true);
        click(twoPlayer);
        click("#cell1");
    }
}
