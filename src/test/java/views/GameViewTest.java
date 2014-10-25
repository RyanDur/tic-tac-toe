package views;

import controllers.GameCtrl;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lang.constants;
import models.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;

import java.io.IOException;
import java.util.function.Function;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameViewTest extends GuiTest {
    private Player player;
    private Player[] board;
    private GameCtrl gameCtrl;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private GameView gameView;
    private Function<MouseEvent, Player[]> play;

    @Override
    protected Parent getRootNode() {
        board = new Player[constants.SIDE * constants.SIDE];
        player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        gameCtrl = mock(GameCtrl.class);
        when(gameCtrl.getBoard()).thenReturn(board);
        play = mockPlay();
        try {
            gameView = new GameViewImpl();
            return (Parent) gameView;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldBeAbleToChooseAPlaceOnTheBoard() {
        gameView.setup(board);
        gameView.setPlay(play);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(gameCtrl.getBoard()).thenReturn(board);
        for (int i = 0; i < board.length; i++) {
            String id = "#cell" + i;
            board[i] = player;
            click(id);
            verifyThat(id, hasText(constants.GAME_PIECE_ONE));
        }
    }

    @Test
    public void shouldBeAbleToSetupTheBoard() {
        int index = 2;
        board[index] = player;
        String id = "#cell" + index;
        gameView.setup(board);
        verifyThat(id, hasText(constants.GAME_PIECE_ONE));
    }

    @Test
    public void shouldBeAbleToClearTheBoard() throws InterruptedException {
        int index = 2;
        Player[] board1 = new Player[constants.SIDE * constants.SIDE];
        board[index] = player;
        String id = "#cell" + index;
        gameView.setup(board);
        verifyThat(id, hasText(constants.GAME_PIECE_ONE));
        gameView.setup(board1);
        verifyThat(id, hasText(constants.EMPTY));
    }

    @Test
    public void shouldBeAbleToSetThePlayAction() {
        int index = 2;
        gameView.setup(board);
        gameView.setPlay(play);
        Player[] board1 = new Player[constants.SIDE * constants.SIDE];
        board1[index] = player;
        when(gameCtrl.getBoard()).thenReturn(board1);
        String id = "#cell" + index;
        click(id);
        verifyThat(id, hasText(constants.GAME_PIECE_ONE));
    }

    private Function<MouseEvent, Player[]> mockPlay() {
        return e -> gameCtrl.getBoard();
    }
}
