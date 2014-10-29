package models;

import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import factories.BoardFactory;
import lang.constants;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class GameTreeImpl implements GameTree {
    private List<GameTree> children;
    private StrategyBoard board;
    private final String player1;
    private final String player2;
    private BoardFactory boardFactory;
    private int max = 0;
    private int min = 0;

    public GameTreeImpl(StrategyBoard board, String player1, String player2, BoardFactory boardFactory) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.boardFactory = boardFactory;
        children = new ArrayList<>();
        setValue();
    }

    @Override
    public int getMaxValue() {
        return max + sumValues(maxValue());
    }

    @Override
    public int getMinValue() {
        return min + sumValues(minValue());
    }

    private void setValue() {
        String winner = board.getWinner();
        if (winner != null) winWeight(player1);
        else if (board.getVacancies().size() == 0) max = min = constants.DRAW_WEIGHT;
        else children = setChildren();
    }

    private void winWeight(String winner) {
        max = constants.WIN_WEIGHT;
        min = constants.LOSE_WEIGHT;
    }

    private List<GameTree> setChildren() {
        List<GameTree> children = new ArrayList<>();
        Optional<Integer[]> winMove = board.winningMove(player2);
        if (winMove.isPresent()) children.add(makeChild(winMove));
        else children = collectChildren(board.getVacancies());
        return children;
    }

    private List<GameTree> collectChildren(List<Integer[]> moves) {
        return moves.stream().map(move -> makeChild(Optional.of(move)))
                .collect(Collectors.toList());
    }

    private GameTree makeChild(Optional<Integer[]> move) {
        return move.map(win -> createNode(playMove(win))).get();
    }

    private GameTreeImpl createNode(StrategyBoard game) {
        return new GameTreeImpl(game, player2, player1, boardFactory);
    }

    private StrategyBoard playMove(Integer[] win) {
        StrategyBoard copy = boardFactory.createBoard(constants.SIDE, board.getBoard());
        try {
            copy.set(win[0], win[1], player2);
        } catch (NotVacantException | OutOfTurnException e) {
            e.printStackTrace();
        }
        return copy;
    }

    private Integer sumValues(Function<GameTree, Integer> value) {
        return children.stream()
                .map(value)
                .reduce(0, (value1, value2) -> value1 + value2);
    }

    private Function<GameTree, Integer> maxValue() {
        return GameTree::getMaxValue;
    }

    private Function<GameTree, Integer> minValue() {
        return GameTree::getMinValue;
    }
}