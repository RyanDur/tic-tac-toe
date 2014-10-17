package models;

import lang.constants;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlayerTest {

    private Player player;

    @Before
    public void setup() {
        player = new PlayerImpl(constants.GAME_PIECE_ONE);
    }

    @Test
    public void shouldHaveAGamePiece() {
        assertThat(player.getPiece(), is(equalTo(constants.GAME_PIECE_ONE)));
    }

    @Test
    public void shouldBeAbleToSetAnXCoordinate() {
        int coordinate = 2;
        player.setX(coordinate);
        assertThat(player.getX(), is(equalTo(coordinate)));
    }

    @Test
    public void shouldBeAbleToSetAYCoordinate() {
        int coordinate = 1;
        player.setY(coordinate);
        assertThat(player.getY(), is(equalTo(coordinate)));
    }
}