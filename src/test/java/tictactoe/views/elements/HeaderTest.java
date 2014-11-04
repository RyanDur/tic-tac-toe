package tictactoe.views.elements;

import javafx.scene.Parent;
import tictactoe.lang.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.exceptions.NoNodesVisibleException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;

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
        header.setMessage(Constants.HAS_WON_MESSAGE);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToClearAMessage() {
        header.setMessage(Constants.HAS_WON_MESSAGE);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.HAS_WON_MESSAGE));
        header.clearMessage();
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.EMPTY));
    }

    @Test
    public void shouldBeAbleToSetEventOnTheReplayButton() {
        String clicked = "replay";
        header.setReplay(e -> header.setMessage(clicked));
        click(Constants.REPLAY_ID);
        verifyThat(Constants.MESSAGES_ID, hasText(clicked));
    }

    @Test
    public void shouldBeAbleToSetEventOnTheResetButton() {
        String clicked = "reset";
        header.setReset(e -> header.setMessage(clicked));
        click(Constants.RESET_ID);
        verifyThat(Constants.MESSAGES_ID, hasText(clicked));
    }

    @Test
    public void shouldBeAbleToMakeTheHeaderButtonsInvisible() {
        exception.expect(NoNodesVisibleException.class);
        header.setButtonsVisibility(false);
        find(Constants.RESET_ID);
        find(Constants.REPLAY_ID);
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForX() {
        header.displayWinner(Constants.GAME_PIECE_ONE);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.GAME_PIECE_ONE + Constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForO() {
        header.displayWinner(Constants.GAME_PIECE_TWO);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.GAME_PIECE_TWO + Constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldBeAbleToDisplayTheWinnerForDraw() {
        header.displayWinner(null);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.DRAW_MESSAGE));
    }
}