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
    public void shouldBeAbleToGetTheWinningMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyGame.findWinningMove(computer)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyGame).findWinningMove(computer);
    }

    @Test
    public void shouldBlockOpponentFromSettingTheWinningMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyGame.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyGame.findWinningMove(human)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyGame).findWinningMove(human);
    }

    @Test
    public void shouldPickBestSpaceIfThereIsNoWinningOrLosingMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyGame.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyGame.findWinningMove(human)).thenReturn(Optional.empty());
        when(strategyGame.getBestMove(computer, human)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyGame).getBestMove(computer, human);
    }
}