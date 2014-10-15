package models;

import lang.constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;

public class BoardTest {

    private Board board;
    private Player mockPlayer;

    @Before
    public void setup() {
        board = new BoardImpl(constants.HEIGHT, constants.WIDTH);
        mockPlayer = mock(Player.class);
    }

    @Test
    public void shouldBeAbleToSetAPieceOnTheBoard() {
        board.set(1,1,mockPlayer);
        assertThat(board.get(1,1), is(equalTo(mockPlayer)));
    }

    @Test
    public void shouldKnowTheNumberOfPiecesPlacedOnTheBoard() {
        board.set(0,1,mockPlayer);
        board.set(1,2,mockPlayer);
        assertThat(board.getNumOfPieces(), is(equalTo(2)));
        board.set(2,2,mockPlayer);
        assertThat(board.getNumOfPieces(), is(equalTo(3)));
        board.set(0, 2, mockPlayer);
        assertThat(board.getNumOfPieces(), is(equalTo(4)));
    }

    @Test
    public void shouldReturnFalseIfSpaceIsNotVacant() {
        board.set(0, 0, mockPlayer);
        assertThat(board.isVacant(0,0), is(false));
    }

    @Test
    public void shouldReturnTrueIfSpaceIsVacant() {
        assertThat(board.isVacant(0,0), is(true));
    }

    @Test
    public void shouldBeAbleToGetTheWinnerWhenThereIsAWinnerViaTheTopRow() {
        board.set(0, 0, mockPlayer);
        board.set(0, 1, mockPlayer);
        board.set(0, 2, mockPlayer);
        Assert.assertThat(board.winner(), is(true));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheTopRow() {
        board.set(0, 0, mockPlayer);
        board.set(0, 1, mock(Player.class));
        board.set(0, 2, mockPlayer);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheMiddleRow() {
        board.set(1, 0, mockPlayer);
        board.set(1, 1, mockPlayer);
        board.set(1, 2, mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheMiddleRow() {
        board.set(1, 0, mock(Player.class));
        board.set(1, 1, mockPlayer);
        board.set(1, 2, mockPlayer);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheBottomRow() {
        board.set(2, 0, mockPlayer);
        board.set(2, 1, mockPlayer);
        board.set(2, 2, mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfRowIsFullAndNotMatchingViaTheBottomRow() {
        board.set(2, 0, mockPlayer);
        board.set(2, 1, mockPlayer);
        board.set(2, 2, mock(Player.class));
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLeftColumn() {
        board.set(0, 0, mockPlayer);
        board.set(1, 0, mockPlayer);
        board.set(2, 0, mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheLeftColumn() {
        board.set(0, 0, mock(Player.class));
        board.set(1, 0, mockPlayer);
        board.set(2, 0, mockPlayer);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheMiddleColumn() {
        board.set(0, 1, mockPlayer);
        board.set(1, 1, mockPlayer);
        board.set(2, 1, mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheMiddleColumn() {
        board.set(0, 1, mockPlayer);
        board.set(1, 1, mockPlayer);
        board.set(2, 1, mock(Player.class));
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLastColumn() {
        board.set(0, 2, mockPlayer);
        board.set(1, 2, mockPlayer);
        board.set(2, 2, mockPlayer);
        assertThat(board.winner(), is(equalTo(true)));
    }

    @Test
    public void shouldNotBeOverIfColumnIsFullAndNotMatchingViaTheLastColumn() {
        board.set(0, 2, mockPlayer);
        board.set(1, 2, mock(Player.class));
        board.set(2, 2, mockPlayer);
        assertThat(board.winner(), is(equalTo(false)));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheLeftDiagonal() {
        board.set(0, 0, mockPlayer);
        board.set(1, 1, mockPlayer);
        board.set(2, 2, mockPlayer);
        assertThat(board.winner(), is(true));
    }

    @Test
    public void shouldNotBeOverIfDiagonalIsFullAndNotMatchingViaTheLeftDiagonal() {
        board.set(0, 0, mockPlayer);
        board.set(1, 1, mock(Player.class));
        board.set(2, 2, mockPlayer);
        assertThat(board.winner(), is(false));
    }

    @Test
    public void shouldBeAbleToCheckIfAGameIsOverWhenThereIsAWinnerViaTheRightDiagonal() {
        board.set(0, 2, mockPlayer);
        board.set(1, 1, mockPlayer);
        board.set(2, 0, mockPlayer);
        assertThat(board.winner(), is(true));
    }

    @Test
    public void shouldNotBeOverIfDiagonalIsFullAndNotMatchingViaTheRightDiagonal() {
        board.set(0, 2, mockPlayer);
        board.set(1, 1, mockPlayer);
        board.set(2, 0, mock(Player.class));
        assertThat(board.winner(), is(false));
    }

    @Test
    public void shouldBeAbleToCheckForADraw() {
        Player player2 = mock(Player.class);
        board.set(0, 0, mockPlayer);
        board.set(0, 1, player2);
        board.set(0, 2, mockPlayer);
        board.set(1, 0, mockPlayer);
        board.set(1, 1, player2);
        board.set(1, 2, mockPlayer);
        board.set(2, 0, mock(Player.class));
        board.set(2, 1, mockPlayer);
        board.set(2, 2, mock(Player.class));

        assertThat(board.winner(), is(false));
        assertThat(board.draw(), is(true));
    }
}