package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StrategyGameImpl extends GameImpl implements StrategyGame {

    private final int boundary;

    public StrategyGameImpl(int side, Player[] players) {
        super(side);
        boundary = side;
        setBoard(players);
    }

    @Override
    public boolean boardEmpty() {
        return getNumOfPieces() == 0;
    }

    @Override
    public Optional<Integer> findWinningMove(ComputerPlayer computerPlayer) {
        return null;
    }

    @Override
    public Optional<Integer> findLosingMove(Player opponent) {
        return null;
    }

    @Override
    public Optional<Integer> getBestMove(ComputerPlayer player, Player opponent) throws NotVacantException, OutOfBoundsException {
        List<StrategyGame> games = generatePossibleMoves(getBoard(), player);
        List<Integer> vacancies = getVacancies(getBoard());
        List<Integer> weights = games.stream().map(game -> getWeight(game.getBoard(), player, opponent)).collect(Collectors.toList());
        System.out.println(weights);
        System.out.println(vacancies);
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

                if (player.getPiece().equals("X")) {
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
        return vacancy -> losingMove(calcRow(vacancy), calcColumn(vacancy), opponent);
    }

    private Predicate<Integer> winningMove() {
        return vacancy -> winningMove(calcRow(vacancy), calcColumn(vacancy));
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

    private boolean winningMove(int row, int column) {
        return false;
    }

    private boolean losingMove(int row, int column, Player opponent) {
        return false;
    }

}
