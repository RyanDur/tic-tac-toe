package tictactoe;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.lang.Constants;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class GameTest {

    private Game game;
    private Character[] players;
    private Character pieceOne;
    private Character pieceTwo;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private ComputerPlayer computer;

    @Before
    public void setup() {
        computer = mock(ComputerPlayer.class);
        pieceOne = Constants.GAME_PIECE_ONE;
        pieceTwo = Constants.GAME_PIECE_TWO;
        players = new Character[Constants.SIDE * Constants.SIDE];
        game = new GameImpl(computer);
        game.setup(null, Constants.SIDE);
    }

    @Test
    public void shouldNotBeAbleToSetAPlayerOnATakenSpot() throws NotVacantException, OutOfBoundsException {
        exception.expect(NotVacantException.class);
        game.set(1, 1);
        game.set(1, 1);
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstRow() throws NotVacantException, OutOfBoundsException {
        game.set(0, 0);
        game.set(2, 0);
        game.set(0, 1);
        game.set(2, 1);
        game.set(0, 2);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstRow() throws NotVacantException, OutOfBoundsException {
        game.set(0, 0);
        game.set(2, 0);
        game.set(0, 1);
        game.set(2, 1);
        game.set(1, 0);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstColumn() throws NotVacantException, OutOfBoundsException {
        game.set(0, 0);
        game.set(2, 2);
        game.set(1, 0);
        game.set(2, 1);
        game.set(2, 0);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstColumn() throws NotVacantException, OutOfBoundsException {
        game.set(0, 0);
        game.set(2, 2);
        game.set(1, 0);
        game.set(2, 1);
        game.set(1, 1);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondRow() throws NotVacantException, OutOfBoundsException {
        game.set(1, 0);
        game.set(2, 2);
        game.set(1, 1);
        game.set(2, 1);
        game.set(1, 2);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondRow() throws NotVacantException, OutOfBoundsException {
        game.set(1, 0);
        game.set(2, 2);
        game.set(1, 1);
        game.set(2, 1);
        game.set(0, 2);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondColumn() throws NotVacantException, OutOfBoundsException {
        game.set(0, 1);
        game.set(2, 2);
        game.set(1, 1);
        game.set(2, 0);
        game.set(2, 1);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondColumn() throws NotVacantException, OutOfBoundsException {
        game.set(0, 1);
        game.set(2, 2);
        game.set(1, 1);
        game.set(2, 0);
        game.set(1, 2);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdRow() throws NotVacantException, OutOfBoundsException {
        game.set(2, 0);
        game.set(0, 2);
        game.set(2, 1);
        game.set(0, 1);
        game.set(2, 2);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdRow() throws NotVacantException, OutOfBoundsException {
        game.set(2, 0);
        game.set(0, 2);
        game.set(2, 1);
        game.set(0, 1);
        game.set(0, 0);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdColumn() throws NotVacantException, OutOfBoundsException {
        game.set(0, 2);
        game.set(0, 1);
        game.set(1, 2);
        game.set(0, 0);
        game.set(2, 2);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdColumn() throws NotVacantException, OutOfBoundsException {
        game.set(0, 2);
        game.set(0, 1);
        game.set(1, 2);
        game.set(0, 0);
        game.set(1, 1);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheLeftDiagonal() throws NotVacantException, OutOfBoundsException {
        game.set(0, 0);
        game.set(0, 1);
        game.set(1, 1);
        game.set(0, 2);
        game.set(2, 2);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheLeftDiagonal() throws NotVacantException, OutOfBoundsException {
        game.set(0, 0);
        game.set(0, 1);
        game.set(1, 1);
        game.set(0, 2);
        game.set(2, 1);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheRightDiagonal() throws NotVacantException, OutOfBoundsException {
        game.set(0, 2);
        game.set(0, 1);
        game.set(1, 1);
        game.set(1, 2);
        game.set(2, 0);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheRightDiagonal() throws NotVacantException, OutOfBoundsException {
        game.set(0, 2);
        game.set(0, 1);
        game.set(1, 1);
        game.set(1, 2);
        game.set(2, 2);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldNotBeAbleToSetAPieceFromOutsideTheBoard() throws OutOfBoundsException, NotVacantException {
        exception.expect(OutOfBoundsException.class);
        game.set(5, 7);
    }

    @Test
    public void shouldNotBeAbleToSetAPieceFromSmallerThanTheBoard() throws OutOfBoundsException, NotVacantException {
        exception.expect(OutOfBoundsException.class);
        game.set(-4, -7);
    }

    @Test
    public void shouldGetAllTheSpacesOfAVacantBoard() {
        assertThat(game.getVacancies().size(), CoreMatchers.is(CoreMatchers.equalTo(players.length)));
    }

    @Test
    public void shouldBeAbleToGetTheVacantSpacesOfABoardBoard() throws OutOfBoundsException, NotVacantException {
        game.set(0, 0);
        game.set(0, 1);
        game.set(0, 2);
        assertThat(game.getVacancies().size(), CoreMatchers.is(CoreMatchers.equalTo(players.length - Constants.SIDE)));
    }

    @Test
    public void shouldBeAbleToMakeACopyOfTheBoard() throws OutOfBoundsException, NotVacantException {
        game.set(0, 0);
        game.set(0, 1);
        game.set(0, 2);
        Game copy = game.copy();
        assertThat(copy.getBoard(), is(equalTo(game.getBoard())));
    }

    @Test
    public void shouldBeAbleToClearTheBoard() throws OutOfBoundsException, NotVacantException {
        game.set(0, 0);
        game.set(0, 1);
        game.set(0, 2);
        assertThat(game.getVacancies().size(), is(equalTo(6)));
        game.setup(null, Constants.SIDE);
        assertThat(game.getVacancies().size(), is(equalTo(9)));
    }

    @Test
    public void shouldResetTheWinnerWhenSettingUpTheBoard() throws OutOfBoundsException, NotVacantException {
        when(computer.getMove(any(Game.class))).thenReturn(Arrays.asList(1, 2));
        game.set(0, 2);
        game.set(0, 1);
        game.set(1, 1);
        game.set(1, 2);
        game.set(2, 0);
        assertThat(game.getWinner(), is(equalTo(pieceOne)));
        game.setup(pieceOne, Constants.SIDE);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void shouldBeAbleToTellIfAGameIsOverIfThereIsAWinner() throws OutOfBoundsException, NotVacantException {
        game.set(0, 2);
        game.set(0, 1);
        game.set(1, 1);
        game.set(1, 2);
        game.set(2, 0);
        assertThat(game.isOver(), is(equalTo(true)));
    }

    @Test
    public void shouldBeAbleToTellIfAGameIsOverIfTheBoardIsFull() throws OutOfBoundsException, NotVacantException {
        game.set(0, 2);
        game.set(0, 1);
        game.set(1, 1);
        game.set(1, 2);
        game.set(2, 1);
        game.set(2, 0);
        game.set(1, 0);
        game.set(0, 0);
        game.set(2, 2);
        assertThat(game.isOver(), is(equalTo(true)));
    }

    @Test
    public void shouldBeAbleToAllowTheComputerToPlayFirstIfItIsX() throws NotVacantException, OutOfBoundsException {
        when(computer.getMove(any(Game.class))).thenReturn(Arrays.asList(1, 2));
        game.setup(pieceOne, Constants.SIDE);
        verify(computer).getMove(any(Game.class));
    }

    @Test
    public void shouldNotBeAbleToAllowTheComputerToPlayFirstIfItIsO() throws NotVacantException, OutOfBoundsException {
        game.setup(pieceTwo, Constants.SIDE);
        verify(computer, never()).getMove(any(Game.class));
    }
}