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

public class HeaderViewTest extends GuiTest {

    private HeaderView headerView;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Override
    protected Parent getRootNode() {
        headerView = new HeaderViewImpl();
        return (Parent) headerView;
    }

    @Test
    public void shouldBeAbleToSetAMessage() {
        headerView.setMessage(constants.HAS_WON_MESSAGE);
        verifyThat(constants.MESSAGES_ID, hasText(constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToClearAMessage() {
        headerView.setMessage(constants.HAS_WON_MESSAGE);
        verifyThat(constants.MESSAGES_ID, hasText(constants.HAS_WON_MESSAGE));
        headerView.clearMessage();
        verifyThat(constants.MESSAGES_ID, hasText(constants.EMPTY));
    }

    @Test
    public void shouldBeAbleToSetEventOnTheReplayButton() {
        String clicked = "replay";
        headerView.setReplay(e -> headerView.setMessage(clicked));
        click(constants.REPLAY_ID);
        verifyThat(constants.MESSAGES_ID, hasText(clicked));
    }

    @Test
    public void shouldBeAbleToSetEventOnTheResetButton() {
        String clicked = "reset";
        headerView.setReset(e -> headerView.setMessage(clicked));
        click(constants.RESET_ID);
        verifyThat(constants.MESSAGES_ID, hasText(clicked));
    }

    @Test
    public void shouldBeAbleToMakeTheHeaderButtonsInvisible() {
        exception.expect(NoNodesVisibleException.class);
        headerView.setButtonsVisibility(false);
        find(constants.RESET_ID);
        find(constants.REPLAY_ID);
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForX() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        headerView.displayWinner(player);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_ONE + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForO() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        headerView.displayWinner(player);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForDraw() {
        headerView.displayWinner(null);
        verifyThat(constants.MESSAGES_ID, hasText(constants.DRAW_MESSAGE));
    }
}