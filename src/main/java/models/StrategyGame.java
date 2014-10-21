package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

import java.util.Optional;

public interface StrategyGame extends Game {
    boolean boardEmpty();

    Optional<Integer> findWinningMove(Player player);

    Optional<Integer> findBestMove(Player player, Player opponent) throws NotVacantException, OutOfBoundsException;

    public void set(int vacancy, Player player);

    public int getSpace();

    public void setWeight(int weight);

    public int getWeight();
}
