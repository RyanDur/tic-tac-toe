package models;

import exceptions.OutOfBoundsException;

public interface ComputerPlayer extends Player {
    void setBoard(Player[] players);

    void calculateBestMove() throws OutOfBoundsException;
}
