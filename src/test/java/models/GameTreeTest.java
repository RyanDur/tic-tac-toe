package models;

import factories.BoardFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

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
        when(board.detectCatsGame(any(Player.class), any(Player.class))).thenReturn(true);
        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getValue(), is(equalTo(constants.DRAW_WEIGHT)));
    }

    @Test
    public void shouldMakeAChildIfNotAWinningBoardAndHasWinningMove() {
        Integer[] winMove = {1, 1};
        board = mockBoard(null, false, Arrays.<Integer[]>asList(winMove));
        StrategyBoard child = mockBoard(human, true, null);
        when(boardFactory.createBoard(anyInt(), any(StrategyBoard.class))).thenReturn(child);

        new GameTreeImpl(board, human, computer, boardFactory);
        verify(boardFactory).createBoard(anyInt(), any(StrategyBoard.class));
    }

    @Test
    public void shouldSumValuesOfChildrenWithParentGettingValueLeadingToAWin() {
        Integer[] winMove = {1, 1};
        StrategyBoard copy = mockBoard(computer, false, null);
        when(boardFactory.createBoard(anyInt(), any(StrategyBoard.class))).thenReturn(copy);
        board = mockBoard(null, false, Arrays.<Integer[]>asList(winMove));

        GameTree node = new GameTreeImpl(board, human, computer, boardFactory);
        assertThat(node.getValue(), is(equalTo(constants.WIN_WEIGHT)));
    }

    private StrategyBoard mockBoard(Player player, boolean won, List<Integer[]> vacancys) {
        StrategyBoard board = mock(StrategyBoard.class);
        when(board.getWinner()).thenReturn(player);
        when(board.detectCatsGame(any(Player.class), any(Player.class))).thenReturn(won);
        when(board.getVacancies()).thenReturn(vacancys);
        return board;
    }
}