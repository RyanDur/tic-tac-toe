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
}
