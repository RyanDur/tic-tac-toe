package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StrategyGameImpl extends GameImpl implements StrategyGame {

    private final int side;
    private Player[] board;
    private List<Integer> vacancies;
    private int vacancy;
    private int weight;
    private Player winner;

    public StrategyGameImpl(int side, Player[] board) {
        super(side);
        this.side = side;
        this.board = board;
        vacancies = getVacancies(board);
    }

    @Override
    public boolean boardEmpty() {
        return countPieces(board) == 0;
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
        this.vacancy = vacancy;
        board[vacancy] = player;
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
        Stream<StrategyGame> wins = filterWins(this, player);
                wins.forEach(board -> board.setWeight(getWeight(board, player, opponent)));
        return Optional.of(wins.
                max((game1, game2) -> game1.getWeight() - game2.getWeight()).
                get().
                getSpace());
    }

    private int getWeight(StrategyGame game, Player player, Player opponent) {
        int result = 0;
            System.out.println(filterWins(game, opponent).count());
//                forEach(board -> board.setWeight(getWeight(board, player, opponent)));
        return result;
    }

    private Stream<StrategyGame> filterWins(StrategyGame game, Player player) {
        return generatePossibleMoves(player, getVacancies(game.getBoard())).stream().
                filter(board -> board.findWinningMove(player).isPresent());
    }


    private List<StrategyGame> generatePossibleMoves(Player player, List<Integer> vacancies) {
        List<StrategyGame> games = new ArrayList<>();

        for (Integer vacancy : vacancies) {
            StrategyGame game = new StrategyGameImpl(side, Arrays.copyOf(board, board.length));
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
            Player[] copy = Arrays.copyOf(board, board.length);
            copy[vacancy] = player;
            if (isWinner(player, vacancy, copy)) winner = player;
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

    private boolean isWinner(Player player, int vacancy, Player[] board) {
        boolean result = check(row(board, player, calcRow(vacancy))) ||
                check(column(board, player, calcColumn(vacancy)));
        if (!result && leftDiagonallyPlaced(vacancy))
            result = check(leftDiagonal(board, player));
        if (!result && rightDiagonallyPlaced(vacancy))
            result = check(rightDiagonal(board, player));
        return result;
    }

    private boolean leftDiagonallyPlaced(int vacancy) {
        return center(vacancy) || topLeft(vacancy) || bottomRight(vacancy);
    }

    private boolean rightDiagonallyPlaced(int vacancy) {
        return center(vacancy) || bottomLeft(vacancy) || topRight(vacancy);
    }

    private boolean topRight(int vacancy) {
        return calcRow(vacancy) == side - 1 && calcColumn(vacancy) == 0;
    }

    private boolean bottomLeft(int vacancy) {
        return calcRow(vacancy) == 0 && calcColumn(vacancy) == side - 1;
    }

    private boolean bottomRight(int vacancy) {
        return calcRow(vacancy) == side - 1 && calcColumn(vacancy) == side - 1;
    }

    private boolean topLeft(int vacancy) {
        return calcRow(vacancy) == 0 && calcColumn(vacancy) == 0;
    }

    private boolean center(int vacancy) {
        int x = calcRow(vacancy);
        int y = calcColumn(vacancy);
        return x > 0 && x < side - 1 && y > 0 && y < side - 1;
    }

    private boolean check(IntPredicate predicate) {
        return side == IntStream.range(0, side).
                filter(predicate).count();
    }

    private IntPredicate rightDiagonal(Player[] board, Player player) {
        return i -> board[calculate(i, (side - 1) - i)] == player;
    }

    private IntPredicate leftDiagonal(Player[] board, Player player) {
        return i -> board[calculate(i, i)] == player;
    }

    private IntPredicate column(Player[] board, Player player, int y) {
        return i -> board[calculate(i, y)] == player;
    }

    private IntPredicate row(Player[] board, Player player, int x) {
        return i -> board[calculate(x, i)] == player;
    }

    private int calculate(int x, int y) {
        return (x * side) + y;
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
