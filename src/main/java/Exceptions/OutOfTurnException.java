package exceptions;

import lang.constants;

public class OutOfTurnException extends Exception {

    public OutOfTurnException() {
        super(constants.OUT_OF_TURN_MESSAGE);
    }
}
