package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import javafx.scene.Parent;
import lang.constants;
import models.Player;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.impl.ContainsNodesMatcher.contains;
import static org.mockito.Mockito.*;

public class BoardTest extends GuiTest {

    private GameCtrl mockGameCtrl;
    private Player[] board;
    private Player mockPlayer1;
    private Player mockPlayer2;

    @Override
    protected Parent getRootNode() {
        mockPlayer1 = mock(Player.class);
        mockPlayer2 = mock(Player.class);
        mockGameCtrl = mock(GameCtrl.class);
        board = new Player[constants.SIDE * constants.SIDE];
        when(mockGameCtrl.getBoard()).thenReturn(board);
        return new Board(mockGameCtrl, mockPlayer1, mockPlayer2);
    }

    @Test
    public void shouldHaveABoardThatReflectsTheBoardForTheGame() {
        verifyThat("#board", contains(board.length, ".label"));
    }

    @Test
    public void shouldBeAbleToChooseAPlaceOnTheBoard() throws OutOfTurnException, NotVacantException {
        int x = 1;
        int y = 1;
        String piece = "X";
        String id = "#" + x + "," + y;
        board[calc(x, y)] = mockPlayer1;
        when(mockPlayer1.getPiece()).thenReturn(piece);
        when(mockGameCtrl.getBoard()).thenReturn(board);

        click(id);
        verify(mockGameCtrl).setPiece(any(Player.class));
    }

    private int calc(int x, int y) {
        return (x * constants.SIDE) + y;
    }
}
