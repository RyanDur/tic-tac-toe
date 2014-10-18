package exceptions;

import lang.constants;

public class OutOfBoundsException extends Exception {

    public OutOfBoundsException() {
        super(constants.OUT_OF_BOUNDS_MESSAGE);
    }
}
