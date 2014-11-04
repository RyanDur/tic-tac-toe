package tictactoe.exceptions;

import tictactoe.lang.Constants;

public class OutOfTurnException extends Exception {

    public OutOfTurnException() {
        super(Constants.OUT_OF_TURN_MESSAGE);
    }
}
