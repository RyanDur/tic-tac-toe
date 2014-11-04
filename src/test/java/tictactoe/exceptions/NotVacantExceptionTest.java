package tictactoe.exceptions;

import tictactoe.lang.Constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NotVacantExceptionTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReceiveTheAppropriateMessageWhenThrown() throws NotVacantException {
        exception.expectMessage(Constants.NOT_VACANT_MESSAGE);
        throw new NotVacantException();
    }
}