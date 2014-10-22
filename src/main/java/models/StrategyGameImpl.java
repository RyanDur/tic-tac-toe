package models;

import factories.BoardFactory;
import lang.constants;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class StrategyGameImpl extends GameImpl implements StrategyGame {

    private final int side;
    private Board board;
    private BoardFactory boardFactory;

    public StrategyGameImpl(int side, Player[] board, BoardFactory boardFactory) {
        super(side, boardFactory);
        this.side = side;
        this.board = boardFactory.createBoard(side);
        this.board.setBoard(board);
        this.boardFactory = boardFactory;
    }

    @Override
    public boolean boardEmpty() {
        return countPieces(board.getBoard()) == 0;
    }

    @Override
    public Optional<Integer[]> findWinningMove(Player player) {
        return board.winningMove(player);
    }

    @Override
    public List<Integer[]> getBestMove(Player computer, Player human) {
        Stream<Board> moves = board.filterMoves(computer);
        moves.forEach(game -> {
            System.out.print(Arrays.toString(game.lastMove()));
            System.out.println(new GameNode(game, human, computer, boardFactory).getValue());
        });

        return null;
    }



    private int countPieces(Player[] board) {
        return (int) Arrays.stream(board).filter(player -> player != null).count();
    }


    private class GameNode {
        private int value = 0;
        private List<GameNode> children;
        private final Board board;
        private Player player1;
        private final Player player2;
        private final BoardFactory boardFactory;

        public GameNode(Board board, Player player1, Player player2, BoardFactory boardFactory) {
            this.board = board;
            this.player1 = player1;
            this.player2 = player2;
            this.boardFactory = boardFactory;
            children = new ArrayList<>();
            check();
        }

        public int getValue() {
            return value + children.stream()
                    .map(GameNode::getValue)
                    .reduce(0, (a, b) -> a + b);
        }

        private void check() {
            Integer[] move = board.lastMove();
            if(board.isWinner(move[0], move[1], player2)) {
                if(player1 instanceof ComputerPlayer) value = 1;
                else value = -1;
            } else if (board.catsGame()) value = 0;
            else addNode();
        }

        private void addNode() {
            Optional<Integer[]> winMove = board.winningMove(player1);
            if(winMove.isPresent()) {
                winMove = board.winningMove(player2);
                set(winMove.get());
            } else {
                board.filterMoves(player1).forEach(move ->
                        set(move.lastMove()));
            }
        }

        private void set(Integer[] move) {
            Board copy = boardFactory.createBoard(constants.SIDE);
            copy.setBoard(board.getBoard());
            copy.set(move[0], move[1], player1);
            GameNode node = new GameNode(copy, player2, player1, boardFactory);
            children.add(node);
        }
    }
}