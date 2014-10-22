package views;

import factories.PlayerFactory;
import javafx.scene.Parent;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class MenuViewTest extends GuiTest{

    private final String twoPlayer = "#two_player";
    private final String onePlayer = "1 Player";
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
        verifyThat("#one_player", hasText(onePlayer));
    }

    @Test
    public void shouldHaveATwoPlayerButton() {
        verifyThat(twoPlayer, hasText("2 Player"));
    }

    @Test
    public void shouldCreateTwoPlayersIfTwoPlayerIsChosen() {
        click(twoPlayer);
        verify(playerFactory, times(2)).createPlayer(anyString(), anyInt());
    }
}
