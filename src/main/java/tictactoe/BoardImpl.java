package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;

import java.util.Arrays;
import java.util.List;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardImpl implements Board {
    private int side;
    private String[] board;
    private String winner;

    public BoardImpl() {
    }

    private BoardImpl(int side, String[] board) {
        this.side = side;
        this.board = board;
    }

    @Override
    public void setup(int side) {
        this.side = side;
        winner = null;
        board = new String[side * side];
    }

    @Override
    public boolean gameOver() {
        return getWinner() != null || numOfPieces() == getBoard().length;
    }

    @Override
    public void set(int row, int column, String piece) throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        if (outOfBounds(row, column)) throw new OutOfBoundsException();
        if (!validTurn(piece)) throw new OutOfTurnException();
        if (!empty(get(row, column))) throw new NotVacantException();
        board[calc(row, column)] = piece;
        if (isWinner(row, column, piece)) winner = piece;
    }

    @Override
    public String getWinner() {
        return winner;
    }

    @Override
    public String[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    @Override
    public List<List<Integer>> getVacancies() {
        String[] board = getBoard();
        return IntStream.range(0, board.length)
                .filter(index -> empty(board[index])).boxed()
                .map(num -> Arrays.asList(calcRow(num), calcColumn(num)))
                .collect(Collectors.toList());
    }

    @Override
    public int numOfPieces() {
        return (int) Arrays.stream(getBoard())
                .filter(piece -> !empty(piece))
                .count();
    }

    @Override
    public Board copy() {
        return new BoardImpl(side, getBoard());
    }

    private String get(int row, int column) {
        return board[calc(row, column)];
    }

    private boolean isWinner(int row, int column, String piece) {
        return check(row(piece, row)) ||
                check(column(piece, column)) ||
                check(leftDiagonal(piece)) ||
                check(rightDiagonal(piece));
    }

    private boolean check(IntPredicate vector) {
        return side == IntStream.range(0, side)
                .filter(vector).count();
    }

    private IntPredicate rightDiagonal(String piece) {
        return index -> piece.equals(get(index, (side - 1) - index));
    }

    private IntPredicate leftDiagonal(String piece) {
        return index -> piece.equals(get(index, index));
    }

    private IntPredicate column(String piece, int column) {
        return index -> piece.equals(get(index, column));
    }

    private IntPredicate row(String piece, int row) {
        return index -> piece.equals(get(row, index));
    }

    private boolean validTurn(String piece) {
        int numOfPieces = numOfPieces();
        return (numOfPieces % 2 == 0 && piece.equals(constants.GAME_PIECE_ONE)) ||
                (numOfPieces % 2 != 0 && piece.equals(constants.GAME_PIECE_TWO));
    }

    private boolean empty(String space) {
        return space == null;
    }

    private boolean outOfBounds(int row, int column) {
        return row > side - 1 || column > side - 1 || row < 0 || column < 0;
    }

    private int calc(int row, int column) {
        return (row * side) + column;
    }

    private int calcColumn(int vacancy) {
        return vacancy - (calcRow(vacancy) * side);
    }

    private int calcRow(int vacancy) {
        int row = 0;
        while (vacancy >= side) {
            vacancy -= side;
            row++;
        }
        return row;
    }
}
