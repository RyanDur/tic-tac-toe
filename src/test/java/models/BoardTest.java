package models;

import lang.constants;
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
        board = new BoardImpl(constants.WIDTH, constants.HEIGHT);
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
}