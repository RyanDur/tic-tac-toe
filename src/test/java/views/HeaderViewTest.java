package views;

import controllers.GamePlayCtrl;
import javafx.scene.Parent;
import lang.constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.exceptions.NoNodesVisibleException;

import java.io.IOException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;

public class HeaderViewTest extends GuiTest {

    private HeaderView headerView;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Override
    protected Parent getRootNode() {
        try {
            headerView = new HeaderViewImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}