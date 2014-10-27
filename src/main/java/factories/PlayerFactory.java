package factories;

import controllers.StrategyCtrl;
import models.ComputerPlayer;
import models.Player;

public interface PlayerFactory {

    Player createPlayer(String gamePiece, int side);

    ComputerPlayer createComputerPlayer(String gamePiece, int side, Player opponent, StrategyCtrl strategyCtrl);
}
