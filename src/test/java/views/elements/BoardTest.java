package views.elements;

import controllers.GameCtrl;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lang.constants;
import models.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;

import java.util.function.Function;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardTest extends GuiTest {
    private Player player;
    private Player[] board;
    private GameCtrl gameCtrl;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Override
    protected Parent getRootNode() {
        this.board = new Player[constants.SIDE * constants.SIDE];
        player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        gameCtrl = mock(GameCtrl.class);
        when(gameCtrl.getBoard()).thenReturn(this.board);
        Function<MouseEvent, Player[]> play = mockPlay();
        Board board = new BoardImpl(this.board, play);
        return (Parent) board;
    }

    @Test
    public void shouldBeAbleToChooseAPlaceOnTheBoard() {
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
        click(id);
        verifyThat(id, hasText(constants.GAME_PIECE_ONE));
    }

    @Test
    public void shouldBeAbleToClearTheBoard() throws InterruptedException {
        int index = 2;
        Player[] board1 = new Player[constants.SIDE * constants.SIDE];
        board[index] = player;
        String id = "#cell" + index;
        click(id);
        verifyThat(id, hasText(constants.GAME_PIECE_ONE));
        when(gameCtrl.getBoard()).thenReturn(board1);
        click(id);
        verifyThat(id, hasText(constants.EMPTY));
    }

    private Function<MouseEvent, Player[]> mockPlay() {
        return e -> gameCtrl.getBoard();
    }
}
