package exceptions;

import lang.constants;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class NotVacantExceptionTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldReceiveTheAppropriateMessageWhenThrown() throws NotVacantException {
        exception.expectMessage(constants.NOT_VACANT_MESSAGE);
        throw new NotVacantException();
    }
}