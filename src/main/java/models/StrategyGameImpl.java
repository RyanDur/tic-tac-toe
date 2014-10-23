package models;

import factories.BoardFactory;
import lang.constants;

import java.util.*;

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
    public Optional<Integer[]> findWinningMove(Player player) {
        return board.winningMove(player);
    }

    @Override
    public Optional<Integer[]> getBestMove(Player computer, Player human) {
        int pieces = countPieces(board.getBoard());
        if(boardEmpty()) return getCorner();
        if(pieces > 0 && pieces < constants.SIDE-1) return centerOrCorner();
        Optional<Board> move = bestMoveOf(computer, human);
        if(noBest(move)) return anyMove();
        return Optional.of(move.get().lastMove());
    }

    private Optional<Integer[]> centerOrCorner() {
        Integer[] center = constants.CENTER;
        if(board.get(center[0], center[1])  == null) return Optional.of(center);
        return getCorner();
    }

    public Optional<Integer[]> getCorner() {
        List<Integer[]> corners = constants.CORNERS;
        return Optional.of(corners.get(random.nextInt(corners.size())));
    }

    private Optional<Board> bestMoveOf(Player computer, Player human) {
//        board.filterMoves(computer).forEach(move -> {
//            System.out.print(Arrays.toString(move.lastMove()));
//            System.out.println(new GameNode(move, human, computer, boardFactory).getValue());
//        });
//        return null;
        return board.filterMoves(computer).max((game1, game2) ->
                new GameNode(game1, human, computer, boardFactory).getValue() -
                        new GameNode(game2, human, computer, boardFactory).getValue());
    }

    private boolean noBest(Optional<Board> game) {
        return !game.isPresent() && !boardEmpty();
    }

    private Optional<Integer[]> anyMove() {
        return Optional.of(board.getVacancies().get(0));
    }

    private int countPieces(Player[] board) {
        return (int) Arrays.stream(board).filter(player -> player != null).count();
    }

    private boolean boardEmpty() {
        return countPieces(board.getBoard()) == 0;
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
            if (board.isWinner(move[0], move[1], player2)) value = winWeight();
            else if (board.catsGame()) value = constants.DRAW_WEIGHT;
            else addNode();
        }

        private int winWeight() {
            return player2 instanceof ComputerPlayer ? 2 : constants.LOSE_WEIGHT;
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