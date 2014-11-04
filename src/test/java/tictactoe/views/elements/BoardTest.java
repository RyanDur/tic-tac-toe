package tictactoe.views.elements;

import tictactoe.lang.Constants;
import tictactoe.game.Game;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
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
    private Character player;
    private Character[] board;
    private Game gameCtrl;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Board boardView;
    private Function<MouseEvent, Character[]> play;

    @Override
    protected Parent getRootNode() {
        this.board = new Character[Constants.SIDE * Constants.SIDE];
        player = Constants.GAME_PIECE_ONE;
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
            verifyThat(id, hasText(String.valueOf(Constants.GAME_PIECE_ONE)));
        }
    }

    @Test
    public void shouldBeAbleToSetupTheBoard() {
        int index = 2;
        board[index] = player;
        String id = cell + index;
        click(id);
        verifyThat(id, hasText(String.valueOf(Constants.GAME_PIECE_ONE)));
    }

    @Test
    public void shouldBeAbleToClearTheBoard() throws InterruptedException {
        int index = 2;
        Character[] board1 = new Character[Constants.SIDE * Constants.SIDE];
        board[index] = player;
        String id = cell + index;
        click(id);
        verifyThat(id, hasText(String.valueOf(Constants.GAME_PIECE_ONE)));
        when(gameCtrl.getBoard()).thenReturn(board1);
        click(id);
        verifyThat(id, hasText(Constants.EMPTY));
    }

    private Function<MouseEvent, Character[]> mockPlay() {
        return e -> gameCtrl.getBoard();
    }
}