package models;

public interface StrategyGame extends Game {
    boolean boardEmpty();

    boolean winningMove(int row, int column);

    boolean losingMove(int row, int column, Player opponent);
}
