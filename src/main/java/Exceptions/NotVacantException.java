package exceptions;

import lang.constants;

public class NotVacantException extends Exception {
    public NotVacantException() {
        super(constants.NOT_VACANT_MESSAGE);
    }
}
