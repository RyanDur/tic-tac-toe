package tictactoe;

public interface ComputerPlayer {
    void setPiece(Character piece);

    Character getPiece();

    java.util.List<Integer> getMove(Game Game);
}
