package models;

public interface Board {
    void set(int x, int y, Player player);

    int getNumOfPieces();

    boolean isVacant(int x, int y);

    Player get(int x, int y);
}
