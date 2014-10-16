package models;

import lang.constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BoardTest {

    private Board board;
    private Player mockPlayer;
    private Player player2;

    @Before
    public void setup() {
        board = new BoardImpl(constants.SIDE);
        mockPlayer = mock(Player.class);
        player2 = mock(Player.class);
    }

    @Test
    public void shouldBeAbleToSetAPieceOnTheBoard() {
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        assertThat(board.get(1,1), is(equalTo(mockPlayer)));
    }

    @Test
    public void shouldKnowTheNumberOfPiecesPlacedOnTheBoard() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.getNumOfPieces(), is(equalTo(2)));
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.getNumOfPieces(), is(equalTo(3)));
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.getNumOfPieces(), is(equalTo(4)));
    }

    @Test
    public void shouldReturnFalseIfSpaceIsNotVacant() {
        board.set(mockPlayer);
        assertThat(board.isVacant(0,0), is(false));
    }

    @Test
    public void shouldReturnTrueIfSpaceIsVacant() {
        assertThat(board.isVacant(0,0), is(true));
    }

    @Test
    public void shouldBeAbleToGetTheWinnerWhenThereIsAWinnerViaTheTopRow() {
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        Assert.assertThat(board.winner(), is(true));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheTopRow() {
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(player2.getY()).thenReturn(1);
        board.set(player2);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheMiddleRow() {
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheMiddleRow() {
        when(mockPlayer.getX()).thenReturn(1);
        when(player2.getX()).thenReturn(1);
        when(player2.getY()).thenReturn(0);
        board.set(player2);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheBottomRow() {
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheBottomRow() {
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(2);
        board.set(player2);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLeftColumn() {
        when(mockPlayer.getX()).thenReturn(0);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheLeftColumn() {
        when(mockPlayer.getX()).thenReturn(0);
        board.set(player2);
        when(mockPlayer.getX()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheMiddleColumn() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheMiddleColumn() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(1);
        board.set(player2);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLastColumn() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheLastColumn() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(1);
        when(player2.getY()).thenReturn(2);
        board.set(player2);
        when(mockPlayer.getX()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLeftDiagonal() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(true));
    }

    @Test
    public void shouldNotBeOverIfDiagonalIsFullAndNotMatchingViaTheLeftDiagonal() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(1);
        when(player2.getY()).thenReturn(1);
        board.set(player2);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        assertThat(board.winner(), is(false));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheRightDiagonal() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        assertThat(board.winner(), is(true));
    }

    @Test
    public void shouldNotBeOverIfDiagonalIsFullAndNotMatchingViaTheRightDiagonal() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(0);
        board.set(player2);
        assertThat(board.winner(), is(false));
    }

    @Test
    public void shouldBeAbleToCheckForADraw() {
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(0);
        when(player2.getY()).thenReturn(1);
        board.set(player2);
        when(mockPlayer.getX()).thenReturn(0);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(0);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(1);
        when(player2.getY()).thenReturn(1);
        board.set(player2);
        when(mockPlayer.getX()).thenReturn(1);
        when(mockPlayer.getY()).thenReturn(2);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(0);
        board.set(player2);
        when(mockPlayer.getX()).thenReturn(2);
        when(mockPlayer.getY()).thenReturn(1);
        board.set(mockPlayer);
        when(player2.getX()).thenReturn(2);
        when(player2.getY()).thenReturn(2);
        board.set(player2);

        assertThat(board.winner(), is(false));
        assertThat(board.full(), is(true));
    }
}