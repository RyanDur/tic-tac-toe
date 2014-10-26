package models;

import factories.BoardFactory;
import lang.constants;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

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
//        return board.winningMove(player);
        return null;
    }

    @Override
    public Optional<Integer[]> getBestMove(Player computer, Player human) {
//        if(boardEmpty()) return getCorner();
        System.out.println(board.getBoard().length);
        Optional<Board> move = bestMoveOf(computer, human);
        if (noBest(move)) return anyMove();
//        return Optional.of(move.get().lastMove());
        return null;
    }

    public Optional<Integer[]> getCorner() {
        List<Integer[]> corners = constants.CORNERS;
        return Optional.of(corners.get(random.nextInt(corners.size())));
    }

    private Optional<Board> bestMoveOf(Player computer, Player human) {
        System.out.println("computer");
//        board.getVacancies().stream().map(vacancy -> {
//            Board board1 = boardFactory.createBoard(constants.SIDE);
//            board1.set(vacancy[0], vacancy[1], computer);
//            return board1;
//        }).forEach(board -> {
//            System.out.print(Arrays.toString(board.lastMove()));
//            System.out.println(boardFactory.createGameTree(board, computer, human, boardFactory).getValue());
//        });
//        System.out.println();
//        System.out.println("human");
//        Stream<Board> boardStream = board.filterMoves(human);
//        boardStream.forEach(board -> {
//            System.out.print(Arrays.toString(board.lastMove()));
//            System.out.println(boardFactory.createGameTree(board, human, computer, boardFactory).getValue());
//        });
//        System.out.println();
//        System.out.println("computer");
//        Stream<Board> boardStream1 = board.filterMoves(computer);
//        boardStream1.forEach(board -> {
//            System.out.print(Arrays.toString(board.lastMove()));
//            System.out.println(boardFactory.createGameTree(board, computer, human, boardFactory).getValue());
//        });

//        return board.filterMoves(computer).max((game1, game2) ->
//                boardFactory.createGameTree(game1, computer, human, boardFactory).getValue() -
//                        boardFactory.createGameTree(game2, computer, human, boardFactory).getValue());
        return null;
    }

    private boolean noBest(Optional<Board> game) {
        return !game.isPresent() && !boardEmpty();
    }

    private Optional<Integer[]> anyMove() {
//        return Optional.of(board.getVacancies().get(0));
        return null;
    }

    private int countPieces(Player[] board) {
        return (int) Arrays.stream(board).filter(player -> player != null).count();
    }

    private boolean boardEmpty() {
        return countPieces(board.getBoard()) == 0;
    }
}