package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import lang.constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class BoardTest {

    private Board board;
    private String[] players;
    private String pieceOne;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private String pieceTwo;

    @Before
    public void setup() {
        pieceOne = constants.GAME_PIECE_ONE;
        pieceTwo = constants.GAME_PIECE_TWO;
        players = new String[constants.SIDE * constants.SIDE];
        board = new BoardImpl(constants.SIDE);
    }

    @Test
    public void shouldBeAbleToGetASetPiece() throws NotVacantException, OutOfTurnException {
        board.set(1, 1, pieceOne);
        assertThat(board.get(1,1), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldNotBeAbleToSetAPlayerOnATakenSpot() throws NotVacantException, OutOfTurnException {
        exception.expect(NotVacantException.class);
        board.set(1, 1, pieceOne);
        board.set(1, 1, pieceTwo);
    }

    @Test
    public void shouldNotBeAbleToPlayOtOfTurn() throws NotVacantException, OutOfTurnException {
        exception.expect(OutOfTurnException.class);
        board.set(1, 1, pieceOne);
        board.set(1, 1, pieceOne);
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstRow() throws NotVacantException, OutOfTurnException {
        players[0] = pieceOne;
        players[1] = pieceOne;
        board.setBoard(players);
        board.set(0, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstRow() throws NotVacantException, OutOfTurnException {
        players[0] = pieceOne;
        players[2] = pieceOne;
        board.setBoard(players);
        board.set(1, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnFirstColumn() throws NotVacantException, OutOfTurnException {
        players[0] = pieceOne;
        players[3] = pieceOne;
        board.setBoard(players);
        board.set(2, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnFirstColumn() throws NotVacantException, OutOfTurnException {
        players[0] = pieceOne;
        players[2] = pieceOne;
        board.setBoard(players);
        board.set(1, 1, pieceOne);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondRow() throws NotVacantException, OutOfTurnException {
        players[4] = pieceOne;
        players[5] = pieceOne;
        board.setBoard(players);
        board.set(1, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondRow() throws NotVacantException, OutOfTurnException {
        players[4] = pieceOne;
        players[5] = pieceOne;
        board.setBoard(players);
        board.set(0, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnSecondColumn() throws NotVacantException, OutOfTurnException {
        players[1] = pieceOne;
        players[7] = pieceOne;
        board.setBoard(players);
        board.set(1, 1, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnSecondColumn() throws NotVacantException, OutOfTurnException {
        players[4] = pieceOne;
        players[7] = pieceOne;
        board.setBoard(players);
        board.set(0, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdRow() throws NotVacantException, OutOfTurnException {
        players[6] = pieceOne;
        players[7] = pieceOne;
        board.setBoard(players);
        board.set(2, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdRow() throws NotVacantException, OutOfTurnException {
        players[6] = pieceOne;
        players[7] = pieceOne;
        board.setBoard(players);
        board.set(0, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnThirdColumn() throws NotVacantException, OutOfTurnException {
        players[5] = pieceOne;
        players[8] = pieceOne;
        board.setBoard(players);
        board.set(0, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnThirdColumn() throws NotVacantException, OutOfTurnException {
        players[2] = pieceOne;
        players[8] = pieceOne;
        board.setBoard(players);
        board.set(0, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheLeftDiagonal() throws NotVacantException, OutOfTurnException {
        players[0] = pieceOne;
        players[4] = pieceOne;
        board.setBoard(players);
        board.set(2, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheLeftDiagonal() throws NotVacantException, OutOfTurnException {
        players[4] = pieceOne;
        players[8] = pieceOne;
        board.setBoard(players);
        board.set(1, 0, pieceOne);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsWinnerOnTheRightDiagonal() throws NotVacantException, OutOfTurnException {
        players[2] = pieceOne;
        players[6] = pieceOne;
        board.setBoard(players);
        board.set(1, 1, pieceOne);
        assertThat(board.getWinner(), is(equalTo(pieceOne)));
    }

    @Test
    public void shouldCheckBoardIfPlayerIsNotWinnerOnTheRightDiagonal() throws NotVacantException, OutOfTurnException {
        players[2] = pieceOne;
        players[4] = pieceOne;
        board.setBoard(players);
        board.set(2, 2, pieceOne);
        assertThat(board.getWinner(), is(equalTo(null)));
    }

    @Test
    public void shouldBeAbleToTellIfTheBoardIsFull() {
        players[0] = pieceOne;
        players[1] = pieceOne;
        players[2] = pieceOne;
        players[3] = pieceOne;
        players[4] = pieceOne;
        players[5] = pieceOne;
        players[6] = pieceOne;
        players[7] = pieceOne;
        players[8] = pieceOne;
        board.setBoard(players);
        assertThat(board.full(), is(true));
    }

    @Test
    public void shouldBeAbleToTellIfTheBoardIsNotFull() {
        players[0] = pieceOne;
        players[1] = pieceOne;
        players[2] = pieceOne;
        players[7] = pieceOne;
        players[8] = pieceOne;
        board.setBoard(players);
        assertThat(board.full(), is(false));
    }

    @Test
    public void shouldBeAbleToGetTheNumberOfPiecesOnTheBoard() {
        players[0] = pieceOne;
        players[1] = pieceOne;
        players[2] = pieceOne;
        players[7] = pieceOne;
        players[8] = pieceOne;
        board.setBoard(players);
        assertThat(board.getNumberOfPieces(), is(equalTo(5)));
    }

    @Test
    public void shouldBeAbleToGetTheNumberOfPiecesForAnEmptyBoard() {
        assertThat(board.getNumberOfPieces(), is(equalTo(0)));
    }

    @Test
    public void shouldNotBeAbleToPlaceOOnTheBoardFirst() throws NotVacantException, OutOfTurnException {
        exception.expect(OutOfTurnException.class);
        board.set(1,1,constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldNotBeAbleToPlaceAPieceXOnTheBoardOutOfTurn() throws NotVacantException, OutOfTurnException {
        exception.expect(OutOfTurnException.class);
        board.set(1,1,constants.GAME_PIECE_ONE);
        board.set(1,0,constants.GAME_PIECE_ONE);
    }
}