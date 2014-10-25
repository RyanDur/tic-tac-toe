package views.elements;

import controllers.GamePlayCtrl;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lang.constants;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.util.function.BiConsumer;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class MenuTest extends GuiTest {
    private GamePlayCtrl game;

    @Override
    protected Parent getRootNode() {
        game = mock(GamePlayCtrl.class);
        BiConsumer<String, String> onePlayer = game::onePlayer;
        EventHandler<MouseEvent> twoPlayer = e -> game.twoPlayer();
        Menu menu = new MenuImpl(onePlayer, twoPlayer);
        return (Parent) menu;
    }

    @Test
    public void shouldBeAbleToSetATwoPlayerGame() {
        click(constants.TWO_PLAYER);
        verify(game).twoPlayer();
    }

    @Test
    public void shouldBeAbleToChooseBetweenXAndOIfAOnePlayerGame() {
        click(constants.ONE_PLAYER);
        verifyThat(constants.LEFT_BUTTON_ID, hasText(constants.GAME_PIECE_ONE));
        verifyThat(constants.RIGHT_BUTTON_ID, hasText(constants.GAME_PIECE_TWO));
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseX() {
        click(constants.ONE_PLAYER);
        click(constants.GAME_PIECE_ONE);
        verify(game).onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseO() {
        click(constants.ONE_PLAYER);
        click(constants.GAME_PIECE_TWO);
        verify(game).onePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE);
    }
}