package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.BoardFactory;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StrategyGameTest {
    private Board board;
    private Player computer;
    private Player human;
    private BoardFactory boardFactory;
    private Player[] players;

    @Before
    public void setup() {
        players = new Player[constants.SIDE * constants.SIDE];
        board = mock(Board.class);
        computer = mock(ComputerPlayer.class);
        human = mock(Player.class);
        boardFactory = mock(BoardFactory.class);
        when(boardFactory.createBoard(constants.SIDE)).thenReturn(board);
        when(computer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(human.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
    }

    @Test
    public void shouldBeAbleToCheckIfABoardIsEmpty() {
        when(board.getBoard()).thenReturn(players);
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.boardEmpty(), is(true));
    }

    @Test
    public void shouldBeAbleToFindTheWinningMove() {
        when(board.isWinner(0, 2, computer)).thenReturn(true);
        when(board.getBoard()).thenReturn(new Player[]{human, null, null, human, computer, null, computer, human, computer});
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.findWinningMove(computer), is(equalTo(Optional.of(2))));
    }

    @Test
    public void shouldNotBeAbleToFindTheWinningMoveIfNoneExists() {
        when(board.isWinner(0, 2, computer)).thenReturn(true);
        when(board.getBoard()).thenReturn(new Player[]{human, null, null, human, computer, null, computer, human, computer});
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.findWinningMove(human), is(Optional.empty()));
    }

    @Test
    public void shouldBeAbleToFindTheLosingMove() {
        when(board.isWinner(1, 0, human)).thenReturn(true);
        when(board.getBoard()).thenReturn(new Player[]{null, null, computer, null, human, human, null, null, computer});
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.findWinningMove(human), is(equalTo(Optional.of(3))));
    }


    @Test
    public void shouldNotBeAbleToFindTheLosingMoveIfNoneExist() {
        when(board.isWinner(1, 0, human)).thenReturn(true);
        when(board.getBoard()).thenReturn(new Player[]{null, null, computer, null, human, human, null, null, computer});
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
        assertThat(strategyGame.findWinningMove(computer), is(Optional.empty()));
    }

    @Test
    public void shouldBeAbleToFindTheBestMove() throws NotVacantException, OutOfBoundsException {
//        board[8] = computer;
//        board[4] = human;
//        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, board, boardFactory);
//        assertThat(strategyGame.findBestMove(computer, human), is(equalTo(of(3))));
    }
}