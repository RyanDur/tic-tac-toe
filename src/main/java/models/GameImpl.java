package models;

import exceptions.NotVacantException;
import factories.BoardFactory;

import java.util.Arrays;

public class GameImpl implements Game {
    private Board board;
    private final int side;

    public GameImpl(int side, BoardFactory boardFactory) {
        this.side = side;
        board = boardFactory.createBoard(side);
    }

    @Override
    public void set(Player player) throws NotVacantException {
        board.set(player.getX(), player.getY(), player);
    }

    @Override
    public Player getWinner() {
        return board.getWinner();
    }

    @Override
    public boolean full() {
        return getNumOfPieces() == (side * side);
    }

    @Override
    public Player[] getBoard() {
        return board.getBoard();
    }

    @Override
    public int getNumOfPieces() {
        return (int) Arrays.stream(getBoard()).filter(player -> player != null).count();
    }
}