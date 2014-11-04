package tictactoe.exceptions;

import tictactoe.lang.Constants;

public class OutOfBoundsException extends Exception {

    public OutOfBoundsException() {
        super(Constants.OUT_OF_BOUNDS_MESSAGE);
    }
}
