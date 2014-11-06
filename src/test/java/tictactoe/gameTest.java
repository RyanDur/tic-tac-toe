package tictactoe;

import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;
import tictactoe.lang.Constants;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;

public class GameTest {

    private Game game;
    private Character[] players;
    private Character pieceOne;
    private Character pieceTwo;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        pieceOne = Constants.GAME_PIECE_ONE;
        pieceTwo = Constants.GAME_PIECE_TWO;
        players = new Character[Constants.SIDE * Constants.SIDE];
        game = new GameImpl();
        game.setup(Constants.SIDE);
    }

    @Test
    public void shouldNotBeAbleToSetAPlayerOnATakenSpot() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        exception.expect(NotVacantException.class);
        game.set(1, 1, pieceOne);
        game.set(1, 1, pieceTwo);
    }

    @Test
    public void shouldNotBeAbleToPlayOtOfTurn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        exception.expect(OutOfTurnException.class);
        game.set(1, 1, pieceOne);
        game.set(1, 1, pieceOne);
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 0, pieceOne);
        game.set(2, 0, pieceTwo);
        game.set(0, 1, pieceOne);
        game.set(2, 1, pieceTwo);
        game.set(0, 2, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 0, pieceOne);
        game.set(2, 0, pieceTwo);
        game.set(0, 1, pieceOne);
        game.set(2, 1, pieceTwo);
        game.set(1, 0, pieceOne);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 0, pieceOne);
        game.set(2, 2, pieceTwo);
        game.set(1, 0, pieceOne);
        game.set(2, 1, pieceTwo);
        game.set(2, 0, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 0, pieceOne);
        game.set(2, 2, pieceTwo);
        game.set(1, 0, pieceOne);
        game.set(2, 1, pieceTwo);
        game.set(1, 1, pieceOne);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(1, 0, pieceOne);
        game.set(2, 2, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(2, 1, pieceTwo);
        game.set(1, 2, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(1, 0, pieceOne);
        game.set(2, 2, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(2, 1, pieceTwo);
        game.set(0, 2, pieceOne);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 1, pieceOne);
        game.set(2, 2, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(2, 0, pieceTwo);
        game.set(2, 1, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 1, pieceOne);
        game.set(2, 2, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(2, 0, pieceTwo);
        game.set(1, 2, pieceOne);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(2, 0, pieceOne);
        game.set(0, 2, pieceTwo);
        game.set(2, 1, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(2, 2, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdRow() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(2, 0, pieceOne);
        game.set(0, 2, pieceTwo);
        game.set(2, 1, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(0, 0, pieceOne);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 2, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 2, pieceOne);
        game.set(0, 0, pieceTwo);
        game.set(2, 2, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdColumn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 2, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 2, pieceOne);
        game.set(0, 0, pieceTwo);
        game.set(1, 1, pieceOne);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheLeftDiagonal() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 0, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(0, 2, pieceTwo);
        game.set(2, 2, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheLeftDiagonal() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 0, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(0, 2, pieceTwo);
        game.set(2, 1, pieceOne);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheRightDiagonal() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 2, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(1, 2, pieceTwo);
        game.set(2, 0, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheRightDiagonal() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        game.set(0, 2, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(1, 2, pieceTwo);
        game.set(2, 2, pieceOne);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldNotBeAbleToPlaceOOnTheBoardFirst() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        exception.expect(OutOfTurnException.class);
        game.set(1, 1, Constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldNotBeAbleToPlaceAPieceXOnTheBoardOutOfTurn() throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        exception.expect(OutOfTurnException.class);
        game.set(1, 1, Constants.GAME_PIECE_ONE);
        game.set(1, 0, Constants.GAME_PIECE_ONE);
    }

    @Test
    public void shouldNotBeAbleToSetAPieceFromOutsideTheBoard() throws OutOfBoundsException, NotVacantException, OutOfTurnException {
        exception.expect(OutOfBoundsException.class);
        game.set(5, 7, pieceOne);
    }

    @Test
    public void shouldNotBeAbleToSetAPieceFromSmallerThanTheBoard() throws OutOfBoundsException, NotVacantException, OutOfTurnException {
        exception.expect(OutOfBoundsException.class);
        game.set(-4, -7, pieceOne);
    }

    @Test
    public void shouldGetAllTheSpacesOfAVacantBoard() {
        assertThat(game.getVacancies().size(), CoreMatchers.is(CoreMatchers.equalTo(players.length)));
    }

    @Test
    public void shouldBeAbleToGetTheVacantSpacesOfABoardBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.set(0, 0, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(0, 2, pieceOne);
        assertThat(game.getVacancies().size(), CoreMatchers.is(CoreMatchers.equalTo(players.length - Constants.SIDE)));
    }

    @Test
    public void shouldBeAbleToGetTheNumberOfPiecesOnAnEmptyBoard() {
        assertThat(game.numOfPieces(), is(equalTo(0)));
    }

    @Test
    public void shouldBeAbleToGetTheNumberOfPiecesOnTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.set(0, 0, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(0, 2, pieceOne);
        assertThat(game.numOfPieces(), is(equalTo(3)));
    }

    @Test
    public void shouldBeAbleToMakeACopyOfTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.set(0, 0, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(0, 2, pieceOne);
        Game copy = game.copy();
        assertThat(copy.getBoard(), is(equalTo(game.getBoard())));
    }

    @Test
    public void shouldBeAbleToClearTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.set(0, 0, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(0, 2, pieceOne);
        assertThat(game.numOfPieces(), is(equalTo(3)));
        game.setup(Constants.SIDE);
        assertThat(game.numOfPieces(), is(equalTo(0)));
    }

    @Test
    public void shouldResetTheWinnerWhenSettingUpTheBoard() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.set(0, 2, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(1, 2, pieceTwo);
        game.set(2, 0, pieceOne);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
        game.setup(Constants.SIDE);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldBeAbleToTellIfAGameIsOverIfThereIsAWinner() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.set(0, 2, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(1, 2, pieceTwo);
        game.set(2, 0, pieceOne);
        assertThat(game.isOver(), is(equalTo(true)));
    }

    @Test
    public void shouldBeAbleToTellIfAGameIsOverIfTheBoardIsFull() throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        game.set(0, 2, pieceOne);
        game.set(0, 1, pieceTwo);
        game.set(1, 1, pieceOne);
        game.set(1, 2, pieceTwo);
        game.set(2, 1, pieceOne);
        game.set(2, 0, pieceTwo);
        game.set(1, 0, pieceOne);
        game.set(0, 0, pieceTwo);
        game.set(2, 2, pieceOne);
        assertThat(game.isOver(), is(equalTo(true)));
    }
}