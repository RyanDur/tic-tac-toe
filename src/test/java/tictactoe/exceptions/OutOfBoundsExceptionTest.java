package tictactoe.exceptions;

import tictactoe.lang.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OutOfBoundsExceptionTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReceiveTheAppropriateMessageWhenThrown() throws OutOfBoundsException {
        exception.expectMessage(Constants.OUT_OF_BOUNDS_MESSAGE);
        throw new OutOfBoundsException();
    }
}