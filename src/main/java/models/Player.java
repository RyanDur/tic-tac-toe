package models;

import exceptions.OutOfBoundsException;

public interface Player {
    int getX();

    int getY();

    String getPiece();

    void setCoordinates(int x, int y) throws OutOfBoundsException;
}
