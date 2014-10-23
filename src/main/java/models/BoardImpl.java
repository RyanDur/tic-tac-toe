package models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class BoardImpl implements Board {
    private final int side;
    private Player[] board;
    private Integer[] lastMove;

    public BoardImpl(int side) {
        this.board = new Player[side * side];
        this.side = side;
    }

    @Override
    public void set(int row, int column, Player player) {
        lastMove = new Integer[]{row, column};
        board[calculate(row, column)] = player;
    }

    @Override
    public Player get(int row, int column) {
        return board[calculate(row, column)];
    }

    @Override
    public void setBoard(Player[] board) {
        this.board = board;
    }

    @Override
    public Player[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    @Override
    public boolean isWinner(int row, int column, Player player) {
        boolean result = check(row(board, player, row)) ||
                check(column(board, player, column));
        if (!result && leftDiagonallyPlaced(row, column))
            result = check(leftDiagonal(board, player));
        if (!result && rightDiagonallyPlaced(row, column))
            result = check(rightDiagonal(board, player));
        return result;
    }

    @Override
    public List<Integer[]> getVacancies() {
        return IntStream.range(0, board.length).
                filter(index -> board[index] == null).boxed().
                map(num -> new Integer[]{calcRow(num), calcColumn(num)}).
                collect(Collectors.toList());
    }

    @Override
    public Integer[] lastMove() {
        return lastMove;
    }

    @Override
    public Optional<Integer[]> winningMove(Player player) {
        return find(winningMove(player, this), getVacancies());
    }

    @Override
    public Stream<Board> filterMoves(Player player) {
        return generatePossibleMoves(player, getVacancies()).stream().
                filter(game -> find(winningMove(player, game), getVacancies()).isPresent());
    }

    @Override
    public boolean detectCatsGame() {
        return 0 == getPlayers().map(player -> {
            List<Integer[]> options = filterMoves(player).map(Board::lastMove).collect(Collectors.toList());
            Optional<Integer[]> option = winningMove(player);
            if(option.isPresent()) options.add(option.get());
            return options.size();
        }).reduce(0, (a,b) -> a + b);
    }

    private Stream<Player> getPlayers() {
        return Arrays.stream(board).
                distinct().
                filter(player -> player != null);
    }

    private List<Board> generatePossibleMoves(Player player, List<Integer[]> vacancies) {
        List<Board> games = new ArrayList<>();
        vacancies.stream().forEach(vacancy -> games.add(setVacancy(player, this, vacancy)));
        return games;
    }

    private Optional<Integer[]> find(Predicate<Integer[]> move, List<Integer[]> vacancies) {
        return vacancies.stream().
                filter(move).
                findFirst();
    }

    private Predicate<Integer[]> winningMove(Player player, Board board) {
        return vacancy -> setVacancy(player, board, vacancy)
                .isWinner(vacancy[0], vacancy[1], player);
    }

    private Board setVacancy(Player player, Board board, Integer[] vacancy) {
        Board copy = new BoardImpl(side);
        copy.setBoard(board.getBoard());
        copy.set(vacancy[0], vacancy[1], player);
        return copy;
    }

    private boolean leftDiagonallyPlaced(int x, int y) {
        return center(x, y) || topLeft(x, y) || bottomRight(x, y);
    }

    private boolean rightDiagonallyPlaced(int x, int y) {
        return center(x, y) || bottomLeft(x, y) || topRight(x, y);
    }

    private boolean topRight(int x, int y) {
        return x == side - 1 && y == 0;
    }

    private boolean bottomLeft(int x, int y) {
        return x == 0 && y == side - 1;
    }

    private boolean bottomRight(int x, int y) {
        return x == side - 1 && y == side - 1;
    }

    private boolean topLeft(int x, int y) {
        return x == 0 && y == 0;
    }

    private boolean center(int x, int y) {
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
