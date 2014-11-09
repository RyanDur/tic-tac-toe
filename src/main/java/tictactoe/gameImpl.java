package tictactoe;

import com.google.inject.Inject;
import tictactoe.exceptions.InvalidMoveException;
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
    private Supplier<Boolean> ifComputersTurn;

    private GameImpl(int side, Character[] board) {
        this.side = side;
        this.board = board;
        ifComputersTurn = setComputer.apply(null);
    }

    @Inject
    public GameImpl(ComputerPlayer computer) {
        this.computer = computer;
    }

    @Override
    public void setup(Character piece, int side) {
        ifComputersTurn = setComputer.apply(piece);
        computer.setPiece(piece);
        winner = null;
        this.side = side;
        board = new Character[side * side];
        move.apply(ifComputersTurn).accept(computer);
    }

    @Override
    public void set(int row, int column) throws InvalidMoveException {
        Integer index = getIndex.apply(row, column);
        if (outOfBounds.or(isEmpty.negate()).test(index)) throw new InvalidMoveException();
        setPiece.andThen(checkForWin).accept(index, nextPiece.get());
        move.apply(ifComputersTurn).accept(computer);
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
        return IntStream.range(0, getBoard().length).boxed()
                .filter(isEmpty::test)
                .map(getRowColumn::apply)
                .collect(Collectors.toSet());
    }

    @Override
    public Game copy() {
        return new GameImpl(side, getBoard());
    }

    private Function<Supplier<Boolean>, Consumer<ComputerPlayer>> move = isComputersTurn -> computer -> {
        if (isComputersTurn.get() && !isOver()) {
            try {
                List<Integer> move = computer.getMove(this);
                set(move.get(0), move.get(1));
            } catch (InvalidMoveException e) {
                e.printStackTrace();
            }
        }
    };

    private Function<Integer, List<Integer>> getRowColumn = position -> {
        int row = Math.floorDiv(position, side);
        int column = position - (row * side);
        return Arrays.asList(row, column);
    };

    private Consumer<Character> setWinner = piece -> winner = piece;

    private Predicate<Integer> isEmpty = index -> board[index] == null;

    private Predicate<Integer> outOfBounds = index -> index > (side * side) || index < 0;

    private BiConsumer<Integer, Character> setPiece = (index, piece) -> board[index] = piece;

    private BiFunction<Integer, Integer, Integer> getIndex = (row, column) -> (row * side) + column;

    private Supplier<Integer> numOfPieces = () -> (int) IntStream.range(0, getBoard().length).filter(isEmpty.negate()::test).count();

    private Supplier<Character> nextPiece = () -> numOfPieces.get() % 2 == 0 ? Constants.GAME_PIECE_ONE : Constants.GAME_PIECE_TWO;

    private Function<Character, Supplier<Boolean>> setComputer = piece -> () -> nextPiece.get().equals(piece);

    private BiFunction<Integer, Integer, Character> get = (row, column) -> board[getIndex.apply(row, column)];

    private BiPredicate<Character, Function<Integer, Character>> isWinner = (piece, vector) ->
            IntStream.range(0, side).allMatch(index -> piece.equals(vector.apply(index)));

    private Function<Integer, Stream<Function<Integer, Character>>> getVectors = position -> {
        List<Integer> move = getRowColumn.apply(position);
        return Stream.of(index -> get.apply(move.get(0), index),
                index -> get.apply(index, move.get(1)),
                index -> get.apply(index, index),
                index -> get.apply(index, (side - 1) - index));
    };

    private BiConsumer<Integer, Character> checkForWin = (position, piece) ->
            getVectors.apply(position).parallel()
                    .filter(index -> isWinner.test(piece, index)).findFirst()
                    .ifPresent(move -> setWinner.accept(piece));
}