package models;

import exceptions.OutOfBoundsException;
import factories.StrategyGameFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputerPlayerTest {

    private StrategyGame strategyGame;
    private Player[] players;
    private ComputerPlayer computer;

    @Before
    public void setup() {
        StrategyGameFactory strategyGameFactory = mock(StrategyGameFactory.class);
        strategyGame = mock(StrategyGame.class);
        players = new Player[constants.SIDE * constants.SIDE];
        when(strategyGameFactory.createStrategyGame(constants.SIDE, players)).thenReturn(strategyGame);
        computer = new ComputerPlayerImpl(constants.GAME_PIECE_ONE, constants.SIDE, strategyGameFactory);
    }

    @Test
    public void shouldChooseCornerIfGoingFirst() throws OutOfBoundsException {
        when(strategyGame.boardEmpty()).thenReturn(true);
        computer.setBoard(players);
        computer.calculateBestMove();
        assertThat(computer.getX(), is(equalTo(constants.SIDE - 1)));
        assertThat(computer.getY(), is(equalTo(constants.SIDE - 1)));
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() throws OutOfBoundsException {
        when(strategyGame.boardEmpty()).thenReturn(false);
        Player player2 = mock(Player.class);
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(strategyGame.winningMove(0, 2)).thenReturn(true);
        players[0] = player2;
        players[3] = player2;
        players[4] = computer;
        players[6] = computer;
        players[7] = player2;
        players[8] = computer;
        computer.setBoard(players);
        computer.calculateBestMove();
        assertThat(computer.getX(), is(equalTo(0)));
        assertThat(computer.getY(), is(equalTo(2)));
    }

    @Test
    public void shouldBlockOpponentFromSettingTheWinningMove() throws OutOfBoundsException {
        when(strategyGame.boardEmpty()).thenReturn(false);
        Player player2 = mock(Player.class);
        when(player2.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        when(strategyGame.losingMove(1, 0, player2)).thenReturn(true);
        players[8] = computer;
        players[4] = player2;
        players[2] = computer;
        players[5] = player2;
        computer.setBoard(players);
        computer.calculateBestMove();
        assertThat(computer.getX(), is(equalTo(1)));
        assertThat(computer.getY(), is(equalTo(0)));
    }
}