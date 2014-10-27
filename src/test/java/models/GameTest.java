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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameTest {

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
    public void shouldNotBeAbleToSetAPlayerOnASpotAlreadyTaken() throws NotVacantException {
        exception.expect(NotVacantException.class);
        when(board.get(anyInt(), anyInt())).thenReturn(mockPlayer);
        game.set(mockPlayer);
    }

    @Test
    public void shouldKnowTheNumberOfPiecesPlacedOnTheBoard() throws NotVacantException {
        Player[] players = new Player[constants.SIDE * constants.SIDE];
        players[0] = mockPlayer;
        players[1] = mockPlayer;
        players[2] = mockPlayer;
        players[3] = mockPlayer;
        when(board.getBoard()).thenReturn(players);
        assertThat(game.getNumOfPieces(), is(equalTo(4)));
    }

    @Test
    public void shouldBeAbleToRetrieveACopyOfTheBoard() {
        Player[] players = new Player[constants.SIDE * constants.SIDE];
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
        when(board.getBoard()).thenReturn(new Player[constants.SIDE * constants.SIDE]);
        assertThat(game.full(), is(false));
    }

    @Test
    public void shouldBeAbleToTellIfABoardIsFull() {
        when(board.getBoard()).thenReturn(new Player[]{
                mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class),
                mock(Player.class)});
        assertThat(game.full(), is(true));
    }
}