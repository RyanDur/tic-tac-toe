package tictactoe;

public interface ComputerPlayer {
    void setPiece(String piece);

    String getPiece();

    Board calculateBestMove(Board board);
}
