package views.game;

import controllers.GamePlayCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.ViewFactory;
import factories.ViewFactoryImpl;
import javafx.scene.Parent;
import lang.constants;
import models.Player;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.loadui.testfx.GuiTest;
import org.loadui.testfx.exceptions.NoNodesFoundException;
import views.elements.Header;
import views.elements.HeaderImpl;

import static org.loadui.testfx.Assertions.assertNodeExists;
import static org.loadui.testfx.Assertions.verifyThat;
import static org.loadui.testfx.controls.Commons.hasText;
import static org.mockito.Mockito.*;

public class TicTacToeTest extends GuiTest {
    private final String onePlayer = "1 Player";
    private final String menuId = "#menu";
    private final String gameId = "#game";
    private String twoPlayer = "2 Player";
    private GamePlayCtrl game;
    private Player[] board = new Player[constants.SIDE * constants.SIDE];
    private String center = "#cell4";
    private String reset = "Reset";

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private String replay = "Replay";
    private String centerLeft = "#cell3";


    @Override
    protected Parent getRootNode() {
        game = mock(GamePlayCtrl.class);
        when(game.getBoard()).thenReturn(board);
        ViewFactory viewFactory = new ViewFactoryImpl();
        Header header = new HeaderImpl();
        return new TicTacToeImpl(game, viewFactory, header);
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
    public void shouldCreateTwoPlayersWhenTwoPlayerModeIsChosen() {
        click(twoPlayer);
        verify(game).twoPlayer();
    }

    @Test
    public void shouldDisplayBoardWhenPlayerChoosesXInOnePlayerMode() {
        click(onePlayer);
        click(constants.GAME_PIECE_ONE);
        assertNodeExists(gameId);
    }

    @Test
    public void shouldCreateAHumanAndComputerWhenPlayerChoosesXInOnePlayerMode() {
        click(onePlayer);
        click(constants.GAME_PIECE_ONE);
        verify(game).onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldDisplayBoardWhenPlayerChoosesOInOnePlayerMode() {
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        assertNodeExists(gameId);
    }

    @Test
    public void shouldCreateAComputerAndHumanWhenPlayerChoosesXInOnePlayerMode() {
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        verify(game).onePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE);
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
        click(constants.GAME_PIECE_ONE);
        find(menuId);
    }

    @Test
    public void shouldRemoveMenuWhenPlayerChoosesOShowingBoardInOnePlayerMode() {
        exception.expect(NoNodesFoundException.class);
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        find(menuId);
    }

    @Test
    public void shouldSetThePlayerWithTheCoordinatesWhenPlayerChoosesInOnePlayerMode() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        click(center);
        verify(game).set(1, 1);
    }

    @Test
    public void shouldCheckForGameOverWhenPlayerChoosesInOnePlayerMode() {
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        click(center);
        verify(game, times(2)).over();
    }

    @Test
    public void shouldRevealReplayButtonWhenGameOverInOnePlayerMode() {
        when(game.over()).thenReturn(true);
        click(onePlayer);
        click(constants.GAME_PIECE_ONE);
        click(center);
        verifyThat(constants.REPLAY_ID, hasText(replay));
    }

    @Test
    public void shouldRevealResetButtonWhenGameOverInOnePlayerMode() {
        when(game.over()).thenReturn(true);
        click(onePlayer);
        click(constants.GAME_PIECE_ONE);
        click(center);
        verifyThat(constants.RESET_ID, hasText(reset));
    }

    @Test
    public void shouldRevealReplayButtonWhenGameOverInTwoPlayerMode() {
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(constants.REPLAY_ID, hasText(replay));
    }

    @Test
    public void shouldRevealResetButtonWhenGameOverInTwoPlayerMode() {
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(constants.RESET_ID, hasText(reset));
    }

    @Test
    public void shouldDisplayWinnerWhenGameIsOverForXInOnePlayerMode() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(onePlayer);
        click(constants.GAME_PIECE_ONE);
        click(center);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_ONE + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldDisplayWinnerWhenGameIsOverForOInOnePlayerMode() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(onePlayer);
        click(constants.GAME_PIECE_TWO);
        click(center);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldDisplayWinnerWhenGameIsOverForXInTwoPlayerMode() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_ONE + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldDisplayWinnerWhenGameIsOverForOInTwoPlayerMode() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
    }

    @Test
    public void shouldDisplayTheMenuWhenResetIsClicked() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        click(reset);
        assertNodeExists(menuId);
    }

    @Test
    public void shouldRemoveMessageIfAnyWhenPlayerClicksOnEmptySpaceIsClicked() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        doThrow(new NotVacantException()).when(game).set(1,1);
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        click(twoPlayer);
        click(center);
        verifyThat(constants.MESSAGES_ID, hasText(constants.NOT_VACANT_MESSAGE));
        click(centerLeft);
        verifyThat(constants.MESSAGES_ID, hasText(constants.EMPTY));
    }

    @Test
    public void shouldRemoveMessageWhenResetIsClicked() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
        click(reset);
        verifyThat(constants.MESSAGES_ID, hasText(constants.EMPTY));
    }

    @Test
    public void shouldRemoveTheGameWhenResetIsClicked() {
        exception.expect(NoNodesFoundException.class);
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        click(reset);
        find(gameId);
    }

    @Test
    public void shouldResetTheGameWhenReplayIsChosen() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        click(replay);
        verify(game).reset();
    }

    @Test
    public void shouldRemoveMessageWhenReplayIsChosen() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
        click(replay);
        verifyThat(constants.MESSAGES_ID, hasText(constants.EMPTY));
    }

    @Test
    public void shouldNotRemoveWinMessageWhenPlayerHasWonButStillTryingToClickOnASpace() {
        Player player = mock(Player.class);
        when(player.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(game.getWinner()).thenReturn(player);
        when(game.over()).thenReturn(true);
        click(twoPlayer);
        click(center);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
        click(centerLeft);
        verifyThat(constants.MESSAGES_ID, hasText(constants.GAME_PIECE_TWO + constants.HAS_WON_MESSAGE));
    }
}
