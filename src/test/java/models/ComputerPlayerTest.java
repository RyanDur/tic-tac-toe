package models;

import controllers.StrategyCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class ComputerPlayerTest {

    private StrategyCtrl strategyCtrl;
    private Player[] board;
    private ComputerPlayer computer;
    private Player human;

    @Before
    public void setup() {
        strategyCtrl = mock(StrategyCtrl.class);
        board = new Player[constants.SIDE * constants.SIDE];
        human = mock(Player.class);
        computer = new ComputerPlayerImpl(constants.GAME_PIECE_ONE, constants.SIDE, human, strategyCtrl);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyCtrl.findWinningMove(computer)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyCtrl).findWinningMove(computer);
    }

    @Test
    public void shouldBlockOpponentFromSettingTheWinningMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyCtrl.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyCtrl.findWinningMove(human)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyCtrl).findWinningMove(human);
    }

    @Test
    public void shouldPickBestSpaceIfThereIsNoWinningOrLosingMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyCtrl.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyCtrl.findWinningMove(human)).thenReturn(Optional.empty());
        when(strategyCtrl.getBestMove(computer, human)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyCtrl).getBestMove(computer, human);
    }
}