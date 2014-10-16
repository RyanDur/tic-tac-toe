package models;

public interface Board {
    void set(Player player);

    int getNumOfPieces();

    boolean isVacant(int x, int y);

    Player get(int x, int y);

    boolean winner();

    boolean full();
}
