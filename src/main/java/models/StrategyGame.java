package models;

public interface StrategyGame extends Game {
    boolean boardEmpty();

    void setBestMove(Player player);
}
