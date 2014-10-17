package models;

import exceptions.OutOfBoundsException;
import lang.constants;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class PlayerTest {

    private Player player;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup() {
        player = new PlayerImpl(constants.GAME_PIECE_ONE, constants.SIDE);
    }

    @Test
    public void shouldHaveAGamePiece() {
        assertThat(player.getPiece(), is(equalTo(constants.GAME_PIECE_ONE)));
    }

    @Test
    public void shouldBeAbleToSetXCoordinate() throws OutOfBoundsException {
        int coordinate = 2;
        player.setCoordinates(coordinate, coordinate);
        assertThat(player.getX(), is(equalTo(coordinate)));
    }

    @Test
    public void shouldNotBeAbleToSetAnXCoordinateLargerThanTheConfinesOfTheBoard() throws OutOfBoundsException {
        exception.expect(OutOfBoundsException.class);
        player.setCoordinates(5, 1);
    }

    @Test
    public void shouldNotBeAbleToSetAnXCoordinateLessThanTheConfinesOfTheBoard() throws OutOfBoundsException {
        exception.expect(OutOfBoundsException.class);
        player.setCoordinates(-1, 2);
    }

    @Test
    public void shouldBeAbleToSetAYCoordinate() throws OutOfBoundsException {
        int coordinate = 1;
        player.setCoordinates(coordinate, coordinate);
        assertThat(player.getY(), is(equalTo(coordinate)));
    }

    @Test
    public void shouldNotBeAbleToSetAnYCoordinateLargerThanTheConfinesOfTheBoard() throws OutOfBoundsException {
        exception.expect(OutOfBoundsException.class);
        player.setCoordinates(2, 4);
    }

    @Test
    public void shouldNotBeAbleToSetAnYCoordinateLessThanTheConfinesOfTheBoard() throws OutOfBoundsException {
        exception.expect(OutOfBoundsException.class);
        player.setCoordinates(2, -2);
    }
}