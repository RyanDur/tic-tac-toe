package tictactoe.views.elements;

import javafx.scene.Parent;
import org.junit.Test;
import org.loadui.testfx.GuiTest;
import tictactoe.Game;
import tictactoe.lang.Constants;

import java.util.function.BiConsumer;

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
        menu.setup(game);
        return (Parent) menu;
    }

    @Test
    public void shouldBeAbleToSetTheSizeOfABoardToSmallOnePlayer() {
        click(Constants.SMALL_BOARD_BUTTON);
        click(onePlayer);
        click(X);
        verify(game).setup(Constants.GAME_PIECE_TWO, Constants.SMALL_BOARD);
    }

    @Test
    public void shouldBeAbleToSetTheSizeOfABoardToLargeOnePlayer() {
        click(Constants.LARGE_BOARD_BUTTON);
        click(onePlayer);
        click(O);
        verify(game).setup(Constants.GAME_PIECE_ONE, Constants.LARGE_BOARD);
    }

    @Test
    public void shouldBeAbleToSetTheSizeOfABoardToSmallTwoPlayer() {
        click(Constants.SMALL_BOARD_BUTTON);
        click(twoPlayer);
        verify(game).setup(null, Constants.SMALL_BOARD);
    }

    @Test
    public void shouldBeAbleToSetTheSizeOfABoardToLargeTwoPlayer() {
        click(Constants.LARGE_BOARD_BUTTON);
        click(twoPlayer);
        verify(game).setup(null, Constants.LARGE_BOARD);
    }
}