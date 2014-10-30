package models;

import lang.constants;
import org.junit.Before;
import org.junit.Test;
import tictactoe.Board;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class StrategyGameCtrlTest {

    private StrategyGameCtrl strategyGameCtrl;
    private Board board;
    private String player;
    private StrategyBoardCtrl strategyBoardCtrl;

    @Before
    public void setup() {
        strategyBoardCtrl = mock(StrategyBoardCtrl.class);
        strategyGameCtrl = new StrategyGameCtrlImpl(strategyBoardCtrl);
        board = mock(Board.class);
        player = constants.GAME_PIECE_ONE;
        strategyGameCtrl.setBoard(board);
    }

    @Test
    public void shouldCreateANewStrategyGameWhenSettingTheBoard() {
        verify(strategyBoardCtrl).setBoard(board);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() {
        strategyGameCtrl.findWinningMove(player);
        verify(strategyBoardCtrl).winningMove(player);
    }

    @Test
    public void shouldBeAbleToGetTheBestMove() {
        Integer[] move1 = {1,2};
        Integer[] move2 = {1,1};
        List<Integer[]> moves = Arrays.asList(move1,move2);
        GameTree gameTree1 = mock(GameTree.class);
        GameTree gameTree2 = mock(GameTree.class);
        when(gameTree1.getMaxValue()).thenReturn(10);
        when(gameTree2.getMaxValue()).thenReturn(20);
        when(strategyBoardCtrl.filterMoves(any(String.class))).thenReturn(moves);
        when(strategyBoardCtrl.getTree(any(String.class), any(String.class), any(Integer[].class))).thenReturn(gameTree1,gameTree2);

        assertThat(strategyGameCtrl.getBestMove(player, player).get(), is(equalTo(move2)));
    }
}