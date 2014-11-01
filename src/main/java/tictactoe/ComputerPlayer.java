package tictactoe;

public interface ComputerPlayer {
    java.util.Optional<java.util.List<Integer>> calculateBestMove(Board board);

    void setPiece(String piece);

    String getPiece();
}
