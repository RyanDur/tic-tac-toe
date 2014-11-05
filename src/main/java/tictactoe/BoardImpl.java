package tictactoe;

import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;
import tictactoe.lang.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BoardImpl implements Board {
    private int side;
    private Character[] board;
    private Character winner;

    public BoardImpl() {
    }

    private BoardImpl(int side, Character[] board) {
        this.side = side;
        this.board = board;
    }

    @Override
    public void setup(int side) {
        this.side = side;
        winner = null;
        board = new Character[side * side];
    }

    @Override
    public boolean gameOver() {
        return getWinner() != null || numOfPieces() == getBoard().length;
    }

    @Override
    public void set(int row, int column, Character piece) throws NotVacantException, OutOfTurnException, OutOfBoundsException {
        if (outOfBounds(row, column)) throw new OutOfBoundsException();
        if (!validTurn(piece)) throw new OutOfTurnException();
        if (!isEmpty(get(row, column))) throw new NotVacantException();
        board[calc(row, column)] = piece;
        if (isWinner(row, column, piece)) winner = piece;
    }

    @Override
    public Character getWinner() {
        return winner;
    }

    @Override
    public Character[] getBoard() {
        return Arrays.copyOf(board, board.length);
    }

    @Override
    public Set<List<Integer>> getVacancies() {
        Character[] board = getBoard();
        return IntStream.range(0, board.length)
                .filter(index -> isEmpty(board[index])).boxed()
                .map(num -> Arrays.asList(calcRow(num), calcColumn(num)))
                .collect(Collectors.toSet());
    }

    @Override
    public int numOfPieces() {
        return (int) Arrays.stream(getBoard())
                .filter(piece -> !isEmpty(piece))
                .count();
    }

    @Override
    public Board copy() {
        return new BoardImpl(side, getBoard());
    }

    private Character get(int row, int column) {
        return board[calc(row, column)];
    }

    private boolean isWinner(int row, int column, Character piece) {
        return check(row(piece, row)) ||
                check(column(piece, column)) ||
                check(leftDiagonal(piece)) ||
                check(rightDiagonal(piece));
    }

    private boolean check(IntPredicate vector) {
        return side == IntStream.range(0, side)
                .filter(vector).count();
    }

    private IntPredicate rightDiagonal(Character piece) {
        return index -> piece.equals(get(index, (side - 1) - index));
    }

    private IntPredicate leftDiagonal(Character piece) {
        return index -> piece.equals(get(index, index));
    }

    private IntPredicate column(Character piece, int column) {
        return index -> piece.equals(get(index, column));
    }

    private IntPredicate row(Character piece, int row) {
        return index -> piece.equals(get(row, index));
    }

    private boolean validTurn(Character piece) {
        int numOfPieces = numOfPieces();
        return (numOfPieces % 2 == 0 && Constants.GAME_PIECE_ONE.equals(piece)) ||
                (numOfPieces % 2 != 0 && Constants.GAME_PIECE_TWO.equals(piece));
    }

    private boolean isEmpty(Character space) {
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
