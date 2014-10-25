package views.elements;

import javafx.scene.Parent;
import lang.constants;
import models.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.exceptions.NoNodesVisibleException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HeaderTest extends GuiTest {

    private Header header;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Override
    protected Parent getRootNode() {
        header = new HeaderImpl();
        return (Parent) header;
    }

    @Test
    public void shouldBeAbleToSetAMessage() {
        header.setMessage(constants.HAS_WON_MESSAGE);
        verifyThat(constants.MESSAGES_ID, hasText(constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToClearAMessage() {
        header.setMessage(constants.HAS_WON_MESSAGE);
        verifyThat(constants.MESSAGES_ID, hasText(constants.HAS_WON_MESSAGE));
        header.clearMessage();
        verifyThat(constants.MESSAGES_ID, hasText(constants.EMPTY));
    }

    @Test
    public void shouldBeAbleToSetEventOnTheReplayButton() {
        String clicked = "replay";
        header.setReplay(e -> header.setMessage(clicked));
        click(constants.REPLAY_ID);
        verifyThat(constants.MESSAGES_ID, hasText(clicked));
    }

    @Test
    public void shouldBeAbleToSetEventOnTheResetButton() {
        String clicked = "reset";
        header.setReset(e -> header.setMessage(clicked));
        click(constants.RESET_ID);
        verifyThat(constants.MESSAGES_ID, hasText(clicked));
    }

    @Test
    public void shouldBeAbleToMakeTheHeaderButtonsInvisible() {
        exception.expect(NoNodesVisibleException.class);
        header.setButtonsVisibility(false);
        find(constants.RESET_ID);
        find(constants.REPLAY_ID);
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForX() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        header.displayWinner(player);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_ONE + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForO() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        header.displayWinner(player);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForDraw() {
        header.displayWinner(null);
        verifyThat(constants.MESSAGES_ID, hasText(constants.DRAW_MESSAGE));
    }
}