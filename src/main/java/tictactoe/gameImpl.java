package tictactoe;

import com.google.inject.Inject;
import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.lang.Constants;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class GameImpl implements Game {
    private int side;
    private Character[] board;
    private Character winner;
    private ComputerPlayer computer;
    private Supplier<Boolean> isComputersTurn;

    private GameImpl(int side, Character[] board) {
        this.side = side;
        this.board = board;
        isComputersTurn = setComputer.apply(null);
    }

    @Inject
    public GameImpl(ComputerPlayer computer) {
        this.computer = computer;
    }

    @Override
    public void setup(Character piece, int side) {
        isComputersTurn = setComputer.apply(piece);
        computer.setPiece(piece);
        winner = null;
        this.side = side;
        board = new Character[side * side];
        if (isComputersTurn.get()) move.accept(computer);
    }

    @Override
    public void set(int row, int column) throws NotVacantException, OutOfBoundsException {
        if (outOfBounds.test(row, column)) throw new OutOfBoundsException();
        if (!isEmpty.test(get.apply(row, column))) throw new NotVacantException();
        Character piece = nextPiece.get();
        board[getIndex.apply(row, column)] = piece;
        if (isWinner(row, column, piece)) winner = piece;
        if (isComputersTurn.get() && !isOver()) move.accept(computer);
    }

    @Override
    public boolean isOver() {
        return getWinner() != null || numOfPieces.get() == getBoard().length;
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
        return IntStream.range(0, board.length).boxed()
                .filter(index -> isEmpty.test(board[index]))
                .map(getRowColumn::apply)
                .collect(Collectors.toSet());
    }

    @Override
    public Game copy() {
        return new GameImpl(side, getBoard());
    }

    private boolean isWinner(int row, int column, Character piece) {
        return getVectors.apply(row, column).parallel()
                .filter(vector -> check.apply(vector, piece))
                .findFirst().isPresent();
    }

    private Consumer<ComputerPlayer> move = computer -> {
        try {
            List<Integer> move = computer.getMove(this);
            set(move.get(0), move.get(1));
        } catch (OutOfBoundsException | NotVacantException e) {
            e.printStackTrace();
        }
    };

    private Function<Integer, List<Integer>> getRowColumn = vacancy -> {
        int row = Math.floorDiv(vacancy, side);
        int column = vacancy - (row * side);
        return Arrays.asList(row, column);
    };

    private Predicate<Character> isEmpty = space -> space == null;

    private BiPredicate<Integer, Integer> outOfBounds = (row, column) -> row > side - 1 || column > side - 1 || row < 0 || column < 0;

    private Supplier<Integer> numOfPieces = () -> (int) Arrays.stream(getBoard()).filter(isEmpty.negate()::test).count();

    private Supplier<Character> nextPiece = () -> numOfPieces.get() % 2 == 0 ? Constants.GAME_PIECE_ONE : Constants.GAME_PIECE_TWO;

    private Function<Character, Supplier<Boolean>> setComputer = piece -> () -> nextPiece.get().equals(piece);

    private BiFunction<Integer, Integer, Integer> getIndex = (row, column) -> (row * side) + column;

    private BiFunction<Integer, Integer, Character> get = (row, column) -> board[getIndex.apply(row, column)];

    private BiFunction<Function<Integer, Character>, Character, Boolean> check = (vector, piece) ->
            IntStream.range(0, side).allMatch(index -> piece.equals(vector.apply(index)));

    private BiFunction<Integer, Integer, Stream<Function<Integer, Character>>> getVectors = (row, column) ->
            Stream.of(index -> get.apply(row, index),
                    index -> get.apply(index, column),
                    index -> get.apply(index, index),
                    index -> get.apply(index, (side - 1) - index));
}