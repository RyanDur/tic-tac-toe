package models;

import exceptions.NotVacantException;
import lang.constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GameTest {

    private Game game;
    private Player mockPlayer;
    private Player player2;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        game = new GameImpl(constants.SIDE);
        mockPlayer = mock(Player.class);
        player2 = mock(Player.class);
    }

    @Test
    public void shouldNotBeAbleToSetAPlayerOnASpotAlreadyTaken() throws NotVacantException {
        exception.expect(NotVacantException.class);
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
    }

    @Test
    public void shouldKnowTheNumberOfPiecesPlacedOnTheBoard() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getNumOfPieces(), is(equalTo(2)));
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getNumOfPieces(), is(equalTo(3)));
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getNumOfPieces(), is(equalTo(4)));
    }

    @Test
    public void shouldBeAbleToGetTheWinnerWhenThereIsAWinnerViaTheTopRow() throws NotVacantException {
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        Assert.assertThat(game.getWinner(), is(mockPlayer));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheTopRow() throws NotVacantException {
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(player2.getY()).thenReturn(1);
        game.set(player2);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheMiddleRow() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(mockPlayer)));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheMiddleRow() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(1);
        when(player2.getX()).thenReturn(1);
        when(player2.getY()).thenReturn(0);
        game.set(player2);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheBottomRow() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(mockPlayer)));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheBottomRow() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(2);
        game.set(player2);
        assertThat(game.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLeftColumn() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(mockPlayer)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheLeftColumn() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        game.set(player2);
        when(mockPlayer.getX()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheMiddleColumn() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(mockPlayer)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheMiddleColumn() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(1);
        game.set(player2);
        assertThat(game.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLastColumn() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(mockPlayer)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheLastColumn() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(1);
        when(player2.getY()).thenReturn(2);
        game.set(player2);
        when(mockPlayer.getX()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLeftDiagonal() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(mockPlayer));
    }

    @Test
    public void shouldNotBeOverIfDiagonalIsFullAndNotMatchingViaTheLeftDiagonal() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(1);
        when(player2.getY()).thenReturn(1);
        game.set(player2);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheRightDiagonal() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(mockPlayer));
    }

    @Test
    public void shouldNotGetAFalseNegativeWinDependingOnOrderOfPlacement() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        assertThat(game.getWinner(), is(mockPlayer));
    }

    @Test
    public void shouldNotBeOverIfDiagonalIsFullAndNotMatchingViaTheRightDiagonal() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(0);
        game.set(player2);
        assertThat(game.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToCheckForADraw() throws NotVacantException {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(0);
        when(player2.getY()).thenReturn(1);
        game.set(player2);
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(0);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(1);
        when(player2.getY()).thenReturn(1);
        game.set(player2);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(2);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(0);
        game.set(player2);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(1);
        game.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(2);
        game.set(player2);

        assertThat(game.getWinner(), is(equalTo(null)));
        assertThat(game.full(), is(true));
    }

    @Test
    public void shouldBeAbleToRetrieveACopyOfTheBoard() {
        assertThat(game.getBoard(), is(equalTo(new Player[constants.SIDE * constants.SIDE])));
    }

    @Test
    public void shouldBeAbleToRetrieveACopyOfTheBoardAfterGameStart() throws NotVacantException {
        int x = 0;
        int y = 2;
        int index = (x * constants.SIDE) + y;
        when(mockPlayer.getX()).thenReturn(x);
        when(mockPlayer.getY()).thenReturn(y);
        game.set(mockPlayer);
        Player[] board = new Player[constants.SIDE * constants.SIDE];
        board[index] = mockPlayer;
        assertThat(game.getBoard(), is(equalTo(board)));
    }
}