package tictactoe.exceptions;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import tictactoe.lang.Constants;

public class InvalidMoveExceptionTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldGiveTheProperMessage() throws InvalidMoveException {
        exception.expectMessage(Constants.INVALID_MOVE);
        throw new InvalidMoveException();
    }
}