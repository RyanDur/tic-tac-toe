package models;

import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.mock;

public class StrategyBoardTest {

    private Player player1;
    private Player player2;
    private Player[] players;

    @Before
    public void setup() {
        player1 = mock(Player.class);
        player2 = mock(Player.class);
        players = new Player[constants.SIDE * constants.SIDE];
    }

    @Test
    public void shouldBeAbleToGetTheWinner() {
        players[0] = player1;
        players[1] = player1;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        board.set(0, 2, player1);
        assertThat(board.getWinner(), is(equalTo(player1)));
    }

    @Test
    public void shouldGetAllTheSpacesOfAVacantBoard() {
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        assertThat(board.getVacancies().size(), is(equalTo(players.length)));
    }

    @Test
    public void shouldBeAbleToGetTheVacantSpacesOfABoardBoard() {
        players[0] = player1;
        players[1] = player1;
        players[2] = player1;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        assertThat(board.getVacancies().size(), is(equalTo(players.length - constants.SIDE)));
    }

    @Test
    public void shouldBeAbleToGetTheWinningMoveForFirstRow() {
        players[0] = player1;
        players[1] = player1;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        Integer[] expected = {0, 2};
        assertThat(board.winningMove(player1).get(), is(equalTo(expected)));
    }

    @Test
    public void shouldBeAbleToGetTheWinningMoveForSecondRow() {
        players[3] = player1;
        players[5] = player1;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        Integer[] expected = {1, 1};
        assertThat(board.winningMove(player1).get(), is(equalTo(expected)));
    }

    @Test
    public void shouldBeAbleToFilterTheBestMovesFromTheWorstIfPlayerIsInACorner() {
        players[0] = player1;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        Integer[] expected1 = {0, 2};
        Integer[] expected2 = {0, 1};
        Integer[] expected3 = {2, 2};
        Integer[] expected4 = {1, 1};
        Integer[] expected5 = {1, 0};
        Integer[] expected6 = {2, 0};
        List<Integer[]> expected = Arrays.asList(expected1, expected2, expected3, expected4, expected5, expected6);
        List<Integer[]> actual = board.filterMoves(player1);
        assertThat(actual.size(), is(equalTo(expected.size())));
        expected.stream().forEach(move -> assertThat(actual, hasItem(move)));
    }

    @Test
    public void shouldBeAbleToFilterTheBestMovesFromTheWorstIfPlayerIsOnASide() {
        players[1] = player1;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        Integer[] expected1 = {0, 2};
        Integer[] expected2 = {0, 0};
        Integer[] expected3 = {2, 1};
        Integer[] expected4 = {1, 1};
        List<Integer[]> expected = Arrays.asList(expected1, expected2, expected3, expected4);
        List<Integer[]> actual = board.filterMoves(player1);
        assertThat(actual.size(), is(equalTo(expected.size())));
        expected.stream().forEach(move -> assertThat(actual, hasItem(move)));
    }

    @Test
    public void shouldBeAbleToFilterTheBestMovesFromTheWorstIfPlayerIsInTheMiddle() {
        players[4] = player1;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        Integer[] expected1 = {0, 0};
        Integer[] expected2 = {0, 1};
        Integer[] expected3 = {0, 2};
        Integer[] expected4 = {1, 0};
        Integer[] expected5 = {1, 2};
        Integer[] expected6 = {2, 0};
        Integer[] expected7 = {2, 1};
        Integer[] expected8 = {2, 2};
        List<Integer[]> expected = Arrays.asList(expected1, expected2, expected3, expected4, expected5, expected6, expected7, expected8);
        List<Integer[]> actual = board.filterMoves(player1);
        assertThat(actual.size(), is(equalTo(expected.size())));
        expected.stream().forEach(move -> assertThat(actual, hasItem(move)));
    }

    @Test
    public void shouldBeAbleToFilterTheBestMovesFromTheWorstIfPlayerIsBlockedByAnother() {
        players[0] = player1;
        players[4] = player2;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        Integer[] expected2 = {0, 1};
        Integer[] expected3 = {0, 2};
        Integer[] expected4 = {1, 0};
        Integer[] expected6 = {2, 0};
        List<Integer[]> expected = Arrays.asList(expected2, expected3, expected4, expected6);
        List<Integer[]> actual = board.filterMoves(player1);
        assertThat(actual.size(), is(equalTo(expected.size())));
        expected.stream().forEach(move -> assertThat(actual, hasItem(move)));
    }
}