package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.BoardFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StrategyGameTest {
    private Board board;
    private Player computer;
    private Player human;
    private BoardFactory boardFactory;
    private Player[] players;

    @Before
    public void setup() {
        players = new Player[constants.SIDE * constants.SIDE];
        board = mock(Board.class);
        computer = mock(ComputerPlayer.class);
        human = mock(Player.class);
        boardFactory = mock(BoardFactory.class);
        when(boardFactory.createBoard(constants.SIDE)).thenReturn(board);
        when(computer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(human.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldBeAbleToCheckIfABoardIsEmpty() {
        when(board.getBoard()).thenReturn(players);
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.boardEmpty(), is(true));
    }

    @Test
    public void shouldBeAbleToFindTheWinningMove() {
        int x = 0;
        int y = 2;
        Integer[] expected = new Integer[]{x, y};
        when(board.isWinner(x, y, computer)).thenReturn(true);
        when(board.getVacancies()).thenReturn(Arrays.<Integer[]>asList(
                new Integer[]{0, 1},
                expected,
                new Integer[]{1, 2}));
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.findWinningMove(computer), is(equalTo(Optional.of(expected))));
    }

    @Test
    public void shouldNotBeAbleToFindTheWinningMoveIfNoneExists() {
        when(board.getVacancies()).thenReturn(Arrays.<Integer[]>asList(
                new Integer[]{0, 1},
                new Integer[]{1, 2}));
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.findWinningMove(human), is(Optional.empty()));
    }

    @Test
    public void shouldBeAbleToFindTheLosingMove() {
        int x = 1;
        int y = 0;
        Integer[] expected = new Integer[]{x, y};
        when(board.isWinner(x, y, human)).thenReturn(true);
        when(board.getVacancies()).thenReturn(Arrays.<Integer[]>asList(
                new Integer[]{0, 0},
                new Integer[]{0, 1},
                expected,
                new Integer[]{2, 0},
                new Integer[]{2, 1}));
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.findWinningMove(human), is(Optional.of(expected)));
    }

    @Test
    public void shouldNotBeAbleToFindTheLosingMoveIfNoneExist() {
        when(board.getVacancies()).thenReturn(Arrays.<Integer[]>asList(
                new Integer[]{0, 0},
                new Integer[]{0, 1},
                new Integer[]{2, 0},
                new Integer[]{2, 1}));
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.findWinningMove(human), is(Optional.empty()));
    }

    @Test
    public void shouldBeAbleToFindTheBestMoves() throws NotVacantException, OutOfBoundsException {
        Integer[] value1 = {0, 2};
        Integer[] value2 = {1, 2};
        Integer[] value3 = {2, 0};
        Integer[] value4 = {2, 1};
        List<Integer[]> expected = Arrays.asList(value1, value2, value3, value4);
        when(board.getVacancies()).thenReturn(Arrays.<Integer[]>asList(
                new Integer[]{0, 0},
                new Integer[]{0, 1},
                value1,
                new Integer[]{1, 0},
                value2,
                value3,
                value4));
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        when(board.lastMove()).thenReturn(value1, value2, value3, value4);
        when(board.isWinner(0, 2, computer)).thenReturn(true, false);
        when(board.isWinner(1, 2, computer)).thenReturn(true, false);
        when(board.isWinner(2, 0, computer)).thenReturn(true, false);
        when(board.isWinner(2, 1, computer)).thenReturn(true, false);
        strategyGame.filterMoves(computer).forEach(actual -> assertThat(expected.contains(actual), is(true)));
    }
}