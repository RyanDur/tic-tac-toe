package tictactoe.exceptions;

import tictactoe.lang.Constants;

public class InvalidMoveException extends Throwable {

    public InvalidMoveException() {
        super(Constants.INVALID_MOVE);
    }
}
