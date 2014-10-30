package models;

import exceptions.OutOfBoundsException;
import tictactoe.BoardImpl;
import exceptions.NotVacantException;
import exceptions.OutOfTurnException;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StrategyBoardImpl extends BoardImpl implements StrategyBoard {
    private int side;

    public StrategyBoardImpl(int side, String[] board) {
        super(side);
        this.side = side;
    }

    @Override
    public List<Integer[]> getVacancies() {
        String[] board = getBoard();
        return IntStream.range(0, board.length).
                filter(index -> board[index] == null).boxed().
                map(num -> new Integer[]{calcRow(num), calcColumn(num)}).
                collect(Collectors.toList());
    }

    @Override
    public Optional<Integer[]> winningMove(String player) {
        return find(winMove(player));
    }

    @Override
    public List<Integer[]> filterMoves(String player) {
        return generatePossibleMoves(player, getVacancies()).stream()
                .filter(game -> game.winningMove(player).isPresent())
                .map(game -> game.winningMove(player).get())
                .collect(Collectors.toList());
    }

    private List<StrategyBoard> generatePossibleMoves(String player, List<Integer[]> vacancies) {
        return vacancies.stream()
                .map(vacancy -> playVacancy(player, vacancy))
                .collect(Collectors.toList());
    }

    private Optional<Integer[]> find(Predicate<Integer[]> move) {
        return getVacancies().stream().
                filter(move).
                findFirst();
    }

    private Predicate<Integer[]> winMove(String player) {
        return vacancy -> player.equals(playVacancy(player, vacancy).getWinner());
    }

    private StrategyBoard playVacancy(String player, Integer[] vacancy) {
        StrategyBoard strategyBoard = new StrategyBoardImpl(side, getBoard());
        try {
            strategyBoard.set(vacancy[0], vacancy[1], player);
        } catch (NotVacantException | OutOfTurnException | OutOfBoundsException e) {
            e.printStackTrace();
        }
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