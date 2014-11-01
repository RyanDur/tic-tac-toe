package tictactoe;

public interface ComputerPlayer {
    Board calculateBestMove(Board board);

    void setPiece(String piece);

    String getPiece();
}
