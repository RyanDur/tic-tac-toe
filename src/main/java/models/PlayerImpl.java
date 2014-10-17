package models;

import exceptions.OutOfBoundsException;
import lang.constants;

public class PlayerImpl implements Player {

    private final String piece;
    private int x;
    private int y;

    public PlayerImpl(String gamePiece) {
        piece = gamePiece;
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public String getPiece() {
        return piece;
    }

    @Override
    public void setX(int coordinate) throws OutOfBoundsException {
        if(coordinate >= constants.SIDE || coordinate < 0) throw new OutOfBoundsException();
        x = coordinate;
    }

    @Override
    public void setY(int coordinate) {
        y = coordinate;
    }
}
