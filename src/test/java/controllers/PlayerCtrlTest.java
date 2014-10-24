package controllers;

import factories.PlayerFactory;
import lang.constants;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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

    @Before
    public void setup() {
        player1 = mock(Player.class);
        when(player1.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        player2 = mock(Player.class);
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        playerFactory = mock(PlayerFactory.class);
        when(playerFactory.createPlayer(anyString(), anyInt())).thenReturn(player1, player2);
        strategyGameCtrl = mock(StrategyGameCtrl.class);
        playerCtrl = new PlayerCtrlImpl(constants.SIDE, playerFactory, strategyGameCtrl);
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
        Player[] players = new Player[]{player1};
        assertThat(playerCtrl.getMove(new Player[]{}), is(equalTo(players)));
    }

    @Test
    public void shouldGetTheCorrectSecondMoveWhenAsked() {
        playerCtrl.setupTwoPlayer();
        Player[] players = new Player[]{player2};
        assertThat(playerCtrl.getMove(new Player[]{player1}), is(equalTo(players)));
    }

    @Test
    public void shouldBeAbleToSetupAOnePlayerGame() {
        playerCtrl.setupOnePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO);
        verify(playerFactory).createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        verify(playerFactory).createComputerPlayer(constants.GAME_PIECE_TWO, constants.SIDE, player1, strategyGameCtrl);
    }

    @Test
    public void shouldGetBothMovesForOnePlayerGameAndComputerPlayerIsO() {
        playerCtrl.setupTwoPlayer();
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        Player[] players = new Player[]{player1, player2};
        assertThat(playerCtrl.getMove(new Player[]{}), is(equalTo(players)));
    }
}