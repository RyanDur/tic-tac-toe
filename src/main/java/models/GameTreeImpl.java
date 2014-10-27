package models;

import exceptions.NotVacantException;
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
        if (winner != null) value = winWeight(winner);
        else if (board.getVacancies().size() == 0) value = constants.DRAW_WEIGHT;
        else children = setChildren();
    }

    private int winWeight(Player winner) {
        return winner instanceof ComputerPlayer ? constants.WIN_WEIGHT : constants.LOSE_WEIGHT;
    }

    private List<GameTree> setChildren() {
        List<GameTree> children = new ArrayList<>();
        Optional<Integer[]> winMove = board.winningMove(player2);
        Optional<Integer[]> loseMove = board.winningMove(player1);
        if (winMove.isPresent())  children.add(makeChild(winMove));
        if (loseMove.isPresent())  children.add(makeChild(loseMove));
        if (children.isEmpty()) children = makeChildren();
        return children;
    }

    private List<GameTree> makeChildren() {
        List<GameTree> children;
        children = collectChildren(board.filterMoves(player1));
        if (children.isEmpty()) children = collectChildren(board.getVacancies());
        return children;
    }

    private List<GameTree> collectChildren(List<Integer[]> stream) {
        return stream.stream().map(move -> makeChild(Optional.of(move)))
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
        copy.setBoard(board.getBoard());
        try {
            copy.set(win[0], win[1], player2);
        } catch (NotVacantException e) {
            e.printStackTrace();
        }
        return copy;
    }
}