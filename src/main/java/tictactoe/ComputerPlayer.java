package tictactoe;

public interface ComputerPlayer {
    void calculateBestMove(Board board);

    void setPiece(String piece);

    int getRow();

    int getColumn();

    String getPiece();
}
