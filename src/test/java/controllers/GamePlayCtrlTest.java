package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;
import models.ComputerPlayer;
import models.Player;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import static org.mockito.Mockito.*;

public class GamePlayCtrlTest {

    private GamePlayCtrl gamePlayCtrl;
    private PlayerCtrl playerCtrl;
    private GameCtrl gameCtrl;
    private Player player1;
    private ComputerPlayer computer;
    private Player player2;

    @Before
    public void setup() {
        player1 = mock(Player.class);
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        player2 = mock(Player.class);
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        computer = mock(ComputerPlayer.class);
        when(computer.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        gameCtrl = mock(GameCtrl.class);
        playerCtrl = mock(PlayerCtrl.class);
        gamePlayCtrl = new GamePlayCtrlImpl(gameCtrl, playerCtrl);
        when(playerCtrl.setupOnePlayer(anyString(), anyString())).thenReturn(new Player[]{player1, computer});
        when(playerCtrl.setupTwoPlayer()).thenReturn(new Player[]{player1, player2});
    }

    @Test
    public void shouldBeAbleToSetupATwoPlayerGame() {
        gamePlayCtrl.twoPlayer();
        verify(playerCtrl).setupTwoPlayer();
        verify(gameCtrl).setup();
    }

    @Test
    public void shouldBeAbleToSetupAOnePlayerGame() {
        gamePlayCtrl.onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        verify(playerCtrl).setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        verify(gameCtrl).setup();
    }

    @Test
    public void shouldBeAbleToAllowTheComputerToPlayFirstIfItIsX() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        when(computer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        gamePlayCtrl.onePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE);
        verify(computer).calculateBestMove(any(String[].class));
        verify(gameCtrl).setPiece(computer);
    }

    @Test
    public void shouldNotBeAbleToAllowTheComputerToPlayFirstIfItIsO() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        gamePlayCtrl.onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        verify(computer, never()).calculateBestMove(any(String[].class));
        verify(gameCtrl, never()).setPiece(computer);
    }

    @Test
    public void shouldBeAbleToCheckIfGameIsOver() {
        gamePlayCtrl.over();
        verify(gameCtrl).gameOver();
    }

    @Test
    public void shouldBeAbleToGetTheWinner() {
        gamePlayCtrl.getWinner();
        verify(gameCtrl).getWinner();
    }

    @Test
    public void shouldBeAbleToGetTheBoard() {
        gamePlayCtrl.getBoard();
        verify(gameCtrl).getBoard();
    }

    @Test
    public void shouldSetPlayerXOnFirstMoveForTwoPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        when(gameCtrl.getBoard()).thenReturn(new String[]{});
        int row = 1;
        int column = 2;
        gamePlayCtrl.twoPlayer();
        gamePlayCtrl.set(row, column);
        verify(player1).setCoordinates(row,column);
        verify(gameCtrl).setPiece(player1);
    }

    @Test
    public void shouldSetPlayerOOnSecondMoveForTwoPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        when(gameCtrl.getBoard()).thenReturn(new String[]{constants.GAME_PIECE_ONE});
        int row = 1;
        int column = 2;
        gamePlayCtrl.twoPlayer();
        gamePlayCtrl.set(row, column);
        verify(player2).setCoordinates(row,column);
        verify(gameCtrl).setPiece(player2);
    }

    @Test
    public void shouldSetPlayer1ThenComputerPlayer() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        String[] board = {};
        when(gameCtrl.getBoard()).thenReturn(board);
        InOrder inOrder = inOrder(player1, computer, gameCtrl);
        int row = 1;
        int column = 2;
        gamePlayCtrl.onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        gamePlayCtrl.set(row, column);
        inOrder.verify(player1).setCoordinates(row, column);
        inOrder.verify(gameCtrl).setPiece(player1);
        inOrder.verify(computer).calculateBestMove(board);
        inOrder.verify(gameCtrl).setPiece(computer);
    }

    @Test
    public void shouldNotCallComputerIfGameOver() throws NotVacantException, OutOfBoundsException, OutOfTurnException {
        String[] board = {};
        when(gameCtrl.getBoard()).thenReturn(board);
        when(gameCtrl.gameOver()).thenReturn(true);
        InOrder inOrder = inOrder(player1, computer, gameCtrl);
        int row = 1;
        int column = 2;
        gamePlayCtrl.onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        gamePlayCtrl.set(row, column);
        inOrder.verify(player1).setCoordinates(row, column);
        inOrder.verify(gameCtrl).setPiece(player1);
        inOrder.verify(computer, never()).calculateBestMove(board);
        inOrder.verify(gameCtrl, never()).setPiece(computer);
    }

    @Test
    public void shouldBeAbleToSetupAGame() {
        gamePlayCtrl.setup();
        verify(gameCtrl).setup();
    }

    @Test
    public void shouldCheckToSeeIfTheComputerPlayerShouldGoIfGameReset() throws OutOfBoundsException, NotVacantException {
        when(computer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        gamePlayCtrl.onePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE);
        gamePlayCtrl.reset();
        verify(computer, times(2)).calculateBestMove(any(String[].class));
    }

    @Test
    public void shouldBeAbleToRestTheGame() throws OutOfBoundsException, NotVacantException {
        gamePlayCtrl.onePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE);
        gamePlayCtrl.reset();
        verify(gameCtrl, times(2)).setup();
    }
}