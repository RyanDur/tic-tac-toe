package models;

public interface StrategyGame extends Game {
    boolean boardEmpty();

    Player getBestMove(Player player);
}
