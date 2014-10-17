package models;

import exceptions.OutOfBoundsException;

public class PlayerImpl implements Player {

    private final String piece;
    private final int boundary;
    private int x;
    private int y;

    public PlayerImpl(String gamePiece, int boundary) {
        this.boundary = boundary;
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
        if(coordinate >= boundary || coordinate < 0) throw new OutOfBoundsException();
        x = coordinate;
    }

    @Override
    public void setY(int coordinate) throws OutOfBoundsException {
        if(coordinate >= boundary || coordinate < 0) throw new OutOfBoundsException();
        y = coordinate;
    }
}
