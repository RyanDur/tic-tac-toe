package models;

import factories.BoardFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

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
    }

    @Test
    public void shouldBeAbleToGetAValueForAWinningNode() {
//        when(board.isWinner(anyInt(), anyInt(), any(Player.class))).thenReturn(true);
//        when(board.lastMove()).thenReturn(new Integer[]{1, 2});
//        GameTree node = new GameTreeImpl(board, computer, human, boardFactory);
//        assertThat(node.getValue(), is(equalTo(constants.WIN_WEIGHT)));
    }

    @Test
    public void shouldBeAbleToGetANegativeValueForAWinningNodeAndNotAComputerPlayer() {
//        when(board.isWinner(anyInt(), anyInt(), any(Player.class))).thenReturn(true);
//        when(board.lastMove()).thenReturn(new Integer[]{1,2});
//        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
//        assertThat(node.getValue(), is(equalTo(constants.LOSE_WEIGHT)));
    }

    @Test
    public void shouldBeAbleToGetA0ValueForACatsGameNode() {
//        when(board.isWinner(anyInt(), anyInt(), any(Player.class))).thenReturn(false);
//        when(board.detectCatsGame()).thenReturn(true);
//        when(board.lastMove()).thenReturn(new Integer[]{1,2});
//        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
//        assertThat(node.getValue(), is(equalTo(constants.DRAW_WEIGHT)));
    }

    @Test
    public void shouldMakeAChildIfNotAWinningBoardAndHasWinningMove() {
        Integer[] winMove = {1, 1};
        Board copy = mockBoard(true, false, null, null, null, null);
        when(boardFactory.createBoard(anyInt())).thenReturn(copy);
        board = mockBoard(false, false, Optional.of(winMove), null, null, null);

        new GameTreeImpl(board, human, computer, boardFactory);
        verify(boardFactory).createBoard(constants.SIDE);
    }

    @Test
    public void shouldSumValuesOfChildrenWithParentGettingValueLeadingToAWin() {
        Integer[] winMove = {1, 1};
        Board copy = mockBoard(true, false, null, null, null, null);
        board = mockBoard(false, false, Optional.of(winMove), null, null, null);
        when(boardFactory.createBoard(anyInt())).thenReturn(copy);

        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getValue(), is(equalTo(constants.WIN_WEIGHT)));
    }

    @Test
    public void shouldSumValuesOfChildrenWithParentGettingValueLeadingToADraw() {
        Integer[] winMove = {1, 1};
        Board copy = mockBoard(false, true, null, null, null, null);
        when(boardFactory.createBoard(anyInt())).thenReturn(copy);
        board = mockBoard(false, false, Optional.empty(), Optional.of(winMove), null, null);

//        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
//        assertThat(node.getValue(), is(equalTo(constants.DRAW_WEIGHT)));
//        verify(board).winningMove(human);
    }

    @Test
    public void shouldSumValuesOfChildrenWithAllChildrenOfChildren() {
        Board copy1 = mockBoard(false, true, null, null, null, null);
        Board copy2 = mockBoard(true, false, null, null, null, null);
        Stream<Board> stream = Arrays.stream(new Board[]{copy1, copy2});
        board = mockBoard(false, false, Optional.empty(), Optional.empty(), null, stream);
        when(boardFactory.createBoard(anyInt())).thenReturn(copy1, copy2);

//        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
//        verify(board).filterMoves(human);
//        assertThat(node.getValue(), is(equalTo(constants.WIN_WEIGHT)));
    }

    @Test
    public void shouldMakeChildrenBasedOnVacanciesLeftIfNoDiscernibleWinMoveIsPresent() {
//        board = mockBoard(false, false, Optional.empty(), Optional.empty(), Stream.empty(), Stream.empty());
//        Board copy2 = mockBoard(true, false, null, null, null, null);
//        when(boardFactory.createBoard(anyInt())).thenReturn(copy2);
//        Integer[] move = {1, 1};
//        when(board.getVacancies()).thenReturn(Arrays.<Integer[]>asList(move));
//        new GameTreeImpl(board, human, computer, boardFactory);
//        verify(board).getVacancies();
    }

    @Test
    public void shouldTakeIntoCountAllThePossibleLosingMovesIfNowPossibleWinningMoves() {
//        Board copy1 = mockBoard(false, true, null, null, null, null);
//        Board copy2 = mockBoard(true, false, null, null, null, null);
//        Stream<Board> stream = Arrays.stream(new Board[]{copy1, copy2});
//        board = mockBoard(false, false, Optional.empty(), Optional.empty(), Stream.empty(), stream);
//        when(boardFactory.createBoard(anyInt())).thenReturn(copy2);
//        Integer[] move = {1, 1};
//        when(board.getVacancies()).thenReturn(Arrays.<Integer[]>asList(move));
//        new GameTreeImpl(board, human, computer, boardFactory);
//        verify(board).filterMoves(human);
    }

    @Test
    public void shouldboardFacto() {
//        BoardFactory boardFactory1 = new BoardFactoryImpl();
//        StrategyBoard board1 = new BoardImpl(constants.SIDE);
//        board1.set(1,1,computer);
//        board1.set(0,0, human);
//        board1.set(2,2, computer);
//        board1.set(2,1, human);
//
//        board1.set(0,1, computer);
//        GameTree gameTree = new GameTreeImpl(board1, computer, human, boardFactory1);
//        System.out.println(gameTree.getValue());
    }

    private StrategyBoard mockBoard(boolean winner, boolean catsGame, Optional<Integer[]> winingMove, Optional<Integer[]> losingMove, Stream<Board> stream1, Stream<Board> stream2) {
        StrategyBoard board = mock(StrategyBoard.class);
//        when(board.lastMove()).thenReturn(new Integer[]{1,2});
//        when(board.isWinner(anyInt(), anyInt(), any(Player.class))).thenReturn(winner);
//        when(board.detectCatsGame()).thenReturn(catsGame);
//        when(board.getBoard()).thenReturn(new Player[]{});
//        when(board.winningMove(computer)).thenReturn(winingMove);
//        when(board.winningMove(human)).thenReturn(losingMove);
//        when(board.filterMoves(computer)).thenReturn(stream1);
//        when(board.filterMoves(human)).thenReturn(stream2);
        return board;
    }
}