package models;

import exceptions.NotVacantException;
import factories.BoardFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class GameTreeTest {

    private final String pieceOne = constants.GAME_PIECE_ONE;
    private StrategyBoard board;
    private Player computer;
    private Player human;
    private BoardFactory boardFactory;
    private List<Integer[]> vacancies;
    private Integer[] winMove = new Integer[]{1, 1};

    @Before
    public void setup() {
        board = mock(StrategyBoard.class);
        computer = mock(ComputerPlayer.class);
        human = mock(Player.class);
        boardFactory = mock(BoardFactory.class);
        vacancies = Arrays.<Integer[]>asList(winMove);
        when(board.getBoard()).thenReturn(new String[]{});
    }

    @Test
    public void shouldBeAbleToGetAValueForAWinningNode() {
        when(board.getWinner()).thenReturn(pieceOne);
        GameTree node = new GameTreeImpl(board, computer, human, boardFactory);
        assertThat(node.getMaxValue(), is(equalTo(constants.WIN_WEIGHT)));
    }

    @Test
    public void shouldBeAbleToGetANegativeValueForAWinningNodeAndNotAComputerPlayer() {
        when(board.getWinner()).thenReturn(pieceOne);
        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getMinValue(), is(equalTo(constants.LOSE_WEIGHT)));
    }

    @Test
    public void shouldBeAbleToGetA0ValueForACatsGameNode() {
        when(board.getVacancies()).thenReturn(new ArrayList<>());
        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getMaxValue(), is(equalTo(constants.DRAW_WEIGHT)));
    }

    @Test
    public void shouldMakeAChildIfNotAWinningBoardAndHasWinningMove() {
        Integer[] winMove = {1, 1};
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove), Optional.of(winMove));
        StrategyBoard child = mockBoard(pieceOne, new ArrayList<>(), Optional.of(winMove));
        when(boardFactory.createBoard(anyInt(), any(String[].class))).thenReturn(child);
        when(board.winningMove(any(Player.class))).thenReturn(Optional.of(winMove));

        new GameTreeImpl(board, human, computer, boardFactory);
        verify(boardFactory).createBoard(anyInt(), any(String[].class));
    }

    @Test
    public void shouldSumValuesOfChildrenWithParentGettingValueLeadingToAWin() {
        Integer[] winMove = {1, 1};
        StrategyBoard copy = mockBoard(pieceOne, null, Optional.of(winMove));
        when(boardFactory.createBoard(anyInt(), any(String[].class))).thenReturn(copy);
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove), Optional.of(winMove));
        when(board.winningMove(any(Player.class))).thenReturn(Optional.of(winMove));

        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getMaxValue(), is(equalTo(constants.WIN_WEIGHT)));
    }


    @Test
    public void shouldGetWinningMoveOfSecondPlayerForChild() throws NotVacantException {
        Integer[] winMove = {1, 1};
        StrategyBoard copy = mockBoard(pieceOne, null, Optional.of(winMove));
        when(boardFactory.createBoard(anyInt(), any(String[].class))).thenReturn(copy);
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove), Optional.of(winMove));
        when(board.winningMove(computer)).thenReturn(Optional.of(winMove));

        new GameTreeImpl(board, human, computer, boardFactory);
        verify(board).winningMove(computer);
    }

    @Test
    public void shouldBeAbleToGetTheMaxValueOfAGame() {
        when(board.getWinner()).thenReturn(pieceOne);
        GameTree node = new GameTreeImpl(board, computer, human, boardFactory);
        assertThat(node.getMaxValue(), is(equalTo(constants.WIN_WEIGHT)));
    }

    @Test
    public void shouldBeAbleToGetTheMinValueOfAGame() {
        when(board.getWinner()).thenReturn(pieceOne);
        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getMinValue(), is(equalTo(constants.LOSE_WEIGHT)));
    }

    @Test
    public void shouldSumMaxValuesOfChildrenWithParentGettingValueLeadingToAWin() {
        Integer[] winMove = {1, 1};
        StrategyBoard copy = mockBoard(pieceOne, null, Optional.of(winMove));
        when(boardFactory.createBoard(anyInt(), any(String[].class))).thenReturn(copy);
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove), Optional.of(winMove));
        when(board.winningMove(any(Player.class))).thenReturn(Optional.of(winMove));

        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getMaxValue(), is(equalTo(constants.WIN_WEIGHT)));
    }

    @Test
    public void shouldSumMinValuesOfChildrenWithParentGettingValueLeadingToAWin() {
        StrategyBoard copy = mockBoard(pieceOne, null, Optional.empty());
        when(boardFactory.createBoard(anyInt(), any(String[].class))).thenReturn(copy);
        board = mockBoard(null, vacancies, Optional.of(winMove));

        GameTree node = new GameTreeImpl(board, computer, human, boardFactory);
        assertThat(node.getMinValue(), is(equalTo(constants.LOSE_WEIGHT)));
    }

    private StrategyBoard mockBoard(String player, List<Integer[]> vacancies, Optional<Integer[]> of) {
        StrategyBoard board = mock(StrategyBoard.class);
        when(board.getWinner()).thenReturn(player);
        when(board.getVacancies()).thenReturn(vacancies);
        when(board.winningMove(any(Player.class))).thenReturn(of);
        return board;
    }
}