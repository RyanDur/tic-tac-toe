package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import factories.BoardFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StrategyGameImpl extends GameImpl implements StrategyGame {

    private final int side;
    private Board board;
    private BoardFactory boardFactory;
    private List<Integer> vacancies;
    private int vacancy;
    private int weight;
    private Player winner;

    public StrategyGameImpl(int side, Player[] board, BoardFactory boardFactory) {
        super(side, boardFactory);
        this.side = side;
        this.board = boardFactory.createBoard(side);
        this.board.setBoard(board);
        this.boardFactory = boardFactory;
        vacancies = getVacancies(this.board.getBoard());
    }

    @Override
    public boolean boardEmpty() {
        return countPieces(board.getBoard()) == 0;
    }

    @Override
    public Optional<Integer> findWinningMove(Player player) {
        return find(winningMove(player));
    }

    @Override
    public Player getWinner() {
        return winner;
    }

    @Override
    public void set(int vacancy, Player player) {
        board.set(calcRow(vacancy), calcColumn(vacancy), player);
        this.vacancy = vacancy;
    }

    @Override
    public int getSpace() {
        return vacancy;
    }

    @Override
    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public Optional<Integer> findBestMove(Player player, Player opponent) throws NotVacantException, OutOfBoundsException {
        Stream<StrategyGame> wins = filterMoves(this, player);
        wins.forEach(board -> board.setWeight(getWeight(board, player, opponent)));
        return Optional.of(wins.
                max((game1, game2) -> game1.getWeight() - game2.getWeight()).
                get().
                getSpace());
    }

    private int getWeight(StrategyGame game, Player player, Player opponent) {
        int result = 0;
        System.out.println(Arrays.toString(game.getBoard()));
        buildTree(game, player, opponent);
//                forEach(board -> board.setWeight(getWeight(board, player, opponent)));
        return result;
    }

    private void buildTree(StrategyGame game, Player player, Player opponent) {
        //filterMoves(game, opponent).forEach(g -> System.out.println(g.getSpace()));
        System.out.println();
        //System.out.println(filterMoves(game, opponent).count());
    }

    private Stream<StrategyGame> filterMoves(StrategyGame game, Player player) {
        return generatePossibleMoves(player, getVacancies(game.getBoard())).stream().
                filter(board -> board.findWinningMove(player).isPresent());
    }


    private List<StrategyGame> generatePossibleMoves(Player player, List<Integer> vacancies) {
        List<StrategyGame> games = new ArrayList<>();

        for (Integer vacancy : vacancies) {
            StrategyGame game = new StrategyGameImpl(side, getBoard(), boardFactory);
            game.set(vacancy, player);
            games.add(game);
        }
        return games;
    }

    private int countPieces(Player[] board) {
        return (int) Arrays.stream(board).filter(player -> player != null).count();
    }

    private Predicate<Integer> winningMove(Player player) {
        return vacancy -> {
            Board board1 = boardFactory.createBoard(side);
            board1.set(calcRow(vacancy), calcColumn(vacancy), player);
            if (board.isWinner(calcRow(vacancy), calcColumn(vacancy), player)) winner = player;
            return getWinner() != null;
        };
    }

    private Optional<Integer> find(Predicate<Integer> move) {
        return vacancies.stream().
                filter(move).
                findFirst();
    }

    private List<Integer> getVacancies(Player[] board) {
        return IntStream.range(0, board.length).
                filter(index -> board[index] == null).boxed().
                collect(Collectors.toList());
    }

    private int calcColumn(int vacancy) {
        return vacancy - (calcRow(vacancy) * side);
    }

    private int calcRow(int vacancy) {
        int row = 0;
        while (vacancy >= side) {
            vacancy -= side;
            row++;
        }
        return row;
    }
}
