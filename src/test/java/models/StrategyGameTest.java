package models;

import exceptions.NotVacantException;
import factories.BoardFactory;
import factories.GameTreeFactory;
import lang.constants;
import models.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

public class StrategyGameTest {

    private StrategyGame strategyGame;
    private BoardFactory boardFactory;
    private StrategyBoard strategyBoard;
    private GameTreeFactory gameTreeFactory;
    private Player computer;

    @Before
    public void setup() {
        computer = mock(Player.class);
        boardFactory = mock(BoardFactory.class);
        gameTreeFactory = mock(GameTreeFactory.class);
        strategyGame = new StrategyGameImpl(boardFactory, gameTreeFactory);
        Player[] players = {};
        strategyBoard = mock(StrategyBoard.class);
        when(boardFactory.createBoard(anyInt(), any(Player[].class))).thenReturn(strategyBoard);
        strategyGame.setBoard(constants.SIDE, players);
    }

    @Test
    public void shouldBeAbleToSetTheBoardTo() {
        verify(boardFactory).createBoard(anyInt(), any(Player[].class));
    }

    @Test
    public void shouldBeAbleTeGetAGameTreeBasedOnAMove() throws NotVacantException {
        Player opponent = mock(Player.class);
        Integer[] move = {1,2};
        GameTree gameTree = mock(GameTree.class);
        when(gameTreeFactory.createTree(strategyBoard, computer, opponent,boardFactory)).thenReturn(gameTree);
        assertThat(strategyGame.getTree(computer, opponent, move), is(equalTo(gameTree)));
        verify(strategyBoard).set(anyInt(), anyInt(), any(Player.class));
        verify(gameTreeFactory).createTree(strategyBoard, computer, opponent,boardFactory);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() {
        strategyGame.winningMove(computer);
        verify(strategyBoard).winningMove(computer);
    }

    @Test
    public void shouldBeAbleToFilterMovesOfBord() {
        strategyGame.filterMoves(computer);
        verify(strategyBoard).filterMoves(computer);
    }

    @Test
    public void shouldBeAbleToSeeIfABoardIsEmpty() {
        when(strategyBoard.getBoard()).thenReturn(new Player[]{});
        assertThat(strategyGame.boardEmpty(), is(true));
    }

    @Test
    public void shouldBeAbleToSeeIfABoardIsNotEmpty() {
        when(strategyBoard.getBoard()).thenReturn(new Player[]{mock(Player.class)});
        assertThat(strategyGame.boardEmpty(), is(false));
    }

    @Test
    public void shouldBeAbleToSeeIfABoardHasToFewPieces() {
        when(strategyBoard.getBoard()).thenReturn(new Player[]{mock(Player.class)});
        assertThat(strategyGame.toFewPieces(), is(true));
    }

    @Test
    public void shouldBeAbleToSeeIfABoardDoesNotHaveToFewPieces() {
        when(strategyBoard.getBoard()).thenReturn(new Player[]{mock(Player.class),mock(Player.class),mock(Player.class)});
        assertThat(strategyGame.toFewPieces(), is(false));
    }

    @Test
    public void shouldBeAbleToGetTheCenter() {
        Integer[] center = constants.CENTER;
        when(strategyBoard.get(center[0],center[1])).thenReturn(null);
        assertThat(strategyGame.centerOrCorner().get(), is(equalTo(center)));
    }

    @Test
    public void shouldBeAbleToGetACorner() {
        Integer[] center = constants.CENTER;
        when(strategyBoard.get(center[0],center[1])).thenReturn(mock(Player.class));
        assertThat(constants.CORNERS, hasItem(strategyGame.centerOrCorner().get()));
    }

    @Test
    public void shouldBeAbleToCheckIfAMoveIsPresent() {
        Optional<Integer[]> move = Optional.of(new Integer[]{1,2});
        assertThat(strategyGame.noBest(move), is(false));
    }

    @Test
    public void shouldBeAbleToCheckIfAMoveIsNotPresentThenTheBoardShouldNotBeEmpty() {
        Optional<Integer[]> move = Optional.empty();
        when(strategyBoard.getBoard()).thenReturn(new Player[constants.SIDE*constants.SIDE]);
        assertThat(strategyGame.noBest(move), is(false));
    }

    @Test
    public void shouldBeAbleToCheckIfAMoveIsNotPresent() {
        Optional<Integer[]> move = Optional.empty();
        when(strategyBoard.getBoard()).thenReturn(new Player[]{mock(Player.class)});
        assertThat(strategyGame.noBest(move), is(true));
    }

    @Test
    public void shouldBeAbleToGetAnyMove() {
        List<Integer[]> vacancies = Arrays.<Integer[]>asList(constants.CENTER);
        when(strategyBoard.getVacancies()).thenReturn(vacancies);

        assertThat(strategyGame.anyMove().get(), is(equalTo(constants.CENTER)));
    }
}