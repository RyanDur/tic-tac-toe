package models;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StrategyBoardImpl extends BoardImpl implements StrategyBoard {
    private int side;

    public StrategyBoardImpl(int side, Player[] board) {
        super(side);
        this.side = side;
        setBoard(board);
    }

    @Override
    public List<Integer[]> getVacancies() {
        Player[] board = getBoard();
        return IntStream.range(0, board.length).
                filter(index -> board[index] == null).boxed().
                map(num -> new Integer[]{calcRow(num), calcColumn(num)}).
                collect(Collectors.toList());
    }

    @Override
    public Optional<Integer[]> winningMove(Player player) {
        return find(winMove(player));
    }

    @Override
    public List<Integer[]> filterMoves(Player player) {
        List<Integer[]> moves = getVacancies();
        moves = generatePossibleMoves(player, moves).stream()
                .filter(game -> game.winningMove(player).isPresent())
                .map(game -> game.winningMove(player).get())
                .collect(Collectors.toList());
        if(moves.isEmpty()) moves = getVacancies();
        return moves;
    }

    private List<StrategyBoard> generatePossibleMoves(Player player, List<Integer[]> vacancies) {
        return vacancies.stream()
                .map(vacancy -> playVacancy(player, vacancy))
                .collect(Collectors.toList());
    }

    private Optional<Integer[]> find(Predicate<Integer[]> move) {
        return getVacancies().stream().
                filter(move).
                findFirst();
    }

    private Predicate<Integer[]> winMove(Player player) {
        return vacancy -> playVacancy(player, vacancy).getWinner() == player;
    }

    private StrategyBoard playVacancy(Player player, Integer[] vacancy) {
        StrategyBoard strategyBoard = new StrategyBoardImpl(side, getBoard());
        strategyBoard.set(vacancy[0], vacancy[1], player);
        return strategyBoard;
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