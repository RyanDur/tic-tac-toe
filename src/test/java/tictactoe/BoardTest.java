package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

public class BoardTest {

    private Board board;
    private String[] players;
    private String pieceOne;
    private String pieceTwo;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        pieceOne = constants.GAME_PIECE_ONE;
        pieceTwo = constants.GAME_PIECE_TWO;
        players = new String[constants.SIDE * constants.SIDE];
        board = new BoardImpl();
        board.setup(constants.SIDE);
    }

    @Test
    public void shouldNotBeAbleToSetAPlayerOnATakenSpot() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        exception.expect(NotVacantException.class);
        board.set(1, 1, pieceOne);
        board.set(1, 1, pieceTwo);
    }

    @Test
    public void shouldNotBeAbleToPlayOtOfTurn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        exception.expect(OutOfTurnException.class);
        board.set(1, 1, pieceOne);
        board.set(1, 1, pieceOne);
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 0, pieceOne);
        board.set(2, 0, pieceTwo);
        board.set(0, 1, pieceOne);
        board.set(2, 1, pieceTwo);
        board.set(0, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 0, pieceOne);
        board.set(2, 0, pieceTwo);
        board.set(0, 1, pieceOne);
        board.set(2, 1, pieceTwo);
        board.set(1, 0, pieceOne);
        assertThat(board.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 0, pieceOne);
        board.set(2, 2, pieceTwo);
        board.set(1, 0, pieceOne);
        board.set(2, 1, pieceTwo);
        board.set(2, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 0, pieceOne);
        board.set(2, 2, pieceTwo);
        board.set(1, 0, pieceOne);
        board.set(2, 1, pieceTwo);
        board.set(1, 1, pieceOne);
        assertThat(board.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(1, 0, pieceOne);
        board.set(2, 2, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(2, 1, pieceTwo);
        board.set(1, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(1, 0, pieceOne);
        board.set(2, 2, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(2, 1, pieceTwo);
        board.set(0, 2, pieceOne);
        assertThat(board.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 1, pieceOne);
        board.set(2, 2, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(2, 0, pieceTwo);
        board.set(2, 1, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 1, pieceOne);
        board.set(2, 2, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(2, 0, pieceTwo);
        board.set(1, 2, pieceOne);
        assertThat(board.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(2, 0, pieceOne);
        board.set(0, 2, pieceTwo);
        board.set(2, 1, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(2, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(2, 0, pieceOne);
        board.set(0, 2, pieceTwo);
        board.set(2, 1, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(0, 0, pieceOne);
        assertThat(board.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 2, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(1, 2, pieceOne);
        board.set(0, 0, pieceTwo);
        board.set(2, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 2, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(1, 2, pieceOne);
        board.set(0, 0, pieceTwo);
        board.set(1, 1, pieceOne);
        assertThat(board.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheLeftDiagonal() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 0, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(0, 2, pieceTwo);
        board.set(2, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheLeftDiagonal() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 0, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(0, 2, pieceTwo);
        board.set(2, 1, pieceOne);
        assertThat(board.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheRightDiagonal() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 2, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(1, 2, pieceTwo);
        board.set(2, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheRightDiagonal() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        board.set(0, 2, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(1, 2, pieceTwo);
        board.set(2, 2, pieceOne);
        assertThat(board.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldNotBeAbleToPlaceOOnTheBoardFirst() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        exception.expect(OutOfTurnException.class);
        board.set(1, 1, constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldNotBeAbleToPlaceAPieceXOnTheBoardOutOfTurn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        exception.expect(OutOfTurnException.class);
        board.set(1, 1, constants.GAME_PIECE_ONE);
        board.set(1, 0, constants.GAME_PIECE_ONE);
    }

    @Test
    public void shouldNotBeAbleToSetAPieceFromOutsideTheBoard() throws OutOfBoundsException, NotVacantException, OutOfTurnException {
        exception.expect(OutOfBoundsException.class);
        board.set(5, 7, pieceOne);
    }

    @Test
    public void shouldNotBeAbleToSetAPieceFromSmallerThanTheBoard() throws OutOfBoundsException, NotVacantException, OutOfTurnException {
        exception.expect(OutOfBoundsException.class);
        board.set(-4, -7, pieceOne);
    }

    @Test
    public void shouldGetAllTheSpacesOfAVacantBoard() {
        assertThat(board.getVacancies().size(), CoreMatchers.is(CoreMatchers.equalTo(players.length)));
    }

    @Test
    public void shouldBeAbleToGetTheVacantSpacesOfABoardBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        board.set(0, 0, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(0, 2, pieceOne);
        assertThat(board.getVacancies().size(), CoreMatchers.is(CoreMatchers.equalTo(players.length - constants.SIDE)));
    }

    @Test
    public void shouldBeAbleToGetTheNumberOfPiecesOnAnEmptyBoard() {
        assertThat(board.numOfPieces(), is(equalTo(0)));
    }

    @Test
    public void shouldBeAbleToGetTheNumberOfPiecesOnTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        board.set(0, 0, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(0, 2, pieceOne);
        assertThat(board.numOfPieces(), is(equalTo(3)));
    }

    @Test
    public void shouldBeAbleToMakeACopyOfTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        board.set(0, 0, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(0, 2, pieceOne);
        Board copy = board.copy();
        assertThat(copy.getBoard(), is(equalTo(board.getBoard())));
    }

    @Test
    public void shouldBeAbleToClearTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        board.set(0, 0, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(0, 2, pieceOne);
        assertThat(board.numOfPieces(), is(equalTo(3)));
        board.setup(constants.SIDE);
        assertThat(board.numOfPieces(), is(equalTo(0)));
    }

    @Test
    public void shouldResetTheWinnerWhenSettingUpTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        board.set(0, 2, pieceOne);
        board.set(0, 1, pieceTwo);
        board.set(1, 1, pieceOne);
        board.set(1, 2, pieceTwo);
        board.set(2, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
        board.setup(constants.SIDE);
        assertThat(board.getWinner(), is(nullValue()));
    }
}