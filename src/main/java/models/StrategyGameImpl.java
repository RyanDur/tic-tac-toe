package models;

import factories.BoardFactory;
import lang.constants;

import java.util.*;
import java.util.stream.Stream;

public class StrategyGameImpl extends GameImpl implements StrategyGame {
    private Random random;
    private Board board;
    private BoardFactory boardFactory;

    public StrategyGameImpl(int side, Player[] board, BoardFactory boardFactory) {
        super(side, boardFactory);
        this.board = boardFactory.createBoard(side);
        this.board.setBoard(board);
        this.boardFactory = boardFactory;
        random = new Random();
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
    public Optional<Integer[]> getBestMove(Player computer, Player human) {
        Stream<Board> moves = board.filterMoves(computer);
        if(noBest(moves)) return anyMove();
        else return bestOf(computer, human, moves);
    }

    @Override
    public Integer[] getCorner() {
        List<Integer[]> corners = constants.CORNERS;
        return corners.get(random.nextInt(corners.size()));
    }

    private Optional<Integer[]> bestOf(Player computer, Player human, Stream<Board> moves) {
        return Optional.of(moves.max((game1, game2) ->
                new GameNode(game1, human, computer, boardFactory).getValue() -
                        new GameNode(game2, human, computer, boardFactory).getValue()).get().lastMove());
    }

    private boolean noBest(Stream<Board> moves) {
        return moves.count() == 0 && !boardEmpty();
    }

    private Optional<Integer[]> anyMove() {
        return Optional.of(board.getVacancies().get(0));
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
            if (board.isWinner(move[0], move[1], player2))
                value = player2 instanceof ComputerPlayer ? constants.WIN_WEIGHT : constants.LOSE_WEIGHT;
            else if (board.catsGame()) value = constants.DRAW_WEIGHT;
            else addNode();
        }

        private void addNode() {
            Optional<Integer[]> winMove = board.winningMove(player1);
            if (!winMove.isPresent()) winMove = board.winningMove(player2);
            if (!winMove.isPresent()) board.filterMoves(player1).forEach(move -> set(move.lastMove()));
            else set(winMove.get());
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