package controllers;

import factories.PlayerFactory;
import lang.constants;
import models.ComputerPlayer;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

public class PlayerCtrlTest {

    private PlayerFactory playerFactory;
    private PlayerCtrl playerCtrl;
    private Player player1;
    private StrategyBoardCtrl strategyBoardCtrl;

    @Before
    public void setup() {
        player1 = mock(Player.class);
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        Player player2 = mock(Player.class);
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        playerFactory = mock(PlayerFactory.class);
        ComputerPlayer computer = mock(ComputerPlayer.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1, player2);
        when(playerFactory.createComputerPlayer(anyString(), anyInt(), any(Player.class), any(StrategyBoardCtrl.class))).thenReturn(computer);
        strategyBoardCtrl = mock(StrategyBoardCtrl.class);
        playerCtrl = new PlayerCtrlImpl(playerFactory, strategyBoardCtrl);
    }

    @Test
    public void shouldBeAbleToSetupATwoPlayerGame() {
        playerCtrl.setupTwoPlayer();
        verify(playerFactory).createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        verify(playerFactory).createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
    }

    @Test
    public void shouldBeAbleToSetupAOnePlayerGame() {
        playerCtrl.setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        verify(playerFactory).createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_TWO, constants.SIDE, player1, strategyBoardCtrl);
    }

    @Test
    public void shouldReceiveTwoPlayersThatAreNotComputerPlayersForATwoPlayerGame() {
        Player[] players = playerCtrl.setupTwoPlayer();
        Arrays.stream(players).forEach(player -> assertThat(player, not((Object) instanceOf(ComputerPlayer.class))));
    }

    @Test
    public void shouldReceiveTwoPlayersWithOneComputerPlayerForAOnePlayerGame() {
        Player[] players = playerCtrl.setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        assertThat(players[0], not((Object) instanceOf(ComputerPlayer.class)));
        assertThat(players[1], instanceOf(ComputerPlayer.class));
     }

    @Test
    public void shouldBeAbleToSpecifyHumanPlayerAsPlayerOne() {
        playerCtrl.setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        verify(playerFactory).createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_TWO, constants.SIDE, player1, strategyBoardCtrl);
    }

    @Test
    public void shouldBeAbleToSpecifyComputerPlayerAsPlayerOne() {
        playerCtrl.setupOnePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE);
        verify(playerFactory).createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_ONE, constants.SIDE, player1, strategyBoardCtrl);
    }
}