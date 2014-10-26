package models;

public interface Board {

    void set(int row, int column, Player player);

    Player get(int row, int column);

    void setBoard(Player[] board);

    Player[] getBoard();

    Player getWinner();
}
