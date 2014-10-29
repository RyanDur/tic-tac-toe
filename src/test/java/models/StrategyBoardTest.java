package models;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.when;

public class StrategyBoardTest {

    private final String pieceOne = constants.GAME_PIECE_ONE;
    private String player1;
    private String[] players;

    @Before
    public void setup() {
        player1 = constants.GAME_PIECE_ONE;
        players = new String[constants.SIDE * constants.SIDE];
    }

    @Test
    public void shouldBeAbleToGetTheWinner() throws NotVacantException, OutOfTurnException {
        players[0] = pieceOne;
        players[1] = pieceOne;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        board.set(0, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldGetAllTheSpacesOfAVacantBoard() {
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        assertThat(board.getVacancies().size(), is(equalTo(players.length)));
    }

    @Test
    public void shouldBeAbleToGetTheVacantSpacesOfABoardBoard() {
        players[0] = pieceOne;
        players[1] = pieceOne;
        players[2] = pieceOne;
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        assertThat(board.getVacancies().size(), is(equalTo(players.length - constants.SIDE)));
    }

    @Test
    public void shouldBeAbleToGetTheWinningMoveForFirstRow() {
        players[0] = pieceOne;
        players[1] = pieceOne;
        when(player1).thenReturn(pieceOne);
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        Integer[] expected = {0, 2};
        assertThat(board.winningMove(player1).get(), is(equalTo(expected)));
    }

    @Test
    public void shouldBeAbleToGetTheWinningMoveForSecondRow() {
        players[3] = pieceOne;
        players[5] = pieceOne;
        when(player1).thenReturn(pieceOne);
        StrategyBoard board = new StrategyBoardImpl(constants.SIDE, players);
        Integer[] expected = {1, 1};
        assertThat(board.winningMove(player1).get(), is(equalTo(expected)));
    }

    @Test
    public void shouldBeAbleToFilterTheBestMovesFromTheWorstIfPlayerIsInACorner() {
        players[0] = pieceOne;
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
        when(player1).thenReturn(pieceOne);
        players[1] = pieceOne;
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

        players[4] = pieceOne;
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
        players[0] = pieceOne;
        players[4] = constants.GAME_PIECE_TWO;
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