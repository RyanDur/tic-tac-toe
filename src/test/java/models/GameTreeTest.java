package models;

import exceptions.NotVacantException;
import factories.BoardFactory;
import factories.BoardFactoryImpl;
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
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class GameTreeTest {

    private StrategyBoard board;
    private Player computer;
    private Player human;
    private BoardFactory boardFactory;

    @Before
    public void setup() {
        board = mock(StrategyBoard.class);
        computer = mock(ComputerPlayer.class);
        human = mock(Player.class);
        boardFactory = mock(BoardFactory.class);
        when(board.getBoard()).thenReturn(new Player[]{});
    }

    @Test
    public void shouldBeAbleToGetAValueForAWinningNode() {
        when(board.getWinner()).thenReturn(computer);
        GameTree node = new GameTreeImpl(board, computer, human, boardFactory);
        assertThat(node.getValue(), is(equalTo(constants.WIN_WEIGHT)));
    }

    @Test
    public void shouldBeAbleToGetANegativeValueForAWinningNodeAndNotAComputerPlayer() {
        when(board.getWinner()).thenReturn(human);
        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getValue(), is(equalTo(constants.LOSE_WEIGHT)));
    }

    @Test
    public void shouldBeAbleToGetA0ValueForACatsGameNode() {
        when(board.getVacancies()).thenReturn(new ArrayList<>());
        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getValue(), is(equalTo(constants.DRAW_WEIGHT)));
    }

    @Test
    public void shouldMakeAChildIfNotAWinningBoardAndHasWinningMove() {
        Integer[] winMove = {1, 1};
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove));
        StrategyBoard child = mockBoard(human, new ArrayList<>());
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(child);

        new GameTreeImpl(board, human, computer, boardFactory);
        verify(boardFactory).createBoard(anyInt(), any(Player[].class));
    }

    @Test
    public void shouldSumValuesOfChildrenWithParentGettingValueLeadingToAWin() {
        Integer[] winMove = {1, 1};
        StrategyBoard copy = mockBoard(computer, null);
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(copy);
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove));

        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getValue(), is(equalTo(constants.WIN_WEIGHT)));
    }


    @Test
    public void should() throws NotVacantException {
        Player[] players = new Player[constants.SIDE * constants.SIDE];
        BoardFactory boardFactory = new BoardFactoryImpl();
        StrategyBoard strategyBoard = boardFactory.createBoard(constants.SIDE, players);
        strategyBoard.set(0,2,computer);

        GameTree gameTree = new GameTreeImpl(strategyBoard, computer, human, boardFactory);
        System.out.println(gameTree.getValue());
    }

    @Test
    public void shouldGetWinningMoveOfFirstPlayerForChild() throws NotVacantException {
        Integer[] winMove = {1, 1};
        StrategyBoard copy = mockBoard(computer, null);
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(copy);
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove));
        new GameTreeImpl(board, human, computer, boardFactory);

        verify(board).winningMove(human);
    }

    @Test
    public void shouldGetWinningMoveOfSecondPlayerForChild() throws NotVacantException {
        Integer[] winMove = {1, 1};
        StrategyBoard copy = mockBoard(computer, null);
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(copy);
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove));
        new GameTreeImpl(board, human, computer, boardFactory);

        verify(board).winningMove(computer);
    }

    @Test
    public void shouldGetBestMovesOfFirstPlayerForChildren() throws NotVacantException {
        Integer[] winMove = {1, 1};
        StrategyBoard copy = mockBoard(computer, null);
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(copy);
        board = mockBoard(null, Arrays.<Integer[]>asList(winMove));
        new GameTreeImpl(board, human, computer, boardFactory);

        verify(board).filterMoves(human);
    }

    private StrategyBoard mockBoard(Player player, List<Integer[]> vacancies) {
        StrategyBoard board = mock(StrategyBoard.class);
        when(board.getWinner()).thenReturn(player);
        when(board.getVacancies()).thenReturn(vacancies);
        when(board.winningMove(any(Player.class))).thenReturn(Optional.empty());
        return board;
    }
}