package models;

import exceptions.OutOfBoundsException;

public interface Player {
    int getX();

    int getY();

    String getPiece();

    void setX(int coordinate) throws OutOfBoundsException;

    void setY(int coordinate);
}
