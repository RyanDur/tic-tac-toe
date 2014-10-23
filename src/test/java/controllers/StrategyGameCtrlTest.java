package controllers;

import factories.StrategyGameFactory;
import lang.constants;
import models.Player;
import models.StrategyGame;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StrategyGameCtrlTest {

    private StrategyGameCtrl strategyGameCtrl;
    private StrategyGameFactory strategyGameFactory;
    private Player[] board;
    private StrategyGame strategyGame;
    private Player player;

    @Before
    public void setup() {
        strategyGameFactory = mock(StrategyGameFactory.class);
        strategyGame = mock(StrategyGame.class);
        when(strategyGameFactory.createStrategyGame(anyInt(), any(Player[].class))).thenReturn(strategyGame);
        strategyGameCtrl = new StrategyGameCtrlImpl(constants.SIDE, strategyGameFactory);
        board = new Player[]{};
        strategyGameCtrl.setBoard(board);
        player = mock(Player.class);
    }

    @Test
    public void shouldCreateANewStrategyGameWhenSettingTheBoard() {
        verify(strategyGameFactory).createStrategyGame(constants.SIDE, board);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() {
        strategyGameCtrl.findWinningMove(player);
        verify(strategyGame).findWinningMove(player);
    }

    @Test
    public void shouldBeAbleToGetTheBestMove() {
        strategyGameCtrl.getBestMove(player, player);
        verify(strategyGame).getBestMove(player, player);
    }
}