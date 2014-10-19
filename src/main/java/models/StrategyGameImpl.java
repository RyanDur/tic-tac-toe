package models;

public class StrategyGameImpl extends GameImpl implements StrategyGame {
    public StrategyGameImpl(int side, Player[] players) {
        super(side);
        setBoard(players);
    }

    @Override
    public boolean boardEmpty() {
        return getNumOfPieces() == 0;
    }

    @Override
    public boolean winningMove(int row, int column) {
        return false;
    }

    @Override
    public boolean losingMove(int row, int column, Player opponent) {
        return false;
    }
}
