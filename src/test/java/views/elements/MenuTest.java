package views.elements;

import tictactoe.Game;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lang.constants;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.util.function.Consumer;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class MenuTest extends GuiTest {
    private Game game;

    @Override
    protected Parent getRootNode() {
        game = mock(Game.class);
        Consumer<String> onePlayer = game::setComputer;
        EventHandler<MouseEvent> twoPlayer = e -> game.setup();
        Menu menu = new MenuImpl(onePlayer, twoPlayer);
        return (Parent) menu;
    }

    @Test
    public void shouldBeAbleToSetATwoPlayerGame() {
        click(constants.TWO_PLAYER);
        verify(game).setup();
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
        verify(game).setComputer(constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseO() {
        click(constants.ONE_PLAYER);
        click(constants.GAME_PIECE_TWO);
        verify(game).setComputer(constants.GAME_PIECE_ONE);
    }
}