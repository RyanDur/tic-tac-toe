package views;

import controllers.GamePlayCtrl;
import javafx.scene.Parent;
import lang.constants;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import java.io.IOException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


public class NavigationViewTest extends GuiTest {
    private NavigationView navigationView;
    private GamePlayCtrl game;

    @Override
    protected Parent getRootNode() {
        game = mock(GamePlayCtrl.class);
        try {
            navigationView = new NavigationViewImpl();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (Parent) navigationView;
    }

    @Test
    public void shouldBeAbleToSetATwoPlayerGame() {
        navigationView.setTwoPlayer(e -> game.twoPlayer());
        click(constants.TWO_PLAYER);
        verify(game).twoPlayer();
    }

    @Test
    public void shouldBeAbleToChooseBetweenXAndOIfAOnePlayerGame() {
        navigationView.setOnePlayer(game::onePlayer);
        click(constants.ONE_PLAYER);
        verifyThat(constants.LEFT_BUTTON_ID, hasText(constants.GAME_PIECE_ONE));
        verifyThat(constants.RIGHT_BUTTON_ID, hasText(constants.GAME_PIECE_TWO));
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseX() {
        navigationView.setOnePlayer(game::onePlayer);
        click(constants.ONE_PLAYER);
        click(constants.GAME_PIECE_ONE);
        verify(game).onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldBeAbleToAllowAPlayerToChooseO() {
        navigationView.setOnePlayer(game::onePlayer);
        click(constants.ONE_PLAYER);
        click(constants.GAME_PIECE_TWO);
        verify(game).onePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE);
    }
}