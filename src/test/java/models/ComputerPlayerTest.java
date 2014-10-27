package models;

import controllers.StrategyBoardCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class ComputerPlayerTest {

    private StrategyBoardCtrl strategyBoardCtrl;
    private Player[] board;
    private ComputerPlayer computer;
    private Player human;

    @Before
    public void setup() {
        strategyBoardCtrl = mock(StrategyBoardCtrl.class);
        board = new Player[constants.SIDE * constants.SIDE];
        human = mock(Player.class);
        computer = new ComputerPlayerImpl(constants.GAME_PIECE_ONE, constants.SIDE, human, strategyBoardCtrl);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyBoardCtrl.findWinningMove(computer)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyBoardCtrl).findWinningMove(computer);
    }

    @Test
    public void shouldBlockOpponentFromSettingTheWinningMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyBoardCtrl.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyBoardCtrl.findWinningMove(human)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyBoardCtrl).findWinningMove(human);
    }

    @Test
    public void shouldPickBestSpaceIfThereIsNoWinningOrLosingMove() throws OutOfBoundsException, NotVacantException {
        Optional<Integer[]> space = Optional.of(new Integer[]{0, 2});
        when(strategyBoardCtrl.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyBoardCtrl.findWinningMove(human)).thenReturn(Optional.empty());
        when(strategyBoardCtrl.getBestMove(computer, human)).thenReturn(space);

        computer.calculateBestMove(board);
        verify(strategyBoardCtrl).getBestMove(computer, human);
    }
}