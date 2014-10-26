package models;

import lang.constants;
import org.junit.Before;
import org.junit.Test;

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
        board.setBoard(players);
        board.set(0, 2, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstRow() {
        players[0] = player;
        players[2] = player;
        board.setBoard(players);
        board.set(1, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstColumn() {
        players[0] = player;
        players[3] = player;
        board.setBoard(players);
        board.set(2, 0, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstColumn() {
        players[0] = player;
        players[2] = player;
        board.setBoard(players);
        board.set(1, 1, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondRow() {
        players[4] = player;
        players[5] = player;
        board.setBoard(players);
        board.set(1, 0, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondRow() {
        players[4] = player;
        players[5] = player;
        board.setBoard(players);
        board.set(0, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondColumn() {
        players[1] = player;
        players[7] = player;
        board.setBoard(players);
        board.set(1, 1, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondColumn() {
        players[4] = player;
        players[7] = player;
        board.setBoard(players);
        board.set(0, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdRow() {
        players[6] = player;
        players[7] = player;
        board.setBoard(players);
        board.set(2, 2, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdRow() {
        players[6] = player;
        players[7] = player;
        board.setBoard(players);
        board.set(0, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdColumn() {
        players[5] = player;
        players[8] = player;
        board.setBoard(players);
        board.set(0, 2, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdColumn() {
        players[2] = player;
        players[8] = player;
        board.setBoard(players);
        board.set(0, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheLeftDiagonal() {
        players[0] = player;
        players[4] = player;
        board.setBoard(players);
        board.set(2, 2, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheLeftDiagonal() {
        players[4] = player;
        players[8] = player;
        board.setBoard(players);
        board.set(1, 0, player);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheRightDiagonal() {
        players[2] = player;
        players[6] = player;
        board.setBoard(players);
        board.set(1, 1, player);
        assertThat(board.getWinner(), is(equalTo(player)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheRightDiagonal() {
        players[2] = player;
        players[4] = player;
        board.setBoard(players);
        board.set(2, 2, player);
        assertThat(board.getWinner(), is(equalTo(null)));
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
        board.set(2, 1, player);
        assertThat(board.get(2, 1), is(equalTo(player)));
    }
}