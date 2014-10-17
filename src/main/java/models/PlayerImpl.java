package models;

public class PlayerImpl implements Player {

    private final String piece;

    public PlayerImpl(String gamePiece) {
        piece = gamePiece;
    }

    @Override
    public int getX() {
        return 0;
    }

    @Override
    public int getY() {
        return 0;
    }

    @Override
    public String getPiece() {
        return piece;
    }
}
