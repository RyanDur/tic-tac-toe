package models;

import exceptions.NotVacantException;
import factories.BoardFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.*;

public class GameTest {

    private final String piece = constants.GAME_PIECE_ONE;
    private Game game;
    private Player mockPlayer;

    @Rule
    public ExpectedException exception = ExpectedException.none();
    private Board board;

    @Before
    public void setup() {
        BoardFactory boardFactory = mock(BoardFactory.class);
        board = mock(Board.class);
        when(boardFactory.createBoard(constants.SIDE)).thenReturn(board);
        game = new GameImpl(constants.SIDE, boardFactory);
        mockPlayer = mock(Player.class);
    }

    @Test
    public void shouldBeAbleToSetAPieceOnTheBoard() throws NotVacantException {
        game.set(mockPlayer);
        verify(board).set(anyInt(), anyInt(), anyString());
    }

    @Test
    public void shouldKnowTheNumberOfPiecesPlacedOnTheBoard() throws NotVacantException {
        String[] players = new String[constants.SIDE * constants.SIDE];
        players[0] = piece;
        players[1] = piece;
        players[2] = piece;
        players[3] = piece;
        when(board.getBoard()).thenReturn(players);
        assertThat(game.getNumOfPieces(), is(equalTo(4)));
    }

    @Test
    public void shouldBeAbleToRetrieveACopyOfTheBoard() {
        String[] players = new String[constants.SIDE * constants.SIDE];
        when(board.getBoard()).thenReturn(players);
        assertThat(game.getBoard(), is(equalTo(players)));
    }

    @Test
    public void shouldBeAbleToGetTheWinner() throws NotVacantException {
        game.getWinner();
        verify(board).getWinner();
    }

    @Test
    public void shouldBeAbleToTellIfABoardIsNotFull() {
        when(board.getBoard()).thenReturn(new String[constants.SIDE * constants.SIDE]);
        assertThat(game.full(), is(false));
    }

    @Test
    public void shouldBeAbleToTellIfABoardIsFull() {
        when(board.getBoard()).thenReturn(new String[]{
                piece,
                piece,
                piece,
                piece,
                piece,
                piece,
                piece,
                piece,
                piece});
        assertThat(game.full(), is(true));
    }
}