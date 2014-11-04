package tictactoe.exceptions;

import tictactoe.lang.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class OutOfTurnExceptionTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReceiveTheAppropriateMessageWhenThrown() throws OutOfTurnException {
        exception.expectMessage(Constants.OUT_OF_TURN_MESSAGE);
        throw new OutOfTurnException();
    }
}