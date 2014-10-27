package controllers;

import lang.constants;
import models.GameTree;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StrategyCtrlTest {

    private StrategyCtrl strategyCtrl;
    private Player[] board;
    private Player player;
    private StrategyGameCtrl strategyGameCtrl;

    @Before
    public void setup() {
        strategyGameCtrl = mock(StrategyGameCtrl.class);
        strategyCtrl = new StrategyCtrlImpl(strategyGameCtrl);
        board = new Player[]{};
        player = mock(Player.class);
        strategyCtrl.setBoard(board);
    }

    @Test
    public void shouldCreateANewStrategyGameWhenSettingTheBoard() {
        verify(strategyGameCtrl).setBoard(constants.SIDE, board);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() {
        strategyCtrl.findWinningMove(player);
        verify(strategyGameCtrl).winningMove(player);
    }

    @Test
    public void shouldBeAbleToGetTheBestMove() {
        Integer[] move1 = {1,2};
        Integer[] move2 = {1,1};
        List<Integer[]> moves = Arrays.asList(move1,move2);
        GameTree gameTree1 = mock(GameTree.class);
        GameTree gameTree2 = mock(GameTree.class);
        when(gameTree1.getValue()).thenReturn(10);
        when(gameTree2.getValue()).thenReturn(20);
        when(strategyGameCtrl.filterMoves(any(Player.class))).thenReturn(moves);
        when(strategyGameCtrl.getTree(any(Player.class), any(Player.class), any(Integer[].class))).thenReturn(gameTree1,gameTree2);

        assertThat(strategyCtrl.getBestMove(player, player).get(), is(equalTo(move2)));
    }
}