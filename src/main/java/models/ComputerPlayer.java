package models;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;

public interface ComputerPlayer extends Player {

    void calculateBestMove(Player[] players) throws OutOfBoundsException, NotVacantException;
}
