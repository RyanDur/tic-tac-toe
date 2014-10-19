package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

import java.util.Optional;

public interface StrategyGame extends Game {
    boolean boardEmpty();

    Optional<Integer> findWinningMove(ComputerPlayer player);

    Optional<Integer> findLosingMove(Player opponent);

    Optional<Integer> getBestMove(ComputerPlayer player, Player opponent) throws NotVacantException, OutOfBoundsException;
}
