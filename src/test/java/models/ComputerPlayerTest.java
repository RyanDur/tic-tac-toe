package models;

import controllers.StrategyGameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ComputerPlayerTest {

    private StrategyGameCtrl strategyGameCtrl;
    private String[] board;
    private ComputerPlayer computer;
    private Player human;

    @Before
    public void setup() {
        strategyGameCtrl = mock(StrategyGameCtrl.class);
        board = new String[constants.SIDE * constants.SIDE];
        human = mock(Player.class);
        computer = new ComputerPlayerImpl(constants.GAME_PIECE_ONE, constants.SIDE, human, strategyGameCtrl);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyGameCtrl.findWinningMove(computer)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyGameCtrl).findWinningMove(computer);
    }

    @Test
    public void shouldBlockOpponentFromSettingTheWinningMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyGameCtrl.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyGameCtrl.findWinningMove(human)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyGameCtrl).findWinningMove(human);
    }

    @Test
    public void shouldPickBestSpaceIfThereIsNoWinningOrLosingMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyGameCtrl.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyGameCtrl.findWinningMove(human)).thenReturn(Optional.empty());
        when(strategyGameCtrl.getBestMove(computer, human)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyGameCtrl).getBestMove(computer, human);
    }
}