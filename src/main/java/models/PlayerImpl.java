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
    public void setCoordinates(int x, int y) throws OutOfBoundsException {
        if(x >= boundary || x < 0 || y >= boundary || y < 0) throw new OutOfBoundsException();
        this.x = x;
        this.y = y;
    }
}
