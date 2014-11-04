package tictactoe;

import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class ComputerPlayerImpl implements ComputerPlayer {
    private Character piece;
    private final Random random;

    public ComputerPlayerImpl() {
        random = new Random();
    }

    @Override
    public void setPiece(Character piece) {
        this.piece = piece;
    }

    @Override
    public Character getPiece() {
        return piece;
    }

    /**
     * When given a board, it will create a list based on the vacant spaces of the board.
     * Each space will be attributed a score. Then the moves will be grouped together based
     * on the score they are given. The group of moves with the highest score will be extracted
     * and a move from the list will be randomly chosen. The reason that a move can be randomly
     * chosen is because it has the same score as any of the other moves, signifying that any move
     * is just as good as another. Once the move is chosen, it is played on the board. Then the
     * board is returned.
     *
     * @param board that represents the current state of the game
     * @return board that has the move of the computerPlayer placed on it.
     */
    @Override
    public Board calculateBestMove(Board board) {
        Character piece = getPiece();
        List<List<Integer>> maxMoves = getMoves(piece, board).parallelStream().collect(
                groupingBy(move -> getScore(piece, playMove(piece, move, board)))).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        List<Integer> move = maxMoves.get(random.nextInt(maxMoves.size()));
        return playMove(piece, move, board);
    }

    /**
     * Upon entering the method, a check to see if the game is over. If it is a score is returned
     * based on the ending state of the game. If the game is not over, the available moves are gathered
     * to recursively build a game tree by playing each move and calling the containing method switching
     * pieces, to emulate game play, until the game is over creating the branches of the tree. Upon the
     * completion of a branch, the min or max value is calculated depending on the piece that
     * entered into the method last.
     *
     * @param piece of the last player
     * @param board that was just played on
     * @return score of the turn
     */
    private int getScore(Character piece, Board board) {
        if (board.gameOver()) return score(board);
        IntStream scores = getMoves(getOpponent(piece), board).stream()
                .map(move -> playMove(getOpponent(piece), move, board))
                .mapToInt(childBoard -> getScore(getOpponent(piece), childBoard));
        return getPiece().equals(piece) ? scores.min().getAsInt() : scores.max().getAsInt();
    }

    /**
     * To simulate the perfect player, the winning moves are first considered. If there are no moves
     * that can win a game, then search for a move that will make player lose. If there are no losing
     * moves, then retrieve all the possible moves for consideration.
     *
     * @param piece representing the player
     * @param board that was played on
     * @return the moves to be used
     */
    private Set<List<Integer>> getMoves(Character piece, Board board) {
        Set<List<Integer>> moves = findWinningMoves(piece, board);
        if (moves.isEmpty()) moves = findLosingMoves(piece, board);
        if (moves.isEmpty()) moves = board.getVacancies();
        return moves;
    }

    /**
     * If there is no winner then return the score for a draw. If there is a winner and it is the computer
     * player return a wining score, else it is not so return a losing score.
     *
     * @param board that was played on
     * @return the score
     */
    private int score(Board board) {
        Character winner = board.getWinner();
        if (winner == null) return constants.DRAW_SCORE;
        return (winner.equals(getPiece())) ? constants.WIN_SCORE : constants.LOSE_SCORE;
    }

    /**
     * Filter out all the winning moves on the current board.
     *
     * @param piece representing the player
     * @param board that was played on
     * @return the moves that will gain a win.
     */
    private Set<List<Integer>> findWinningMoves(Character piece, Board board) {
        return board.getVacancies().stream()
                .filter(move -> playMove(piece, move, board).getWinner() != null)
                .collect(toSet());
    }

    /**
     * Filter out all the losing moves on the current board.
     *
     * @param piece representing the player
     * @param board that was played on
     * @return the moves that will incur a loss.
     */
    private Set<List<Integer>> findLosingMoves(Character piece, Board board) {
        return board.getVacancies().stream()
                .map(move -> playMove(piece, move, board))
                .flatMap(childBoard -> findWinningMoves(getOpponent(piece), childBoard).stream())
                .collect(toSet());
    }

    /**
     * Creates a copy of the board with the move attributed to the piece that was passed
     *
     * @param piece representing the player
     * @param move for the board
     * @param board to play on
     * @return board copy with the move set upon it
     */
    private Board playMove(Character piece, List<Integer> move, Board board) {
        board = board.copy();
        try {
            board.set(move.get(0), move.get(1), piece);
        } catch (NotVacantException | OutOfBoundsException | OutOfTurnException e) {
            e.printStackTrace();
        }
        return board;
    }

    private Character getOpponent(Character piece) {
        return constants.GAME_PIECE_ONE.equals(piece) ?
                constants.GAME_PIECE_TWO : constants.GAME_PIECE_ONE;
    }
}
