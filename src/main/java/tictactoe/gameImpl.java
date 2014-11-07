package tictactoe;

import com.google.inject.Inject;
import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.lang.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class GameImpl implements Game {
    private int side;
    private Character[] board;
    private Character winner;
    private ComputerPlayer computer;
    private Character computerPiece;

    private GameImpl(int side, Character[] board) {
        this.side = side;
        this.board = board;
        computerPiece = null;
    }

    @Inject
    public GameImpl(ComputerPlayer computer) {
        this.computer = computer;
    }

    @Override
    public void setup(Character piece, int side) {
        computerPiece = piece;
        computer.setPiece(computerPiece);
        winner = null;
        this.side = side;
        board = new Character[side * side];
        if (computersTurn()) computerMove();
    }

    @Override
    public void set(int row, int column) throws NotVacantException, OutOfBoundsException {
        if (outOfBounds(row, column)) throw new OutOfBoundsException();
        if (!isEmpty(get(row, column))) throw new NotVacantException();
        Character piece = getPiece();
        board[calc(row, column)] = piece;
        if (isWinner(row, column, piece)) winner = piece;
        if (!isOver() && computersTurn()) computerMove();
    }

    @Override
    public boolean isOver() {
        return getWinner() != null || numOfPieces() == getBoard().length;
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
    public Game copy() {
        return new GameImpl(side, getBoard());
    }

    private int numOfPieces() {
        return (int) Arrays.stream(getBoard())
                .filter(piece -> !isEmpty(piece))
                .count();
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

    private Character getPiece() {
        return numOfPieces() % 2 == 0 ? Constants.GAME_PIECE_ONE : Constants.GAME_PIECE_TWO;
    }

    private boolean computersTurn() {
        return getPiece().equals(computerPiece);
    }

    private void computerMove() {
        try {
            List<Integer> move = computer.getMove(this);
            set(move.get(0), move.get(1));
        } catch (OutOfBoundsException | NotVacantException e) {
            e.printStackTrace();
        }
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
