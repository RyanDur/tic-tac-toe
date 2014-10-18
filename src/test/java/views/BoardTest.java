package views;

import controllers.GameCtrl;
import javafx.scene.Parent;
import lang.constants;
import models.Player;
import org.junit.Test;
import org.loadui.testfx.GuiTest;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.impl.ContainsNodesMatcher.contains;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardTest extends GuiTest {

    private GameCtrl mockGameCtrl;

    @Override
    protected Parent getRootNode() {
        mockGameCtrl = mock(GameCtrl.class);
        return new Board(mockGameCtrl);
    }

    @Test
    public void shouldHaveABoardThatReflectsTheBoardForTheGame() {
        Player[] board = new Player[constants.SIDE * constants.SIDE];
        when(mockGameCtrl.getBoard()).thenReturn(board);
        verifyThat("#board", contains(board.length, ".label"));
    }
}
