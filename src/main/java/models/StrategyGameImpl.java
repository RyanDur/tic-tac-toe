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

public class StrategyGameImpl extends GameImpl implements StrategyGame {

    private final int boundary;
    private Player[] board;

    public StrategyGameImpl(int side, Player[] board) {
        super(side);
        boundary = side;
        this.board = board;
        setBoard(board);
    }

    @Override
    public boolean boardEmpty() {
        return getNumOfPieces() == 0;
    }

    @Override
    public Optional<Integer> findWinningMove(Player player) {
        return find(getBoard(), winningMove(player));
    }

    @Override
    public Optional<Integer> findLosingMove(Player opponent) {
        return find(getBoard(), losingMove(opponent));
    }

    @Override
    public Optional<Integer> getBestMove(ComputerPlayer player, Player opponent) throws NotVacantException, OutOfBoundsException {
        List<StrategyGame> games = generatePossibleMoves(getBoard(), player);
        List<Integer> vacancies = getVacancies(getBoard());
        List<Integer> weights = games.stream().map(game -> getWeight(game.getBoard(), player, opponent)).collect(Collectors.toList());
        vacancies.get(findMaxIndex(weights));
        return null;
    }

    private Integer getWeight(Player[] board, Player player, Player opponent) {
        int result = 0;
        try {
            List<StrategyGame> games;
            games = generatePossibleMoves(board, player);
            for (StrategyGame game : games) {
                if (game.getWinner() == this) result += 1;
                else if (game.getWinner() == opponent) result += -1;

                if (player instanceof ComputerPlayer) {
                    result += getWeight(getBoard(), player, opponent);
                } else {
                    result += getWeight(getBoard(), player, opponent);
                }
            }
        } catch (OutOfBoundsException | NotVacantException e) {
            e.printStackTrace();
        }
        return result;
    }

    private int findMaxIndex(List<Integer> list) {
        long max = 0;
        int index = 0;
        for (int i = 0; i < list.size(); i++) {
            if (max < list.get(i)) {
                max = list.get(i);
                index = i;
            }
        }
        return index;
    }

    private Predicate<Integer> losingMove(Player opponent) {
        return vacancy -> losingMove(vacancy, opponent);
    }

    private Predicate<Integer> winningMove(Player player) {
        return vacancy -> win(vacancy, player);
    }

    private Optional<Integer> find(Player[] board, Predicate<Integer> move) {
        return getVacancies(board).stream().
                filter(move).
                findFirst();
    }

    private List<StrategyGame> generatePossibleMoves(Player[] board, Player player) throws OutOfBoundsException, NotVacantException {
        List<StrategyGame> games = new ArrayList<>();
        for (Integer vacancy : getVacancies(board)) {
            StrategyGame game = new StrategyGameImpl(boundary, Arrays.copyOf(board, board.length));
            player.setCoordinates(calcRow(vacancy), calcColumn(vacancy));
            game.set(player);
            games.add(game);

        }
        return games;
    }

    private List<Integer> getVacancies(Player[] board) {
        return IntStream.range(0, board.length).
                filter(index -> board[index] == null).boxed().
                collect(Collectors.toList());
    }

    private boolean win(int vacancy, Player player) {
        board = getBoard();
        board[vacancy] = player;
        return isWinner(player, vacancy);
    }

    private boolean losingMove(int vacancy, Player opponent) {
        board = getBoard();
        board[vacancy] = opponent;
        return isWinner(opponent, vacancy);
    }

    private boolean isWinner(Player player, int vacancy) {
        boolean result = check(row(player, calcRow(vacancy))) ||
                check(column(player,calcColumn(vacancy)));
        if (!result && leftDiagonallyPlaced(vacancy)) {
            result = check(leftDiagonal(player));
        }
        if (!result && rightDiagonallyPlaced(vacancy)) {
            result = check(rightDiagonal(player));
        }
        return result;
    }

    private boolean leftDiagonallyPlaced(int vacancy) {
        return center(vacancy) || topLeft(vacancy) || bottomRight(vacancy);
    }

    private boolean rightDiagonallyPlaced(int vacancy) {
        return center(vacancy) || bottomLeft(vacancy) || topRight(vacancy);
    }

    private boolean topRight(int vacancy) {
        return calcRow(vacancy) == boundary - 1 && calcColumn(vacancy) == 0;
    }

    private boolean bottomLeft(int vacancy) {
        return calcRow(vacancy) == 0 && calcColumn(vacancy) == boundary - 1;
    }

    private boolean bottomRight(int vacancy) {
        return calcRow(vacancy) == boundary - 1 && calcColumn(vacancy) == boundary - 1;
    }

    private boolean topLeft(int vacancy) {
        return calcRow(vacancy) == 0 && calcColumn(vacancy) == 0;
    }

    private boolean center(int vacancy) {
        int x = calcRow(vacancy);
        int y = calcColumn(vacancy);
        return x > 0 && x < boundary - 1 && y > 0 && y < boundary - 1;
    }

    private boolean check(IntPredicate predicate) {
        return boundary == IntStream.range(0, boundary).
                filter(predicate).count();
    }

    private IntPredicate rightDiagonal(Player player) {
        return i -> board[calculate(i, (boundary - 1) - i)] == player;
    }

    private IntPredicate leftDiagonal(Player player) {
        return i -> board[calculate(i, i)] == player;
    }

    private IntPredicate column(Player player, int y) {
        return i -> board[calculate(i, y)] == player;
    }

    private IntPredicate row(Player player, int x) {
        return i -> board[calculate(x, i)] == player;
    }

    private int calculate(int x, int y) {
        return (x * boundary) + y;
    }

    private int calcColumn(int vacancy) {
        return vacancy - (calcRow(vacancy) * boundary);
    }

    private int calcRow(int vacancy) {
        int row = 0;
        while (vacancy >= boundary) {
            vacancy -= boundary;
            row++;
        }
        return row;
    }
}
