package controllers;

import factories.BoardFactory;
import factories.GameTreeFactory;
import lang.constants;
import models.GameTree;
import models.Player;
import models.StrategyBoard;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class StrategyGameCtrlTest {

    private StrategyGameCtrl strategyGameCtrl;
    private Player[] board;
    private Player player;
    private BoardFactory boardFactory;
    private GameTreeFactory gameTreeFactory;
    private StrategyBoard strategyBoard;

    @Before
    public void setup() {
        boardFactory = mock(BoardFactory.class);
        gameTreeFactory = mock(GameTreeFactory.class);
        strategyGameCtrl = new StrategyGameCtrlImpl(gameTreeFactory, boardFactory);
        board = new Player[]{};
        strategyBoard = mock(StrategyBoard.class);
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(strategyBoard);
        player = mock(Player.class);
        strategyGameCtrl.setBoard(board);

    }

    @Test
    public void shouldCreateANewStrategyGameWhenSettingTheBoard() {
        verify(boardFactory).createBoard(constants.SIDE, board);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() {
        strategyGameCtrl.findWinningMove(player);
        verify(strategyBoard).winningMove(player);
    }

    @Test
    public void shouldBeAbleToGetTheBestMove() {
        Integer[] move1 = {1, 1};
        Integer[] move2 = {1, 2};
        StrategyBoard board1 = mock(StrategyBoard.class);
        StrategyBoard board2 = mock(StrategyBoard.class);
        GameTree gameTree1 = mock(GameTree.class);
        GameTree gameTree2 = mock(GameTree.class);
        when(gameTree1.getValue()).thenReturn(10);
        when(gameTree2.getValue()).thenReturn(20);

        when(strategyBoard.filterMoves(any(Player.class))).thenReturn(Arrays.asList(move1, move2));
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(board1, board2);
        when(gameTreeFactory.createTree(any(StrategyBoard.class), any(Player.class), any(Player.class), any(BoardFactory.class)))
                .thenReturn(gameTree1, gameTree2);

        assertThat(strategyGameCtrl.getBestMove(player, player).get(), is(equalTo(move2)));
    }
}