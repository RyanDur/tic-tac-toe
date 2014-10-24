package controllers;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.PlayerFactory;
import lang.constants;
import models.ComputerPlayer;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlayerCtrlTest {

    private PlayerFactory playerFactory;
    private PlayerCtrl playerCtrl;
    private Player player1;
    private Player player2;
    private StrategyGameCtrl strategyGameCtrl;
    private ComputerPlayer computer;

    @Before
    public void setup() {
        player1 = mock(Player.class);
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        player2 = mock(Player.class);
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        playerFactory = mock(PlayerFactory.class);
        computer = mock(ComputerPlayer.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1, player2);
        when(playerFactory.createComputerPlayer(anyString(), anyInt(), any(Player.class), any(StrategyGameCtrl.class))).thenReturn(computer);
        strategyGameCtrl = mock(StrategyGameCtrl.class);
        playerCtrl = new PlayerCtrlImpl(playerFactory, strategyGameCtrl);
    }

    @Test
    public void shouldBeAbleToSetupATwoPlayerGame() {
        playerCtrl.setupTwoPlayer();
        verify(playerFactory).createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        verify(playerFactory).createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
    }

    @Test
    public void shouldGetTheCorrectFirstMoveWhenAsked() {
        playerCtrl.setupTwoPlayer();
        assertThat(playerCtrl.getPlayer(new Player[]{}), is(equalTo(player1)));
    }

    @Test
    public void shouldGetTheCorrectSecondMoveWhenAsked() {
        playerCtrl.setupTwoPlayer();
        assertThat(playerCtrl.getPlayer(new Player[]{player1}), is(equalTo(player2)));
    }

    @Test
    public void shouldBeAbleToSetupAOnePlayerGame() {
        playerCtrl.setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        verify(playerFactory).createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_TWO, constants.SIDE, player1, strategyGameCtrl);
    }

    @Test
    public void shouldOnlyGetHumanPlayerIfOnePlayerMode() {
        playerCtrl.setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        assertThat(playerCtrl.getPlayer(new Player[]{}), is(equalTo(player1)));
        assertThat(playerCtrl.getPlayer(new Player[]{player1}), is(equalTo(player1)));
    }

    @Test
    public void shouldGet0IfAPlayerModeHasNotBeenSetup() {
        assertThat(playerCtrl.playerCount(), is(equalTo(0)));
    }

    @Test
    public void shouldBeAbleToGetTheNumberOfHumanPlayersInTwoPlayerMode() {
        playerCtrl.setupTwoPlayer();
        assertThat(playerCtrl.playerCount(), is(equalTo(2)));
    }

    @Test
    public void shouldBeAbleToGetTheNumberOfHumanPlayersInOnePlayerMode() {
        playerCtrl.setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        assertThat(playerCtrl.playerCount(), is(equalTo(1)));
    }

    @Test
    public void shouldNotBeAbleToGetAComputerPlayerIfInTwoPlayerMode() throws NotVacantException, OutOfBoundsException {
        playerCtrl.setupTwoPlayer();
        assertThat(playerCtrl.getComputerPlayer(new Player[]{}), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToGetAComputerPlayerIfInOnePlayerMode() throws NotVacantException, OutOfBoundsException {
        playerCtrl.setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        assertThat(playerCtrl.getComputerPlayer(new Player[]{}), is(equalTo(computer)));
    }
}