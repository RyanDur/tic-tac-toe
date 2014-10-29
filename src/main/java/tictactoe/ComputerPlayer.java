package tictactoe;

public interface ComputerPlayer {
    void calculateBestMove(String[] board);

    void setPiece(String piece);

    int getRow();

    int getColumn();

    String getPiece();
}
