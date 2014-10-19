package models;

import exceptions.OutOfBoundsException;
import factories.StrategyGameFactory;
import lang.constants;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputerPlayerTest {

    @Test
    public void shouldChooseCornerIfGoingFirst() throws OutOfBoundsException {
        StrategyGameFactory strategyGameFactory = mock(StrategyGameFactory.class);
        StrategyGame strategyGame = mock(StrategyGame.class);
        Player[] players = new Player[constants.SIDE * constants.SIDE];
        when(strategyGameFactory.createStrategyGame(constants.SIDE, players)).thenReturn(strategyGame);
        when(strategyGame.boardEmpty()).thenReturn(true);
        ComputerPlayer computer = new ComputerPlayerImpl(constants.GAME_PIECE_ONE, constants.SIDE, strategyGameFactory);
        computer.setBoard(players);
        assertThat(computer.getX(), is(equalTo(constants.SIDE - 1)));
        assertThat(computer.getY(), is(equalTo(constants.SIDE - 1)));
    }
}
