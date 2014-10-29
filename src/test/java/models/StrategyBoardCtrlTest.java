package models;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
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

public class StrategyBoardCtrlTest {

    private StrategyBoardCtrl strategyBoardCtrl;
    private BoardFactory boardFactory;
    private StrategyBoard strategyBoard;
    private GameTreeFactory gameTreeFactory;
    private String computer;

    @Before
    public void setup() {
        computer = constants.GAME_PIECE_TWO;
        boardFactory = mock(BoardFactory.class);
        gameTreeFactory = mock(GameTreeFactory.class);
        strategyBoardCtrl = new StrategyBoardCtrlImpl(boardFactory, gameTreeFactory);
        String[] players = {};
        strategyBoard = mock(StrategyBoard.class);
        when(boardFactory.createBoard(anyInt(), any(String[].class))).thenReturn(strategyBoard);
        strategyBoardCtrl.setBoard(constants.SIDE, players);
    }

    @Test
    public void shouldBeAbleToSetTheBoardTo() {
        verify(boardFactory).createBoard(anyInt(), any(String[].class));
    }

    @Test
    public void shouldBeAbleTeGetAGameTreeBasedOnAMove() throws NotVacantException, OutOfTurnException {
        String opponent = constants.GAME_PIECE_TWO;
        Integer[] move = {1,2};
        GameTree gameTree = mock(GameTree.class);
        when(gameTreeFactory.createTree(strategyBoard, computer, opponent,boardFactory)).thenReturn(gameTree);
        assertThat(strategyBoardCtrl.getTree(computer, opponent, move), is(equalTo(gameTree)));
        verify(strategyBoard).set(anyInt(), anyInt(), anyString());
        verify(gameTreeFactory).createTree(strategyBoard, computer, opponent,boardFactory);
    }

    @Test
    public void shouldBeAbleToGetTheWinningMove() {
        strategyBoardCtrl.winningMove(computer);
        verify(strategyBoard).winningMove(computer);
    }

    @Test
    public void shouldBeAbleToFilterMovesOfBord() {
        strategyBoardCtrl.filterMoves(computer);
        verify(strategyBoard).filterMoves(computer);
    }

    @Test
    public void shouldBeAbleToSeeIfABoardIsEmpty() {
        when(strategyBoard.getBoard()).thenReturn(new String[]{});
        assertThat(strategyBoardCtrl.boardEmpty(), is(true));
    }

    @Test
    public void shouldBeAbleToSeeIfABoardIsNotEmpty() {
        when(strategyBoard.getBoard()).thenReturn(new String[]{anyString()});
        assertThat(strategyBoardCtrl.boardEmpty(), is(false));
    }

    @Test
    public void shouldBeAbleToSeeIfABoardHasToFewPieces() {
        when(strategyBoard.getBoard()).thenReturn(new String[]{anyString()});
        assertThat(strategyBoardCtrl.toFewPieces(), is(true));
    }

    @Test
    public void shouldBeAbleToSeeIfABoardDoesNotHaveToFewPieces() {
        when(strategyBoard.getBoard()).thenReturn(new String[]{anyString(),anyString(),anyString()});
        assertThat(strategyBoardCtrl.toFewPieces(), is(false));
    }

    @Test
    public void shouldBeAbleToGetTheCenter() {
        Integer[] center = constants.CENTER;
        when(strategyBoard.get(center[0],center[1])).thenReturn(null);
        assertThat(strategyBoardCtrl.centerOrCorner().get(), is(equalTo(center)));
    }

    @Test
    public void shouldBeAbleToGetACorner() {
        Integer[] center = constants.CENTER;
        when(strategyBoard.get(center[0],center[1])).thenReturn(anyString());
        assertThat(constants.CORNERS, hasItem(strategyBoardCtrl.centerOrCorner().get()));
    }

    @Test
    public void shouldBeAbleToCheckIfAMoveIsPresent() {
        Optional<Integer[]> move = Optional.of(new Integer[]{1,2});
        assertThat(strategyBoardCtrl.noBest(move), is(false));
    }

    @Test
    public void shouldBeAbleToCheckIfAMoveIsNotPresentThenTheBoardShouldNotBeEmpty() {
        Optional<Integer[]> move = Optional.empty();
        when(strategyBoard.getBoard()).thenReturn(new String[constants.SIDE*constants.SIDE]);
        assertThat(strategyBoardCtrl.noBest(move), is(false));
    }

    @Test
    public void shouldBeAbleToCheckIfAMoveIsNotPresent() {
        Optional<Integer[]> move = Optional.empty();
        when(strategyBoard.getBoard()).thenReturn(new String[]{anyString()});
        assertThat(strategyBoardCtrl.noBest(move), is(true));
    }

    @Test
    public void shouldBeAbleToGetAnyMove() {
        List<Integer[]> vacancies = Arrays.<Integer[]>asList(constants.CENTER);
        when(strategyBoard.getVacancies()).thenReturn(vacancies);

        assertThat(strategyBoardCtrl.anyMove().get(), is(equalTo(constants.CENTER)));
    }
}