package tictactoe;

import java.util.List;

public interface ComputerPlayer {
    void setPiece(Character piece);

    List<Integer> getMove(Game Game);
}
