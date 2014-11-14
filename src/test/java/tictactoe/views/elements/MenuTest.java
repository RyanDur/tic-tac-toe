package tictactoe.views.elements;

import javafx.scene.Parent;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import tictactoe.Game;
import tictactoe.lang.Constants;

import java.util.function.BiConsumer;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class MenuTest extends GuiTest {
    private final String onePlayer = Constants.ONE_PLAYER;
    private final String X = String.valueOf(Constants.GAME_PIECE_ONE);
    private Game game;
    private String O = (String.valueOf(Constants.GAME_PIECE_TWO));
    private String twoPlayer = Constants.TWO_PLAYER;

    @Override
    protected Parent getRootNode() {
        game = mock(Game.class);
        BiConsumer<Integer, Character> game = (size, piece) -> this.game.setup(piece, size);
        Menu menu = new MenuImpl();
        menu.setUpMenu(game);
        return (Parent) menu;
    }

    @Test
    public void shouldBeAbleToSetTheSizeOfABoardToSmallOnePlayer() {
        verifyThat(Constants.LEFT_BUTTON_ID, hasText(Constants.SMALL_BOARD_BUTTON));
        click(Constants.SMALL_BOARD_BUTTON);
        verifyThat(Constants.LEFT_BUTTON_ID, hasText(Constants.ONE_PLAYER));
        click(onePlayer);
        verifyThat(Constants.LEFT_BUTTON_ID, hasText(X));
        click(X);
        verify(game).setup(Constants.GAME_PIECE_TWO, Constants.SMALL_BOARD);
    }

    @Test
    public void shouldBeAbleToSetTheSizeOfABoardToLargeOnePlayer() {
        verifyThat(Constants.RIGHT_BUTTON_ID, hasText(Constants.LARGE_BOARD_BUTTON));
        click(Constants.LARGE_BOARD_BUTTON);
        verifyThat(Constants.RIGHT_BUTTON_ID, hasText(twoPlayer));
        click(onePlayer);
        verifyThat(Constants.RIGHT_BUTTON_ID, hasText(O));
        click(O);
        verify(game).setup(Constants.GAME_PIECE_ONE, Constants.LARGE_BOARD);
    }

    @Test
    public void shouldBeAbleToSetTheSizeOfABoardToSmallTwoPlayer() {
        verifyThat(Constants.LEFT_BUTTON_ID, hasText(Constants.SMALL_BOARD_BUTTON));
        click(Constants.SMALL_BOARD_BUTTON);
        verifyThat(Constants.LEFT_BUTTON_ID, hasText(Constants.ONE_PLAYER));
        click(twoPlayer);
        verify(game).setup(null, Constants.SMALL_BOARD);
    }

    @Test
    public void shouldBeAbleToSetTheSizeOfABoardToLargeTwoPlayer() {
        verifyThat(Constants.RIGHT_BUTTON_ID, hasText(Constants.LARGE_BOARD_BUTTON));
        click(Constants.LARGE_BOARD_BUTTON);
        verifyThat(Constants.RIGHT_BUTTON_ID, hasText(twoPlayer));
        click(twoPlayer);
        verify(game).setup(null, Constants.LARGE_BOARD);
    }
}