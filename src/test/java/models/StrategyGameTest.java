package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StrategyGameTest {
    private Player[] board;
    private Player computer;
    private Player human;

    @Before
    public void setup() {
        board = new Player[constants.SIDE * constants.SIDE];
        computer = mock(ComputerPlayer.class);
        human = mock(Player.class);
        when(computer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(human.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldBeAbleToCheckIfABoardIsEmpty() {
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, board);
        assertThat(strategyGame.boardEmpty(), is(true));
    }

    @Test
    public void shouldBeAbleToFindTheWinningMove() {
        board[8] = computer;
        board[7] = human;
        board[6] = computer;
        board[3] = human;
        board[4] = computer;
        board[0] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, board);
        assertThat(strategyGame.findWinningMove(computer), is(equalTo(of(2))));
    }

    @Test
    public void shouldNotBeAbleToFindTheWinningMoveIfNoneExists() {
        board[8] = computer;
        board[7] = human;
        board[6] = computer;
        board[3] = human;
        board[4] = computer;
        board[0] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, board);
        assertThat(strategyGame.findWinningMove(human), is(empty()));
    }

    @Test
    public void shouldBeAbleToFindTheLosingMove() {
        board[8] = computer;
        board[4] = human;
        board[2] = computer;
        board[5] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, board);
        assertThat(strategyGame.findWinningMove(human), is(equalTo(of(3))));
    }


    @Test
    public void shouldNotBeAbleToFindTheLosingMoveIfNoneExist() {
        board[8] = computer;
        board[7] = human;
        board[6] = computer;
        board[3] = human;
        board[4] = computer;
        board[0] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, board);
        assertThat(strategyGame.findWinningMove(human), is(empty()));
    }

    @Test
    public void shouldBeAbleToFindTheBestMove() throws NotVacantException, OutOfBoundsException {
        board[8] = computer;
        board[4] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, board);
        assertThat(strategyGame.findBestMove(computer, human), is(equalTo(of(3))));
    }
}