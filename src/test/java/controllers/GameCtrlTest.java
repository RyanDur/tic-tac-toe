package controllers;

import factories.GameFactory;
import models.Game;
import models.Player;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

public class GameCtrlTest {
    private GameCtrl gameCtrl;
    private GameFactory gameFactory;

    @Before
    public void setup() {
        gameFactory = mock(GameFactory.class);
        gameCtrl = new GameCtrlImpl(gameFactory);
    }

    @Test
    public void shouldBeAbleToSetupAGame() {
        Player player1 = mock(Player.class);
        Player player2 = mock(Player.class);
        Game mockGame = mock(Game.class);
        when(gameFactory.createGame(player1, player2)).thenReturn(mockGame);
        
        gameCtrl.setup(player1, player2);
        verify(gameFactory).createGame(player1, player2);
    }
}
