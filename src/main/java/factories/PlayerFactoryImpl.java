package factories;

import controllers.StrategyBoardCtrl;
import models.ComputerPlayer;
import models.ComputerPlayerImpl;
import models.Player;
import models.PlayerImpl;

public class PlayerFactoryImpl implements PlayerFactory {

    @Override
    public Player createPlayer(String gamePiece, int side) {
        return new PlayerImpl(gamePiece, side);
    }

    @Override
    public ComputerPlayer createComputerPlayer(String gamePiece, int side, Player opponent, StrategyBoardCtrl strategyBoardCtrl) {
        return new ComputerPlayerImpl(gamePiece, side, opponent, strategyBoardCtrl);
    }


}
