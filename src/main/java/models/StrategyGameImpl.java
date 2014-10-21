package models;

import factories.BoardFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StrategyGameImpl extends GameImpl implements StrategyGame {

    private final int side;
    private Board board;
    private BoardFactory boardFactory;
    private List<Integer[]> vacancies;

    public StrategyGameImpl(int side, Player[] board, BoardFactory boardFactory) {
        super(side, boardFactory);
        this.side = side;
        this.board = boardFactory.createBoard(side);
        this.board.setBoard(board);
        this.boardFactory = boardFactory;
        vacancies = this.board.getVacancies();
    }

    @Override
    public boolean boardEmpty() {
        return countPieces(board.getBoard()) == 0;
    }

    @Override
    public Optional<Integer[]> findWinningMove(Player player) {
        return find(winningMove(player, board));
    }

    @Override
    public List<Integer[]> filterMoves(Player player) {
        return generatePossibleMoves(player, vacancies).stream().
                filter(game -> find(winningMove(player, game)).isPresent()).
                map(Board::lastMove).collect(Collectors.toList());
    }

    private List<Board> generatePossibleMoves(Player player, List<Integer[]> vacancies) {
        List<Board> games = new ArrayList<>();

        for (Integer[] vacancy : vacancies) {
            Board game = boardFactory.createBoard(side);
            game.set(vacancy[0], vacancy[1], player);
            games.add(game);
        }
        return games;
    }

    private int countPieces(Player[] board) {
        return (int) Arrays.stream(board).filter(player -> player != null).count();
    }

    private Predicate<Integer[]> winningMove(Player player, Board board) {
        return vacancy -> {
            Board copy = boardFactory.createBoard(side);
            copy.setBoard(board.getBoard());
            copy.set(vacancy[0], vacancy[1], player);
            return copy.isWinner(vacancy[0], vacancy[1], player);
        };
    }

    private Optional<Integer[]> find(Predicate<Integer[]> move) {
        return vacancies.stream().
                filter(move).
                findFirst();
    }
}
