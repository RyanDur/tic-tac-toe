package models;

import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

public class StrategyGameTest {
    private StrategyGame strategyGame;
    private Player[] board;

    @Before
    public void setup() {
        board = new Player[constants.SIDE * constants.SIDE];
        strategyGame = new StrategyGameImpl(constants.SIDE, board);
    }

    @Test
    public void shouldBeAbleToCheckIfABoardIsEmpty() {
        assertThat(strategyGame.boardEmpty(), is(true));
    }

    @Test
    public void shouldBeAbleToFindTheWinningMove() {
        Player computer = mock(ComputerPlayer.class);
        Player human = mock(Player.class);
        board[8] = computer;
        board[7] = human;
        board[6] = computer;
        board[3] = human;
        board[4] = computer;
        board[0] = human;

        assertThat(strategyGame.findWinningMove(computer), is(equalTo(Optional.of(2))));
    }

    @Test
    public void shouldBeAbleToFindTheLosingMove() {
        Player computer = mock(ComputerPlayer.class);
        Player human = mock(Player.class);
        board[8] = computer;
        board[4] = human;
        board[2] = computer;
        board[5] = human;

        assertThat(strategyGame.findLosingMove(human), is(equalTo(Optional.of(3))));
    }
}