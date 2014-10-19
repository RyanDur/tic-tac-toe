package models;

import lang.constants;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class StrategyGameTest {
    private StrategyGame strategyGame;

    @Before
    public void setup() {
        Player[] players = new Player[constants.SIDE * constants.SIDE];
        strategyGame = new StrategyGameImpl(constants.SIDE, players);
    }

    @Test
    public void shouldBeAbleToCheckIfABoardIsEmpty() {
        assertThat(strategyGame.boardEmpty(), is(true));
    }
}