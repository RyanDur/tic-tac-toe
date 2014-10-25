package views;

import controllers.GamePlayCtrl;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lang.constants;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;
import java.util.function.BiConsumer;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class NavigationViewTest extends GuiTest {
    private NavigationView navigationView;
    private GamePlayCtrl gamePlayCtrl;

    @Override
    protected Parent getRootNode() {
        gamePlayCtrl = mock(GamePlayCtrl.class);
        try {
            navigationView = new NavigationViewImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (Parent) navigationView;
    }

    @Test
    public void shouldBeAbleToSetATwoPlayerGame() {
        EventHandler<MouseEvent> event = e -> gamePlayCtrl.twoPlayer();
        navigationView.setTwoPlayer(event);
        click(constants.TWO_PLAYER);
        verify(gamePlayCtrl).twoPlayer();
    }

    @Test
    public void shouldBeAbleToChooseBetweenXAndOIfAOnePlayerGame() {
        BiConsumer<String, String> event = gamePlayCtrl::onePlayer;
        navigationView.setOnePlayer(event);
        click(constants.ONE_PLAYER);
        verifyThat(constants.LEFT_BUTTON_ID, hasText(constants.GAME_PIECE_ONE));
        verifyThat(constants.RIGHT_BUTTON_ID, hasText(constants.GAME_PIECE_TWO));
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseX() {
        BiConsumer<String, String> event = gamePlayCtrl::onePlayer;
        navigationView.setOnePlayer(event);
        click(constants.ONE_PLAYER);
        click(constants.GAME_PIECE_ONE);
        verify(gamePlayCtrl).onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseO() {
        BiConsumer<String, String> event = gamePlayCtrl::onePlayer;
        navigationView.setOnePlayer(event);
        click(constants.ONE_PLAYER);
        click(constants.GAME_PIECE_TWO);
        verify(gamePlayCtrl).onePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE);
    }
}