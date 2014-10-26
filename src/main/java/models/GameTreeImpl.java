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
    private Board board;
    private final Player player1;
    private final Player player2;
    private BoardFactory boardFactory;

    public GameTreeImpl(Board board, Player player1, Player player2, BoardFactory boardFactory) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.boardFactory = boardFactory;
        children = new ArrayList<>();
        setValue(board.lastMove());
    }

    @Override
    public int getValue() {
        return value + children.stream()
                .map(GameTree::getValue)
                .reduce(0, (value1, value2) -> value1 + value2);
    }

    private void setValue(Integer[] move) {
        if (board.isWinner(move[0], move[1], player1)) {
            if (player1 instanceof ComputerPlayer) value = constants.WIN_WEIGHT;
            else value = constants.LOSE_WEIGHT;
        } else if (board.detectCatsGame()) value = constants.DRAW_WEIGHT;
        else setChildNodes();
    }

    private void setChildNodes() {
        Optional<Integer[]> winningMove = board.winningMove(player2);
        if(!winningMove.isPresent()) winningMove = board.winningMove(player1);
        if(winningMove.isPresent()) children.add(getChild(winningMove));
        else children = getChildren();
    }

    private List<GameTree> getChildren() {
        List<GameTree> children = bestChildren();
        if (children.isEmpty()) children = anyChildren();
        return children;
    }

    private List<GameTree> anyChildren() {
        return board.getVacancies().stream()
                .map(vacancy -> getChild(Optional.of(vacancy)))
                .collect(Collectors.toList());
    }

    private List<GameTree> bestChildren() {
        List<GameTree> filtered = filter(player1);
        if (filtered.isEmpty()) filtered = filter(player2);
        return filtered;
    }

    private List<GameTree> filter(Player player) {
        return board.filterMoves(player)
                .map(this::getGameNode)
                .collect(Collectors.toList());
    }

    private GameTree getChild(Optional<Integer[]> move) {
        return move.map(win -> {
            Board copy = getBoard(win);
            return getGameNode(copy);
        }).get();
    }

    private GameTreeImpl getGameNode(Board game) {
        return new GameTreeImpl(game, player2, player1, boardFactory);
    }

    private Board getBoard(Integer[] win) {
        Board copy = boardFactory.createBoard(constants.SIDE);
        copy.setBoard(board.getBoard());
        copy.set(win[0], win[1], player2);
        return copy;
    }
}
