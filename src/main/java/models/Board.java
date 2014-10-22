package models;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public interface Board {

    void set(int row, int column, Player player);

    Player get(int row, int column);

    void setBoard(Player[] board);

    Player[] getBoard();

    boolean isWinner(int row, int column, Player player);

    List<Integer[]> getVacancies();

    Integer[] lastMove();

    Optional<Integer[]> winningMove(Player player);

    Stream<Board> filterMoves(Player player);

    boolean catsGame();
}
