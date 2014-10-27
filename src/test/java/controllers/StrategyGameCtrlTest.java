package controllers;

import factories.BoardFactory;
import factories.GameTreeFactory;
import lang.constants;
import models.GameTree;
import models.Player;
import models.StrategyBoard;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StrategyGameCtrlTest {

    private StrategyGameCtrl strategyGameCtrl;
    private BoardFactory boardFactory;
    private StrategyBoard strategyBoard;
    private GameTreeFactory gameTreeFactory;
    private Player computer;

    @Before
    public void setup() {
        computer = mock(Player.class);
        boardFactory = mock(BoardFactory.class);
        gameTreeFactory = mock(GameTreeFactory.class);
        strategyGameCtrl = new StrategyGameCtrlImpl(boardFactory, gameTreeFactory);
        Player[] players = {};
        strategyBoard = mock(StrategyBoard.class);
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(strategyBoard);
        strategyGameCtrl.setBoard(constants.SIDE, players);
    }

    @Test
    public void shouldBeAbleToSetTheBoardTo() {
        verify(boardFactory).createBoard(anyInt(), any(Player[].class));
    }

    @Test
    public void shouldBeAbleTeGetAGameTreeBasedOnAMove() {
        Player opponent = mock(Player.class);
        Integer[] move = {1,2};
        GameTree gameTree = mock(GameTree.class);
        when(gameTreeFactory.createTree(strategyBoard, computer, opponent,boardFactory)).thenReturn(gameTree);
        assertThat(strategyGameCtrl.getTree(computer, opponent, move), is(equalTo(gameTree)));
        verify(strategyBoard).set(anyInt(), anyInt(), any(Player.class));
        verify(gameTreeFactory).createTree(strategyBoard, computer, opponent,boardFactory);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() {
        strategyGameCtrl.winningMove(computer);
        verify(strategyBoard).winningMove(computer);
    }

    @Test
    public void shouldBeAbleToFilterMovesOfBord() {
        strategyGameCtrl.filterMoves(computer);
        verify(strategyBoard).filterMoves(computer);
    }
}