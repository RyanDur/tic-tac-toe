package factories;

import com.google.inject.Inject;
import models.ComputerPlayer;
import models.ComputerPlayerImpl;
import models.Player;
import models.PlayerImpl;

public class PlayerFactoryImpl implements PlayerFactory {

    private StrategyGameFactory strategyGameFactory;

    @Inject
    public PlayerFactoryImpl(StrategyGameFactory strategyGameFactory) {
        this.strategyGameFactory = strategyGameFactory;
    }

    @Override
    public Player createPlayer(String gamePiece, int side) {
        return new PlayerImpl(gamePiece, side);
    }

    @Override
    public ComputerPlayer createComputerPlayer(String gamePiece, int side, Player opponent) {
        return new ComputerPlayerImpl(gamePiece, side, opponent,  strategyGameFactory);
    }


}
