package tictactoe.game;

import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;
import tictactoe.lang.Constants;

import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

public class ComputerPlayerImpl implements ComputerPlayer {
    private Character piece;
    private final Random random;
    private Character opponent;

    public ComputerPlayerImpl() {
        random = new Random();
    }

    @Override
    public void setPiece(Character piece) {
        this.piece = piece;
        setOpponent(piece);
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
        List<List<Integer>> maxMoves = getMoves(true, board).parallelStream().collect(
                groupingBy(move -> getScore(true, playMove(true, move, board)))).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        List<Integer> move = maxMoves.get(random.nextInt(maxMoves.size()));
        return playMove(true, move, board);
    }

    /**
     * Upon entering the method, a check to see if the game is over. If it is a score is returned
     * based on the ending state of the game. If the game is not over, the available moves are gathered
     * to recursively build a game tree by playing each move and calling the containing method switching
     * pieces, to emulate game play, until the game is over creating the branches of the tree. Upon the
     * completion of a branch, the min or max value is calculated depending on the piece that entered
     * into the method last.
     *
     * @param isComputer true if the player is computer
     * @param board that was just played on
     * @return score of the turn
     */
    private int getScore(boolean isComputer, Board board) {
        if (board.gameOver()) return score(board);
        IntStream scores = getMoves(!isComputer, board).stream()
                .map(move -> playMove(!isComputer, move, board))
                .mapToInt(childBoard -> getScore(!isComputer, childBoard));
        return isComputer ? scores.min().getAsInt() : scores.max().getAsInt();
    }

    /**
     * To simulate the perfect player, the winning moves are first considered. If there are no moves
     * that can win a game, then search for a move that will make player lose. If there are no losing
     * moves, then retrieve all the possible moves for consideration.
     *
     * @param piece true if the player is computer
     * @param board that was played on
     * @return the moves to be used
     */
    private Set<List<Integer>> getMoves(boolean piece, Board board) {
        Set<List<Integer>> moves = findWinningMoves(piece, board);
        if (moves.isEmpty()) moves = findLosingMoves(piece, board);
        if (moves.isEmpty()) moves = board.getVacancies();
        return moves;
    }

    private int score(Board board) {
        Character winner = board.getWinner();
        if (winner == null) return Constants.DRAW_SCORE;
        return (winner.equals(getPiece())) ? Constants.WIN_SCORE : Constants.LOSE_SCORE;
    }

    private Set<List<Integer>> findWinningMoves(boolean computer, Board board) {
        return board.getVacancies().stream()
                .filter(move -> playMove(computer, move, board).getWinner() != null)
                .collect(toSet());
    }

    private Set<List<Integer>> findLosingMoves(boolean computer, Board board) {
        return board.getVacancies().stream()
                .map(move -> playMove(computer, move, board))
                .flatMap(childBoard -> findWinningMoves(!computer, childBoard).stream())
                .collect(toSet());
    }

    private Board playMove(boolean isComputer, List<Integer> move, Board board) {
        board = board.copy();
        try {
            board.set(move.get(0), move.get(1), isComputer ? getPiece() : getOpponent());
        } catch (NotVacantException | OutOfBoundsException | OutOfTurnException e) {
            e.printStackTrace();
        }
        return board;
    }

    public Character getOpponent() {
        return opponent;
    }

    private void setOpponent(Character piece) {
        opponent = Constants.GAME_PIECE_ONE.equals(piece) ?
                Constants.GAME_PIECE_TWO : Constants.GAME_PIECE_ONE;
    }
}
