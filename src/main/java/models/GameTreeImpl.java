package models;

import factories.BoardFactory;
import lang.constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class GameTreeImpl implements GameTree {
    private int value = 0;
    private List<GameTree> children;
    private StrategyBoard board;
    private final Player player1;
    private final Player player2;
    private BoardFactory boardFactory;

    public GameTreeImpl(StrategyBoard board, Player player1, Player player2, BoardFactory boardFactory) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.boardFactory = boardFactory;
        children = new ArrayList<>();
        setValue();
    }

    @Override
    public int getValue() {
        return value + children.stream()
                .map(GameTree::getValue)
                .reduce(0, (value1, value2) -> value1 + value2);
    }

    private void setValue() {
        Player winner = board.getWinner();
        if (winner != null) {
            if (winner instanceof ComputerPlayer) value = constants.WIN_WEIGHT;
            else value = constants.LOSE_WEIGHT;
        } else if (board.getVacancies().size() == 0) value = constants.DRAW_WEIGHT;
         else children = makeChildren();
    }

    private List<GameTree> makeChildren() {
        return board.getVacancies().stream()
                .map(vacancy -> makeChild(Optional.of(vacancy)))
                .collect(Collectors.toList());
    }

    private GameTree makeChild(Optional<Integer[]> move) {
        return move.map(win -> createNode(playMove(win))).get();
    }

    private GameTreeImpl createNode(StrategyBoard game) {
        return new GameTreeImpl(game, player2, player1, boardFactory);
    }

    private StrategyBoard playMove(Integer[] win) {
        StrategyBoard copy = boardFactory.createBoard(constants.SIDE, board);
        copy.setBoard(board.getBoard());
        copy.set(win[0], win[1], player2);
        return copy;
    }
}