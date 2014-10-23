package views;

import controllers.GameCtrl;
import factories.GameViewFactory;
import factories.PlayerFactory;
import javafx.scene.Parent;
import lang.constants;
import models.ComputerPlayer;
import models.Player;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class MenuViewTest extends GuiTest{

    private final String twoPlayerId = "#two_player";
    private final String onePlayer = "1 Player";
    private final String onePlayerId = "#one_player";
    private PlayerFactory playerFactory;
    private String twoPlayer = "2 Player";
    private GameViewFactory gameViewFactory;
    private GameCtrl gameCtrl;

    @Override
    protected Parent getRootNode() {
        gameCtrl = mock(GameCtrl.class);
        playerFactory = mock(PlayerFactory.class);
        gameViewFactory = mock(GameViewFactory.class);
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
        verify(playerFactory).createComputerPlayer(anyString(), anyInt(), any(Player.class));
        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_ONE, constants.SIDE, null);
    }

    @Test
    public void shouldMakeTheComputerPlayerOWhenXIsChosen() {
        click(onePlayer);
        click(constants.GAME_PIECE_ONE);
        Player human = mock(Player.class);
        when(playerFactory.createPlayer(anyString(),anyInt())).thenReturn(human);

        verify(playerFactory).createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        verify(playerFactory).createComputerPlayer(anyString(), anyInt(), any(Player.class));
        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_TWO, constants.SIDE, null);
    }

    @Test
    public void shouldCreateGameViewWhenTwoPlayersIsChosen() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1, player2);
        click(twoPlayer);
        verify(gameViewFactory).createGameView(gameCtrl, player1, player2);
    }

    @Test
    public void shouldCreateGameViewWhenXIsChosen() {
        Player player1 = mock(Player.class);
        ComputerPlayer player2 = mock(ComputerPlayer.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1);
        when(playerFactory.createComputerPlayer(anyString(), anyInt(), any(Player.class))).thenReturn(player2);
        click(onePlayer);
        click(constants.GAME_PIECE_ONE);
        verify(gameViewFactory).createGameView(gameCtrl, player1, player2);
    }

    @Test
    public void shouldCreateGameViewWhenOIsChosen() {
        Player player1 = mock(Player.class);
        ComputerPlayer player2 = mock(ComputerPlayer.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1);
        when(playerFactory.createComputerPlayer(anyString(), anyInt(), any(Player.class))).thenReturn(player2);
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        verify(gameViewFactory).createGameView(gameCtrl, player1, player2);
    }
}
