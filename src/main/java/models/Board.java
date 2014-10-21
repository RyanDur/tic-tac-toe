package models;

import java.util.List;

public interface Board {

    void set(int row, int column, Player player);

    Player get(int row, int column);

    void setBoard(Player[] board);

    Player[] getBoard();

    boolean isWinner(int row, int column, Player player);

    List<Integer[]> getVacancies();

    Integer[] lastMove();
}
