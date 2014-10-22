package views;

import factories.PlayerFactory;
import javafx.scene.Parent;
import lang.constants;
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

    @Override
    protected Parent getRootNode() {
        playerFactory = mock(PlayerFactory.class);
        try {
            return new MenuView(playerFactory);
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
        verifyThat(twoPlayerId, hasText("2 Player"));
    }

    @Test
    public void shouldCreateTwoPlayersIfTwoPlayerIsChosen() {
        click(twoPlayerId);
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
}
