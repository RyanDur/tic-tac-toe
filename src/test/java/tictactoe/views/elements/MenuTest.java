package tictactoe.views.elements;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import tictactoe.Game;
import tictactoe.lang.Constants;

import java.util.function.Consumer;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class MenuTest extends GuiTest {
    private Game game;
    private Menu menu;

    @Override
    protected Parent getRootNode() {
        game = mock(Game.class);
        Consumer<Character> onePlayer = e -> game.setup(null, Constants.SIDE);
        EventHandler<MouseEvent> twoPlayer = e -> game.setup(null, Constants.SIDE);
        menu = new MenuImpl();
        menu.setOnePlayer(onePlayer);
        menu.setTwoPlayer(twoPlayer);
        return (Parent) menu;
    }

    @Test
    public void shouldBeAbleToSetATwoPlayerGame() {
        click(Constants.TWO_PLAYER);
        verify(game).setup(null, Constants.SIDE);
    }

    @Test
    public void shouldBeAbleToChooseBetweenXAndOIfAOnePlayerGame() {
        click(Constants.ONE_PLAYER);
        verifyThat(Constants.LEFT_BUTTON_ID, hasText(String.valueOf(Constants.GAME_PIECE_ONE)));
        verifyThat(Constants.RIGHT_BUTTON_ID, hasText(String.valueOf(String.valueOf(Constants.GAME_PIECE_TWO))));
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseX() {
        click(Constants.ONE_PLAYER);
        click(String.valueOf(Constants.GAME_PIECE_ONE));
        verify(game).setup(null, Constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseO() {
        click(Constants.ONE_PLAYER);
        click(String.valueOf(Constants.GAME_PIECE_TWO));
        verify(game).setup(null, Constants.GAME_PIECE_ONE);
    }

    @Test
    public void shouldBeAbleToResetTheMenu() {
        click(Constants.ONE_PLAYER);
        click(String.valueOf(Constants.GAME_PIECE_TWO));
        menu.reset();
        verifyThat(Constants.LEFT_BUTTON_ID, hasText(Constants.ONE_PLAYER));
        verifyThat(Constants.RIGHT_BUTTON_ID, hasText(Constants.TWO_PLAYER));
    }
}