package tictactoe.exceptions;

import tictactoe.lang.Constants;

public class NotVacantException extends Exception {
    public NotVacantException() {
        super(Constants.NOT_VACANT_MESSAGE);
    }
}
