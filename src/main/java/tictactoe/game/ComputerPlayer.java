package tictactoe.game;

public interface ComputerPlayer {
    void setPiece(Character piece);

    Character getPiece();

    Board calculateBestMove(Board board);
}