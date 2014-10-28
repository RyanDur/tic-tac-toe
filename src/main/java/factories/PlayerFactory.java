package factories;

import controllers.StrategyGameCtrl;
import models.ComputerPlayer;
import models.Player;

public interface PlayerFactory {

    Player createPlayer(String gamePiece, int side);

    ComputerPlayer createComputerPlayer(String gamePiece, int side, Player opponent, StrategyGameCtrl strategyGameCtrl);
}
