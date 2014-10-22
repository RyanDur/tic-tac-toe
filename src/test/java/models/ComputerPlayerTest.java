package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.StrategyGameFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class ComputerPlayerTest {

    private StrategyGame strategyGame;
    private Player[] board;
    private ComputerPlayer computer;
    private Player human;

    @Before
    public void setup() {
        StrategyGameFactory strategyGameFactory = mock(StrategyGameFactory.class);
        strategyGame = mock(StrategyGame.class);
        board = new Player[constants.SIDE * constants.SIDE];
        human = mock(Player.class);
        when(strategyGameFactory.createStrategyGame(anyInt(), any(Player[].class))).thenReturn(strategyGame);
        computer = new ComputerPlayerImpl(constants.GAME_PIECE_ONE, constants.SIDE, human, strategyGameFactory);
    }

    @Test
    public void shouldChooseCornerIfGoingFirst() throws OutOfBoundsException, NotVacantException {
        when(strategyGame.boardEmpty()).thenReturn(true);
        when(strategyGame.getCorner()).thenReturn(new Integer[]{1,2});

        computer.calculateBestMove(board);
        verify(strategyGame).getCorner();
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() throws OutOfBoundsException, NotVacantException {
        when(strategyGame.boardEmpty()).thenReturn(false);
        int row = 0;
        int column = 2;
        when(strategyGame.findWinningMove(computer)).thenReturn(Optional.of(new Integer[]{row, column}));

        computer.calculateBestMove(board);
        verify(strategyGame).findWinningMove(computer);
    }

    @Test
    public void shouldBlockOpponentFromSettingTheWinningMove() throws OutOfBoundsException, NotVacantException {
        int row = 1;
        int column = 0;
        when(strategyGame.boardEmpty()).thenReturn(false);
        when(strategyGame.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyGame.findWinningMove(human)).thenReturn(Optional.of(new Integer[]{row, column}));

        computer.calculateBestMove(board);
        verify(strategyGame).findWinningMove(human);
    }

    @Test
    public void shouldPickBestSpaceIfThereIsNoWinningOrLosingMove() throws OutOfBoundsException, NotVacantException {
        int row = 1;
        int column = 0;
        when(strategyGame.boardEmpty()).thenReturn(false);
        when(strategyGame.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyGame.findWinningMove(human)).thenReturn(Optional.empty());
        when(strategyGame.getBestMove(computer, human)).thenReturn(Optional.of(new Integer[]{row, column}));

        computer.calculateBestMove(board);
        verify(strategyGame).getBestMove(computer, human);
    }
}