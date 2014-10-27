package models;

import exceptions.NotVacantException;
import lang.constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

public class BoardTest {

    private Board board;
    private Player[] players;
    private Player player;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        player = mock(Player.class);
        players = new Player[constants.SIDE * constants.SIDE];
        board = new BoardImpl(constants.SIDE);
    }

    @Test
    public void shouldNotBeAbleToSetAPlayerOnATakenSpot() throws NotVacantException {
        exception.expect(NotVacantException.class);
        board.set(1, 1, player);
        board.set(1, 1, player);
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstRow() throws NotVacantException {
        players[0] = player;
        players[1] = player;
        board.setBoard(players);
        board.set(0, 2, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstRow() throws NotVacantException {
        players[0] = player;
        players[2] = player;
        board.setBoard(players);
        board.set(1, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstColumn() throws NotVacantException {
        players[0] = player;
        players[3] = player;
        board.setBoard(players);
        board.set(2, 0, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstColumn() throws NotVacantException {
        players[0] = player;
        players[2] = player;
        board.setBoard(players);
        board.set(1, 1, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondRow() throws NotVacantException {
        players[4] = player;
        players[5] = player;
        board.setBoard(players);
        board.set(1, 0, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondRow() throws NotVacantException {
        players[4] = player;
        players[5] = player;
        board.setBoard(players);
        board.set(0, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondColumn() throws NotVacantException {
        players[1] = player;
        players[7] = player;
        board.setBoard(players);
        board.set(1, 1, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondColumn() throws NotVacantException {
        players[4] = player;
        players[7] = player;
        board.setBoard(players);
        board.set(0, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdRow() throws NotVacantException {
        players[6] = player;
        players[7] = player;
        board.setBoard(players);
        board.set(2, 2, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdRow() throws NotVacantException {
        players[6] = player;
        players[7] = player;
        board.setBoard(players);
        board.set(0, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdColumn() throws NotVacantException {
        players[5] = player;
        players[8] = player;
        board.setBoard(players);
        board.set(0, 2, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdColumn() throws NotVacantException {
        players[2] = player;
        players[8] = player;
        board.setBoard(players);
        board.set(0, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheLeftDiagonal() throws NotVacantException {
        players[0] = player;
        players[4] = player;
        board.setBoard(players);
        board.set(2, 2, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheLeftDiagonal() throws NotVacantException {
        players[4] = player;
        players[8] = player;
        board.setBoard(players);
        board.set(1, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheRightDiagonal() throws NotVacantException {
        players[2] = player;
        players[6] = player;
        board.setBoard(players);
        board.set(1, 1, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheRightDiagonal() throws NotVacantException {
        players[2] = player;
        players[4] = player;
        board.setBoard(players);
        board.set(2, 2, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }
}