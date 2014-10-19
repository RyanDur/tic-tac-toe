package models;

import exceptions.OutOfBoundsException;
import factories.StrategyGameFactory;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComputerPlayerImpl extends PlayerImpl implements ComputerPlayer {
    private int boundary;
    private StrategyGameFactory strategyGameFactory;
    private StrategyGame strategyGame;
    private Player[] players;
    private boolean winner;
    private List<Integer> vacancies;

    public ComputerPlayerImpl(String gamePiece, int boundary, StrategyGameFactory strategyGameFactory) {
        super(gamePiece, boundary);
        this.boundary = boundary;
        this.strategyGameFactory = strategyGameFactory;
        winner = false;
    }

    @Override
    public void setBoard(Player[] players) {
        this.players = players;
        strategyGame = strategyGameFactory.createStrategyGame(boundary, players);
    }

    @Override
    public void calculateBestMove() throws OutOfBoundsException {
        if (strategyGame.boardEmpty()) setCoordinates(boundary - 1, boundary - 1);
        else {
            vacancies = getVacancies();
            find(winningMove());
            if (!winner) {
                find(losingMove(getOpponent()));
            }
        }
    }

    private Player getOpponent() {
        return Arrays.stream(players).
                filter(player -> player != this && player != null).
                findFirst().get();
    }

    private Predicate<Integer> losingMove(Player opponent) {
        return vacancy -> strategyGame.losingMove(calcRow(vacancy), calcColumn(vacancy), opponent);
    }

    private Predicate<Integer> winningMove() {
        return vacancy -> {
            winner = strategyGame.winningMove(calcRow(vacancy), calcColumn(vacancy));
            return winner;
        };
    }

    private void find(Predicate<Integer> move) throws OutOfBoundsException {
        vacancies.stream().filter(move).
                findFirst().
                ifPresent(setVacancy());
    }

    private Consumer<Integer> setVacancy() {
        return vacancy -> {
            try {
                setCoordinates(calcRow(vacancy), calcColumn(vacancy));
            } catch (OutOfBoundsException e) {
                e.printStackTrace();
            }
        };
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

    private List<Integer> getVacancies() {
        return IntStream.range(0, players.length).
                filter(index -> players[index] == null).boxed().
                collect(Collectors.toList());
    }
}
