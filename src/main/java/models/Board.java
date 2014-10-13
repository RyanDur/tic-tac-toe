package models;

public interface Board {
    void place(int x, int y, Player player);

    int getNumOfPieces();

    boolean isVacant(int x, int y);
}
