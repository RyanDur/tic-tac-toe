package models;

import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

public class BoardTest {

    Board board;
    private Player[] players;
    private Player player;

    @Before
    public void setup() {
        player = mock(Player.class);
        players = new Player[constants.SIDE * constants.SIDE];
        board = new BoardImpl(constants.SIDE);
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstRow() {
        players[0] = player;
        players[1] = player;
        players[2] = player;
        board.setBoard(players);
        assertThat(board.isWinner(0, 0, player), is(true));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstRow() {
        players[0] = player;
        players[2] = player;
        board.setBoard(players);
        assertThat(board.isWinner(0, 0, player), is(false));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondRow() {
        players[3] = player;
        players[4] = player;
        players[5] = player;
        board.setBoard(players);
        assertThat(board.isWinner(1, 1, player), is(true));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondRow() {
        players[4] = player;
        players[5] = player;
        board.setBoard(players);
        assertThat(board.isWinner(1, 1, player), is(false));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdRow() {
        players[6] = player;
        players[7] = player;
        players[8] = player;
        board.setBoard(players);
        assertThat(board.isWinner(2, 2, player), is(true));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdRow() {
        players[6] = player;
        players[7] = player;
        board.setBoard(players);
        assertThat(board.isWinner(2, 0, player), is(false));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheLeftDiagonal() {
        players[0] = player;
        players[4] = player;
        players[8] = player;
        board.setBoard(players);
        assertThat(board.isWinner(2, 2, player), is(true));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheLeftDiagonal() {
        players[4] = player;
        players[8] = player;
        board.setBoard(players);
        assertThat(board.isWinner(1, 1, player), is(false));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheRightDiagonal() {
        players[2] = player;
        players[4] = player;
        players[6] = player;
        board.setBoard(players);
        assertThat(board.isWinner(2, 0, player), is(true));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheRightDiagonal() {
        players[2] = player;
        players[4] = player;
        board.setBoard(players);
        assertThat(board.isWinner(1, 1, player), is(false));
    }

    @Test
    public void shouldBeAbleToGetAPieceFromTheBoard() {
        players[2] = player;
        players[4] = player;
        board.setBoard(players);
        assertThat(board.get(1, 1), is(equalTo(player)));
    }

    @Test
    public void shouldBeAbleToSetAPieceOnTheBoard() {
        players[2] = player;
        players[4] = player;
        board.setBoard(players);
        board.set(2,1, player);
        assertThat(board.get(2, 1), is(equalTo(player)));
    }

    @Test
    public void shouldBeAbleToGetTheVacantSpotsOnTheBoard() {
        players[2] = player;
        players[4] = player;
        players[6] = player;
        board.setBoard(players);
        List<Integer[]> expected = new ArrayList<>();
        expected.add(new Integer[]{0, 0});
        expected.add(new Integer[]{0, 1});
        expected.add(new Integer[]{1, 0});
        expected.add(new Integer[]{1, 2});
        expected.add(new Integer[]{2, 1});
        expected.add(new Integer[]{2, 2});
        List<Integer[]> actual = board.getVacancies();
        assertThat(actual.size(), is(equalTo(expected.size())));
        for(int i = 0; i < expected.size(); i++) {
            assertThat(actual.get(i), is(equalTo(expected.get(i))));
        }
    }

    @Test
    public void shouldBeAbleToRetrieveTheLastMoveOnTheBoard() {
        board.set(2,1, player);
        assertThat(board.lastMove(), is(equalTo(new Integer[]{2,1})));
    }
}