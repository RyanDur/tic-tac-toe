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
import static org.mockito.Mockito.verify;
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
        assertThat(computer.getX(), is(equalTo(constants.SIDE - 1)));
        assertThat(computer.getY(), is(equalTo(constants.SIDE - 1)));
    }

    @Test
    public void shouldSetBestMove() throws OutOfBoundsException {
        when(strategyGame.boardEmpty()).thenReturn(false);
        computer.setBoard(players);
        verify(strategyGame).setBestMove(computer);
    }
}
