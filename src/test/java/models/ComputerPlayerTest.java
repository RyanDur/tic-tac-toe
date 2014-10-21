package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.BoardFactory;
import factories.StrategyGameFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ComputerPlayerTest {

    private StrategyGame strategyGame;
    private Player[] board;
    private ComputerPlayer computer;

    @Before
    public void setup() {
        StrategyGameFactory strategyGameFactory = mock(StrategyGameFactory.class);
        strategyGame = mock(StrategyGame.class);
        BoardFactory boardFactory = mock(BoardFactory.class);
        board = new Player[constants.SIDE * constants.SIDE];
        when(strategyGameFactory.createStrategyGame(anyInt(), any(Player[].class), any(BoardFactory.class))).thenReturn(strategyGame);
        computer = new ComputerPlayerImpl(constants.GAME_PIECE_ONE, constants.SIDE, strategyGameFactory, boardFactory);
    }

    @Test
    public void shouldChooseCornerIfGoingFirst() throws OutOfBoundsException, NotVacantException {
        when(strategyGame.boardEmpty()).thenReturn(true);
        computer.setBoard(board);
        computer.calculateBestMove();
        assertThat(computer.getX(), is(equalTo(constants.SIDE - 1)));
        assertThat(computer.getY(), is(equalTo(constants.SIDE - 1)));
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() throws OutOfBoundsException, NotVacantException {
        when(strategyGame.boardEmpty()).thenReturn(false);
        when(strategyGame.findWinningMove(computer)).thenReturn(Optional.of(new Integer[]{0,2}));
        computer.setBoard(board);
        computer.calculateBestMove();
        assertThat(computer.getX(), is(equalTo(0)));
        assertThat(computer.getY(), is(equalTo(2)));
    }

    @Test
    public void shouldBlockOpponentFromSettingTheWinningMove() throws OutOfBoundsException, NotVacantException {
        when(strategyGame.boardEmpty()).thenReturn(false);
        Player player2 = mock(Player.class);
        when(strategyGame.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyGame.findWinningMove(player2)).thenReturn(Optional.of(new Integer[]{1,0}));
        board[3] = player2;
        computer.setBoard(board);
        computer.calculateBestMove();
        assertThat(computer.getX(), is(equalTo(1)));
        assertThat(computer.getY(), is(equalTo(0)));
    }

    @Test
    public void shouldPickBestSpaceIfThereIsNoWinningOrLosingMove() throws OutOfBoundsException, NotVacantException {
        when(strategyGame.boardEmpty()).thenReturn(false);
        Player player2 = new PlayerImpl(constants.GAME_PIECE_TWO, constants.SIDE);
        when(strategyGame.findWinningMove(computer)).thenReturn(Optional.empty());
        when(strategyGame.findWinningMove(player2)).thenReturn(Optional.empty());
        when(strategyGame.findBestMove(computer, player2)).thenReturn(Optional.of(new Integer[]{2,1}));
        board[3] = player2;
        computer.setBoard(board);
        computer.calculateBestMove();
        assertThat(computer.getX(), is(equalTo(2)));
        assertThat(computer.getY(), is(equalTo(1)));
    }
}