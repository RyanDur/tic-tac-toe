package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComputerPlayerImpl implements ComputerPlayer {
    private String piece;

    @Override
    public void setPiece(String piece) {
        this.piece = piece;
    }

    @Override
    public String getPiece() {
        return piece;
    }

    @Override
    public Board calculateBestMove(Board board) {
        String piece = getPiece();
        int depth = constants.DEPTH;
        return getMoves(piece, board).stream().max((move1, move2) ->
                getWeight(piece, playMove(piece, move1, board), depth) -
                        getWeight(piece, playMove(piece, move2, board), depth))
                .map(move -> playMove(piece, move, board)).get();
    }

    private int getWeight(String piece, Board board, int depth) {
        if (depth == 0 || board.gameOver()) return score(board, piece);
        IntStream weights = getMoves(getOpponent(piece), board).stream()
                .map(move -> playMove(getOpponent(piece), move, board))
                .mapToInt(childBoard -> getWeight(getOpponent(piece), childBoard, depth - 1));
        return getPiece().equals(piece) ? weights.min().getAsInt() : weights.max().getAsInt();
    }

    private Set<List<Integer>> getMoves(String piece, Board board) {
        Set<List<Integer>> moves = findWinningMoves(piece, board);
        if (moves.isEmpty()) moves = findLosingMoves(piece, board);
        if (moves.isEmpty()) moves = board.getVacancies();
        return moves;
    }

    private int score(Board board, String piece) {
        if (board.getWinner() == null) return constants.DRAW_WEIGHT;
        return (getPiece().equals(piece)) ? constants.WIN_WEIGHT : constants.LOSE_WEIGHT;
    }

    private Set<List<Integer>> findWinningMoves(String piece, Board board) {
        return board.getVacancies().stream()
                .filter(move -> playMove(piece, move, board).getWinner() != null)
                .collect(Collectors.toSet());
    }

    private Set<List<Integer>> findLosingMoves(String piece, Board board) {
        return board.getVacancies().stream()
                .map(move -> playMove(piece, move, board))
                .flatMap(childBoard -> findWinningMoves(getOpponent(piece), childBoard).stream())
                .collect(Collectors.toSet());
    }

    private Board playMove(String piece, List<Integer> move, Board board) {
        Board copy = board.copy();
        try {
            copy.set(move.get(0), move.get(1), piece);
        } catch (NotVacantException | OutOfBoundsException | OutOfTurnException e) {
            e.printStackTrace();
        }
        return copy;
    }

    private String getOpponent(String piece) {
        return constants.GAME_PIECE_ONE.equals(piece) ?
                constants.GAME_PIECE_TWO : constants.GAME_PIECE_ONE;
    }
}
