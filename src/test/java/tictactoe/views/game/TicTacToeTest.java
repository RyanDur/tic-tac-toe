package tictactoe.views.game;

import javafx.scene.Parent;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.exceptions.NoNodesFoundException;
import tictactoe.Game;
import tictactoe.exceptions.InvalidMoveException;
import tictactoe.lang.Constants;
import tictactoe.views.elements.*;

import java.util.Arrays;

import static org.loadui.testfx.Assertions.assertNodeExists;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.*;

public class TicTacToeTest extends GuiTest {
    private final String onePlayer = "1 Player";
    private final String menuId = "#menu";
    private final String gameId = "#tictactoe.game";
    private String twoPlayer = "2 Player";
    private Game game;
    private Character[] board = new Character[Constants.SMALL_BOARD * Constants.SMALL_BOARD];
    private String center = "#cell4";
    private String reset = "Reset";

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private String replay = "Replay";
    private String centerLeft = "#cell3";
    private String pieceOne = String.valueOf(Constants.GAME_PIECE_ONE);
    private String pieceTwo = String.valueOf(Constants.GAME_PIECE_TWO);


    @Override
    protected Parent getRootNode() {
        game = mock(Game.class);
        when(game.getBoard()).thenReturn(board);
        Header header = new HeaderImpl();
        Menu menu = new MenuImpl();
        Board board = new BoardImpl();
        return new TicTacToeImpl(game, header, menu, board);
    }

    @Test
    public void shouldDisplayAMenuWhenStarted() {
        assertNodeExists(menuId);
    }

    @Test
    public void shouldDisplayTheGameWhenTwoPlayerModeIsChosen() {
        click(twoPlayer);
        assertNodeExists(gameId);
    }

    @Test
    public void shouldDisplayBoardWhenPlayerChoosesXInOnePlayerMode() {
        click(onePlayer);
        click(pieceOne);
        assertNodeExists(gameId);
    }

    @Test
    public void shouldCreateAHumanAndComputerWhenPlayerChoosesXInOnePlayerMode() {
        click(onePlayer);
        click(pieceOne);
        verify(game).setup(null, Constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldDisplayBoardWhenPlayerChoosesOInOnePlayerMode() {
        click(onePlayer);
        click(pieceTwo);
        assertNodeExists(gameId);
    }

    @Test
    public void shouldCreateAComputerAndHumanWhenPlayerChoosesXInOnePlayerMode() {
        click(onePlayer);
        click(pieceTwo);
        verify(game).setup(null, Constants.GAME_PIECE_ONE);
    }

    @Test
    public void shouldRemoveMenuWhenShowingBoardInTwoPlayerMode() {
        exception.expect(NoNodesFoundException.class);
        click(twoPlayer);
        find(menuId);
    }

    @Test
    public void shouldRemoveMenuWhenPlayerChoosesXShowingBoardInOnePlayerMode() {
        exception.expect(NoNodesFoundException.class);
        click(onePlayer);
        click(pieceOne);
        find(menuId);
    }

    @Test
    public void shouldRemoveMenuWhenPlayerChoosesOShowingBoardInOnePlayerMode() {
        exception.expect(NoNodesFoundException.class);
        click(onePlayer);
        click(pieceTwo);
        find(menuId);
    }

    @Test
    public void shouldSetThePlayerWithTheCoordinatesWhenPlayerChoosesInOnePlayerMode() throws InvalidMoveException {
        click(onePlayer);
        click(pieceTwo);
        click(center);
        verify(game).set(Arrays.asList(1, 1));
    }

    @Test
    public void shouldCheckForGameOverWhenPlayerChoosesInOnePlayerMode() {
        click(onePlayer);
        click(pieceTwo);
        click(center);
        verify(game, times(2)).isOver();
    }

    @Test
    public void shouldRevealReplayButtonWhenGameOverInOnePlayerMode() {
        when(game.isOver()).thenReturn(true);
        click(onePlayer);
        click(pieceOne);
        click(center);
        verifyThat(Constants.REPLAY_ID, hasText(replay));
    }

    @Test
    public void shouldRevealResetButtonWhenGameOverInOnePlayerMode() {
        when(game.isOver()).thenReturn(true);
        click(onePlayer);
        click(pieceOne);
        click(center);
        verifyThat(Constants.RESET_ID, hasText(reset));
    }

    @Test
    public void shouldRevealReplayButtonWhenGameOverInTwoPlayerMode() {
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(Constants.REPLAY_ID, hasText(replay));
    }

    @Test
    public void shouldRevealResetButtonWhenGameOverInTwoPlayerMode() {
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(Constants.RESET_ID, hasText(reset));
    }

    @Test
    public void shouldDisplayWinnerWhenGameIsOverForXInOnePlayerMode() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_ONE);
        when(game.isOver()).thenReturn(true);
        click(onePlayer);
        click(pieceOne);
        click(center);
        verifyThat(Constants.MESSAGES_ID, hasText(pieceOne + Constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldDisplayWinnerWhenGameIsOverForOInOnePlayerMode() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_TWO);
        when(game.isOver()).thenReturn(true);
        click(onePlayer);
        click(pieceTwo);
        click(center);
        verifyThat(Constants.MESSAGES_ID, hasText(pieceTwo + Constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldDisplayWinnerWhenGameIsOverForXInTwoPlayerMode() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_ONE);
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(Constants.MESSAGES_ID, hasText(pieceOne + Constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldDisplayWinnerWhenGameIsOverForOInTwoPlayerMode() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_TWO);
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(Constants.MESSAGES_ID, hasText(pieceTwo + Constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldDisplayTheMenuWhenResetIsClicked() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_ONE);
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        click(reset);
        assertNodeExists(menuId);
    }

    @Test
    public void shouldRemoveMessageIfAnyWhenPlayerClicksOnEmptySpaceIsClicked() throws InvalidMoveException {
        doThrow(new InvalidMoveException()).when(game).set(Arrays.asList(1,1));
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_ONE);
        click(twoPlayer);
        click(center);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.NOT_VACANT_MESSAGE));
        click(centerLeft);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.EMPTY));
    }

    @Test
    public void shouldRemoveMessageWhenResetIsClicked() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_TWO);
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(Constants.MESSAGES_ID, hasText(pieceTwo + Constants.HAS_WON_MESSAGE));
        click(reset);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.EMPTY));
    }

    @Test
    public void shouldRemoveTheGameWhenResetIsClicked() {
        exception.expect(NoNodesFoundException.class);
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_ONE);
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        click(reset);
        find(gameId);
    }

    @Test
    public void shouldResetTheGameWhenReplayIsChosen() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_ONE);
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        click(replay);
        verify(game, times(2)).setup(any(null), anyChar());
    }

    @Test
    public void shouldRemoveMessageWhenReplayIsChosen() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_ONE);
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(Constants.MESSAGES_ID, hasText(pieceTwo + Constants.HAS_WON_MESSAGE));
        click(replay);
        verifyThat(Constants.MESSAGES_ID, hasText(Constants.EMPTY));
    }

    @Test
    public void shouldNotRemoveWinMessageWhenPlayerHasWonButStillTryingToClickOnASpace() {
        when(game.getWinner()).thenReturn(Constants.GAME_PIECE_TWO);
        when(game.isOver()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(Constants.MESSAGES_ID, hasText(pieceTwo + Constants.HAS_WON_MESSAGE));
        click(centerLeft);
        verifyThat(Constants.MESSAGES_ID, hasText(pieceTwo + Constants.HAS_WON_MESSAGE));
    }
}
