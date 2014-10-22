package models;

import factories.BoardFactory;
import factories.BoardFactoryImpl;
import lang.constants;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.mockito.Mockito.*;

public class StrategyGameTest {
    private Board board;
    private Player computer;
    private Player human;
    private Player[] players;
    private StrategyGame strategyGame;

    @Before
    public void setup() {
        players = new Player[constants.SIDE * constants.SIDE];
        board = mock(Board.class);
        computer = mock(ComputerPlayer.class);
        human = mock(Player.class);
        BoardFactory boardFactory = mock(BoardFactory.class);
        when(boardFactory.createBoard(constants.SIDE)).thenReturn(board);
        when(computer.getPiece()).thenReturn(constants.GAME_PIECE_ONE);
        when(human.getPiece()).thenReturn(constants.GAME_PIECE_TWO);
        strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory);
    }

    @Test
    public void shouldBeAbleToCheckIfABoardIsEmpty() {
        when(board.getBoard()).thenReturn(players);
        assertThat(strategyGame.boardEmpty(), is(true));
    }

    @Test
    public void shouldBeAbleToFindTheWinningMove() {
        strategyGame.findWinningMove(computer);
        verify(board).winningMove(computer);
    }

    @Test
    public void shouldBeAbleToFindTheLosingMove() {
        strategyGame.findWinningMove(human);
        verify(board).winningMove(human);
    }

    //      | |
    //    -------
    //      | |
    //    -------
    //      | |X
    @Test
    public void shouldBeAbleToFindTheBestMoveIfHumanGoesFirst() {
        BoardFactory boardFactory1 = new BoardFactoryImpl();
        players[4] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory1);
        assertThat(strategyGame.getBestMove(computer, human).get(), is(new Integer[]{0, 0}));
    }

    //      | |
    //    -------
    //      |O|
    //    -------
    //      | |X
    @Test
    public void shouldBeAbleToFindTheBestMoveForComputerCornerHumanCenter() {
        BoardFactory boardFactory1 = new BoardFactoryImpl();
        players[8] = computer;
        players[4] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory1);
        assertThat(strategyGame.getBestMove(computer, human).get(), is(new Integer[]{0, 2}));
    }

    //     O| |
    //    -------
    //      |X|
    //    -------
    //      |O|X
    @Test
    public void shouldBeAbleToFindTheBestMoveForComputerRightBottomHumanLeftTopComputerCenterHumanBottomMiddle() {
        BoardFactory boardFactory1 = new BoardFactoryImpl();
        players[8] = computer;
        players[0] = human;
        players[4] = computer;
        players[7] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory1);
        assertThat(strategyGame.getBestMove(computer, human).get(), is(new Integer[]{0, 2}));
    }

    //      | |O
    //    -------
    //      | |X
    //    -------
    //      |O|X
    @Test
    public void shouldBeAbleToFindTheBestMoveForComputerRightBottomHumanMiddleRightComputerTOpRightHumanBottomMiddle() {
        BoardFactory boardFactory1 = new BoardFactoryImpl();
        players[8] = computer;
        players[2] = human;
        players[5] = computer;
        players[7] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory1);
        assertThat(strategyGame.getBestMove(computer, human).get(), is(new Integer[]{1, 1}));
    }

    //     O| |X
    //    -------
    //      | |O
    //    -------
    //      | |X
    @Test
    public void shouldBeAbleToFindTheBestMoveForComputerRightBottomHumanLeftTopComputerTopRightHumanCenterRight() {
        BoardFactory boardFactory1 = new BoardFactoryImpl();
        players[8] = computer;
        players[0] = human;
        players[2] = computer;
        players[5] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory1);
        assertThat(strategyGame.getBestMove(computer, human).get(), is(new Integer[]{2, 0}));
    }

    //     O| |X
    //    -------
    //     X|O|O
    //    -------
    //     O|X|X
    @Test
    public void shouldBeAbleToMoveIfNoBestMoveExists() {
        BoardFactory boardFactory1 = new BoardFactoryImpl();
        players[8] = computer;
        players[0] = human;
        players[2] = computer;
        players[5] = human;
        players[3] = computer;
        players[6] = human;
        StrategyGame strategyGame = new StrategyGameImpl(constants.SIDE, players, boardFactory1);
        assertThat(strategyGame.getBestMove(computer, human).get(), is(new Integer[]{0, 1}));
    }

    @Test
    public void shouldBeAbleToChooseAnyRandomCorner() {
        List<Integer[]> corners = constants.CORNERS;
        Integer[] corner = strategyGame.getCorner();
        assertThat(corners, hasItem(corner));
    }
}