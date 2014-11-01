package views.elements;

import tictactoe.Game;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import lang.constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;

import java.util.function.Function;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.*;

public class BoardTest extends GuiTest {
    private final String cell = "#cell";
    private String player;
    private String[] board;
    private Game gameCtrl;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Board boardView;
    private Function<MouseEvent, String[]> play;

    @Override
    protected Parent getRootNode() {
        this.board = new String[constants.SIDE * constants.SIDE];
        player = constants.GAME_PIECE_ONE;
        gameCtrl = mock(Game.class);
        when(gameCtrl.getBoard()).thenReturn(this.board);
        play = mockPlay();
        boardView = new BoardImpl();
        boardView.setPlay(play);
        boardView.setBoard(board);
        return (Parent) boardView;
    }

    @Test
    public void shouldBeAbleToSetThePlayAction() {
        when(gameCtrl.getBoard()).thenReturn(board);
        click(cell + 1);
        verify(gameCtrl).getBoard();
    }

    @Test
    public void shouldBeAbleToChooseAPlaceOnTheBoard() {
        when(gameCtrl.getBoard()).thenReturn(board);
        boardView.setPlay(play);
        for (int i = 0; i < board.length; i++) {
            String id = cell + i;
            board[i] = player;
            click(id);
            verifyThat(id, hasText(constants.GAME_PIECE_ONE));
        }
    }

    @Test
    public void shouldBeAbleToSetupTheBoard() {
        int index = 2;
        board[index] = player;
        String id = cell + index;
        click(id);
        verifyThat(id, hasText(constants.GAME_PIECE_ONE));
    }

    @Test
    public void shouldBeAbleToClearTheBoard() throws InterruptedException {
        int index = 2;
        String[] board1 = new String[constants.SIDE * constants.SIDE];
        board[index] = player;
        String id = cell + index;
        click(id);
        verifyThat(id, hasText(constants.GAME_PIECE_ONE));
        when(gameCtrl.getBoard()).thenReturn(board1);
        click(id);
        verifyThat(id, hasText(constants.EMPTY));
    }

    private Function<MouseEvent, String[]> mockPlay() {
        return e -> gameCtrl.getBoard();
    }
}
