package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import factories.PlayerFactory;
import javafx.scene.Parent;
import lang.constants;
import models.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.exceptions.NoNodesVisibleException;
import org.mockito.InOrder;

import java.io.IOException;

import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.loadui.testfx.controls.impl.ContainsNodesMatcher.contains;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class GameViewTest extends GuiTest {

    private Player player1;
    private Player player2;
    private GameCtrl mockGameCtrl;
    private Player[] board;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private PlayerFactory mockPlayerFactory;

    @Override
    protected Parent getRootNode() {
        board = new Player[constants.SIDE * constants.SIDE];
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        mockGameCtrl = mock(GameCtrl.class);
        when(mockGameCtrl.getBoard()).thenReturn(new Player[constants.SIDE * constants.SIDE]);
        mockPlayerFactory = mock(PlayerFactory.class);
        when(mockPlayerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1, player2);
        try {
            return new GameView(mockGameCtrl, mockPlayerFactory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Test
    public void shouldBeAbleToChooseAPlaceOnTheBoard() throws OutOfTurnException, NotVacantException {
        click("#" + constants.PLAY_ID);
        int x = 1;
        int y = 1;
        String piece = "X";
        String id = "#cell" + calc(x, y);
        board[calc(x, y)] = player1;
        when(player1.getPiece()).thenReturn(piece);
        when(mockGameCtrl.getBoard()).thenReturn(board);

        click(id);
        verify(mockGameCtrl).setPiece(player1);
    }

    @Test
    public void shouldMakeSureToAlternateBetweenPlayers() throws OutOfTurnException, NotVacantException {
        click("#" + constants.PLAY_ID);
        int x1 = 1;
        int y1 = 1;
        int x2 = 2;
        int y2 = 2;
        String piece1 = "X";
        String piece2 = "O";
        String id1 = "#cell" + calc(x1, y1);
        String id2 = "#cell" + calc(x2, y2);
        board[calc(x1, y1)] = player1;
        when(player1.getPiece()).thenReturn(piece1);
        when(player2.getPiece()).thenReturn(piece2);
        when(mockGameCtrl.getBoard()).thenReturn(board);

        click(id1);
        verify(mockGameCtrl).setPiece(player1);
        board[calc(x2, y2)] = player2;
        click(id2);
        verify(mockGameCtrl).setPiece(player2);
    }

    @Test
    public void shouldMakeSureTheSpacesAreNotChangedByClickIfGameOver() throws OutOfTurnException, NotVacantException {
        click("#" + constants.PLAY_ID);
        int x1 = 1;
        int y1 = 1;
        int x2 = 2;
        int y2 = 2;
        String piece1 = "X";
        String piece2 = "O";
        String id1 = "#cell" + calc(x1, y1);
        String id2 = "#cell" + calc(x2, y2);
        board[calc(x1, y1)] = player1;
        when(player1.getPiece()).thenReturn(piece1);
        when(player2.getPiece()).thenReturn(piece2);
        when(mockGameCtrl.getBoard()).thenReturn(board);

        click(id1);
        when(mockGameCtrl.gameOver()).thenReturn(true);
        verify(mockGameCtrl).setPiece(player1);
        board[calc(x2, y2)] = player2;
        click(id2);
        verify(mockGameCtrl, never()).setPiece(player2);
    }

    @Test
    public void shouldNotHaveAVisibleBoardOnOpen() {
        exception.expect(NoNodesVisibleException.class);
        find("#board");
    }

    @Test
    public void shouldBeGivenABoardWhenPushThePlayButton() {
        click("#" + constants.PLAY_ID);
        verifyThat("#game", contains("#board"));
    }

    @Test
    public void shouldSetupTheGameBeforeGettingTheBoard() {
        click("#" + constants.PLAY_ID);
        InOrder order = inOrder(mockGameCtrl);
        order.verify(mockGameCtrl).setup();
        order.verify(mockGameCtrl).getBoard();
    }

    @Test
    public void shouldMakeThePlayButtonInvisibleAfterClicked() {
        exception.expect(NoNodesVisibleException.class);
        click("#" + constants.PLAY_ID);
        click("#" + constants.PLAY_ID);
    }

    @Test
    public void shouldGetAppropriateMessageIfXWins() {
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(mockGameCtrl.getWinner()).thenReturn(player1);
        click("#" + constants.PLAY_ID);
        when(mockGameCtrl.gameOver()).thenReturn(false, true);
        click("#cell" + 3);
        verifyThat("#" + constants.MESSAGES_ID, hasText(constants.GAME_PIECE_ONE + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldGetAppropriateMessageIfOWins() {
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(mockGameCtrl.getWinner()).thenReturn(player2);
        click("#" + constants.PLAY_ID);
        click("#cell" + 3);
        when(mockGameCtrl.gameOver()).thenReturn(false, true);
        click("#cell" + 4);
        verifyThat("#" + constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldMakePlayButtonVisibleWhenGameOver() {
        click("#" + constants.PLAY_ID);
        when(mockGameCtrl.gameOver()).thenReturn(false, true);
        click("#cell" + 3);
        verifyThat("#" + constants.PLAY_ID, hasText("Play"));
    }

    @Test
    public void shouldRemoveMessageForNewGame() {
        click("#" + constants.PLAY_ID);
        when(mockGameCtrl.gameOver()).thenReturn(false, true);
        click("#cell" + 3);
        when(mockGameCtrl.gameOver()).thenReturn(false);
        click("#" + constants.PLAY_ID);
        verifyThat("#messages", hasText(""));
    }

    @Test
    public void shouldDisplayDrawIfNoWinner() {
        click("#" + constants.PLAY_ID);
        when(mockGameCtrl.gameOver()).thenReturn(false, true);
        when(mockGameCtrl.getWinner()).thenReturn(null);
        click("#cell" + 3);
        verifyThat("#" + constants.MESSAGES_ID, hasText(constants.DRAW_MESSAGE));
    }

    @Test
    public void shouldResetBoardWhenClickPlayBoardAfterGameIsOver() {
        String piece1 = "X";
        int x = 1;
        int y = 0;
        String cell = "#cell" + calc(x, y);
        when(player1.getPiece()).thenReturn(piece1);
        board[calc(x, y)] = player1;
        click("#" + constants.PLAY_ID);
        when(mockGameCtrl.gameOver()).thenReturn(false, true);
        when(mockGameCtrl.getBoard()).thenReturn(board);
        click(cell);
        when(mockGameCtrl.getBoard()).thenReturn(new Player[constants.SIDE * constants.SIDE]);
        click("#" + constants.PLAY_ID);
        verifyThat(cell, hasText(""));
    }

    @Test
    public void shouldShowAppropriateMessageIfAPlayerTriesToTakeATakenSpace() throws OutOfTurnException, NotVacantException {
        doThrow(new NotVacantException()).when(mockGameCtrl).setPiece(any(Player.class));
        click("#" + constants.PLAY_ID);
        when(mockGameCtrl.gameOver()).thenReturn(false);
        click("#cell" + 3);
        verifyThat("#" + constants.MESSAGES_ID, hasText(constants.NOT_VACANT_MESSAGE));
    }

    @Test
    public void shouldRemoveAnyMessageWhenAPlayerChoosesASpace() throws OutOfTurnException, NotVacantException {
        click("#" + constants.PLAY_ID);
        when(mockGameCtrl.gameOver()).thenReturn(false);
        click("#cell" + 3);
        doThrow(new NotVacantException()).doNothing().when(mockGameCtrl).setPiece(any(Player.class));
        click("#cell" + 3);
        verifyThat("#" + constants.MESSAGES_ID, hasText(constants.NOT_VACANT_MESSAGE));
        click("#cell" + 4);
        verifyThat("#" + constants.MESSAGES_ID, hasText(""));
    }

    @Test
    public void shouldNotLetTheWrongPlayerChooseASpaceWhenItIsNotThereTurn() throws OutOfTurnException, NotVacantException {
        click("#" + constants.PLAY_ID);
        when(mockPlayerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1, player2);
        when(mockGameCtrl.gameOver()).thenReturn(false);
        click("#cell" + 3);
        doThrow(new NotVacantException()).doNothing().when(mockGameCtrl).setPiece(player2);
        click("#cell" + 3);
        verifyThat("#" + constants.MESSAGES_ID, hasText(constants.NOT_VACANT_MESSAGE));
        doThrow(new OutOfTurnException()).when(mockGameCtrl).setPiece(player1);
        click("#cell" + 4);
        verifyThat("#" + constants.MESSAGES_ID, hasText(""));
    }

    private int calc(int x, int y) {
        return (x * constants.SIDE) + y;
    }
}
