package controllers;

import factories.GameFactory;
import models.Game;
import models.Player;

public class GameCtrlImpl implements GameCtrl {
    GameFactory gameFactory;
    Game game;

    public GameCtrlImpl(GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    @Override
    public void setup(Player player1, Player player2) {
        game = gameFactory.createGame(player1, player2);
    }
}
