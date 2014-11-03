package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ComputerPlayerImpl implements ComputerPlayer {
    private String piece;
    private final Random random;

    public ComputerPlayerImpl() {
        random = new Random();
    }

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
        List<List<Integer>> maxMoves = getMoves(piece, board).parallelStream().collect(Collectors
                .groupingBy(move -> getScore(piece, playMove(piece, move, board)))).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        List<Integer> move = maxMoves.get(random.nextInt(maxMoves.size()));
        return playMove(piece, move, board);
    }

    private int getScore(String piece, Board board) {
        if (board.gameOver()) return score(board, piece);
        IntStream scores = getMoves(getOpponent(piece), board).stream()
                .map(move -> playMove(getOpponent(piece), move, board))
                .mapToInt(childBoard -> getScore(getOpponent(piece), childBoard));
        return getPiece().equals(piece) ? scores.min().getAsInt() : scores.max().getAsInt();
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
        board = board.copy();
        try {
            board.set(move.get(0), move.get(1), piece);
        } catch (NotVacantException | OutOfBoundsException | OutOfTurnException e) {
            e.printStackTrace();
        }
        return board;
    }

    private String getOpponent(String piece) {
        return constants.GAME_PIECE_ONE.equals(piece) ?
                constants.GAME_PIECE_TWO : constants.GAME_PIECE_ONE;
    }
}
