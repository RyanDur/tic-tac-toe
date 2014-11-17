package tictactoe;

import tictactoe.exceptions.InvalidMoveException;
import tictactoe.lang.Constants;

import java.util.*;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

public class ComputerPlayerImpl implements ComputerPlayer {
    private Character piece;
    private final Random random;
    private static int count;

    private static void nodeCount() {
        count++;
    }

    public ComputerPlayerImpl() {
        random = new Random();
    }

    @Override
    public void setPiece(Character piece) {
        this.piece = piece;
    }

    /*
     * When given a board, it will create a list based on the vacant spaces of the board.
     * Each space will be attributed a score. Then the moves will be grouped together based
     * on the score they are given. The group of moves with the highest score will be extracted
     * and a move from the list will be randomly chosen. The reason that a move can be randomly
     * chosen is because it has the same score as any of the other moves, signifying that any move
     * is just as good as another. Once the move is chosen, it is returned.
     */
    @Override
    public List<Integer> getMove(Game game) {
        List<List<Integer>> maxMoves = game.getVacancies().stream().collect(
                groupingBy(getAlgo(game))).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        System.out.println();
        return maxMoves.get(0);
    }

    private Function<List<Integer>, Integer> getAlgo(Game game) {
        return move -> print(move, miniMaxPruneDynamicDepth(
                new int[]{Constants.POS_INF, Constants.POS_INF},
                new int[]{Constants.NEG_INF, Constants.POS_INF},
                true, playMove(move, game), 0)[0]);
//        return move -> print(move, negaPruneDepth(Constants.POS_INF, Constants.NEG_INF, playMove(move, game), Constants.PLY, Constants.DEPTH));
//        return move -> print(move, miniMaxPruneDepth(Constants.POS_INF, Constants.NEG_INF, true, playMove(move, game), Constants.DEPTH));
//        return move -> print(move, negaPrune(Constants.POS_INF, Constants.NEG_INF, playMove(move, game), Constants.PLY));
//        return move -> print(move, miniMaxPrune(Constants.POS_INF, Constants.NEG_INF, true, playMove(move, game)));
//        return move -> print(move, negaMax(playMove(move, game), 1));
//        return move -> print(move, miniMax(true, playMove(move, game)));
    }

    private int print(List<Integer> move, int score) {
        System.out.println(move + ", " + score + ", " + ComputerPlayerImpl.count);
        ComputerPlayerImpl.count = 0;
        return score;
    }

    /*
     * Upon entering the method, a check to see if the game is isOver. If it is a score is returned
     * based on the ending state of the game. If the game is not isOver, the available moves are gathered
     * to recursively build a game tree by playing each move and calling the containing method switching
     * pieces, to emulate game play, until the game is isOver creating the branches of the tree. Upon the
     * completion of a branch, the min or max value is calculated depending on the piece that entered
     * into the method last.
     */

    private int[] miniMaxPruneDynamicDepth(int[] alpha, int beta[], boolean isComputer, Game game, int depth) {
        ComputerPlayerImpl.nodeCount();
        if (depth >= alpha[1] || depth >= beta[1] || game.isOver()) return score(game, depth);
        LinkedList<Game> children = collectChildren(game);
        while (alpha[0] > beta[0] && !children.isEmpty()) {
            int[] temp = miniMaxPruneDynamicDepth(alpha, beta, !isComputer, children.pop(), depth + 1);
            if (isComputer) alpha = alpha[0] < temp[0] ? alpha : temp;
            else beta = beta[0] > temp[0] ? beta : temp;
        }
        return isComputer ? alpha : beta;
    }

    private int[] score(Game game, int depth) {
        Character winner = game.getWinner();
        if (winner == null) return new int[]{Constants.DRAW_SCORE, depth};
        int extraWins = findWinningMoves(game, 1).size();
        if (winner.equals(getPiece())) return new int[]{Constants.WIN_SCORE + extraWins, depth};
        else return new int[]{Constants.LOSE_SCORE - extraWins, depth};
    }

    private int negaPruneDepth(int alpha, int beta, Game game, int ply, int depth) {
        if (depth <= 0 || game.isOver()) return ply * score(game);
        LinkedList<Game> children = collectChildren(game);
        while (alpha > beta && !children.isEmpty())
            alpha = Math.min(alpha, -negaPruneDepth(-beta, -alpha, children.pop(), -ply, depth - 1));
        return alpha;
    }

    private int miniMaxPruneDepth(int alpha, int beta, boolean isComputer, Game game, int depth) {
        if (depth <= 0 || game.isOver()) return score(game);
        LinkedList<Game> children = collectChildren(game);
        while (alpha > beta && !children.isEmpty()) {
            if (isComputer) alpha = Math.min(alpha, miniMaxPruneDepth(alpha, beta, false, children.pop(), depth - 1));
            else beta = Math.max(beta, miniMaxPruneDepth(alpha, beta, true, children.pop(), depth - 1));
        }
        return isComputer ? alpha : beta;
    }

    private int negaPrune(int alpha, int beta, Game game, int ply) {
        ComputerPlayerImpl.nodeCount();
        if (game.isOver()) return ply * score(game);
        LinkedList<Game> children = collectChildren(game);
        while (alpha > beta && !children.isEmpty())
            alpha = Math.min(alpha, -negaPrune(-beta, -alpha, children.pop(), -ply));
        return alpha;
    }

    private int miniMaxPrune(int alpha, int beta, boolean isComputer, Game game) {
        ComputerPlayerImpl.nodeCount();
        if (game.isOver()) return score(game);
        LinkedList<Game> children = collectChildren(game);
        while (alpha > beta && !children.isEmpty()) {
            if (isComputer) alpha = Math.min(alpha, miniMaxPrune(alpha, beta, false, children.pop()));
            else beta = Math.max(beta, miniMaxPrune(alpha, beta, true, children.pop()));
        }
        return isComputer ? alpha : beta;
    }

    private int negaMax(Game game, int ply) {
        ComputerPlayerImpl.nodeCount();
        return game.isOver() ? ply * score(game) :
                -getScores(game, child -> negaMax(child, -ply)).max().getAsInt();
    }

    private int miniMax(boolean isComputer, Game game) {
        ComputerPlayerImpl.nodeCount();
        if (game.isOver()) return score(game);
        IntStream scores = getScores(game, child -> miniMax(!isComputer, child));
        return isComputer ? scores.min().getAsInt() : scores.max().getAsInt();
    }

    private LinkedList<Game> collectChildren(Game game) {
        return getGames(game).collect(toCollection(LinkedList::new));
    }

    private Stream<Game> getGames(Game game) {
        return getCandidates(game).stream()
                .map(move -> playMove(move, game));
    }

    private IntStream getScores(Game game, ToIntFunction<Game> children) {
        return getGames(game)
                .mapToInt(children);
    }

    /*
     * To simulate the perfect player, the winning moves are first considered. If there are no moves
     * that can win a game, then search for a move that will make player lose. If there are no losing
     * moves, then retrieve all the possible moves for consideration.
     */
    private Set<List<Integer>> getCandidates(Game game) {
        Set<List<Integer>> candidates = findWinningMoves(game, 0);
        if (candidates.isEmpty()) candidates = findWinningMoves(game, 1);
        if (candidates.isEmpty()) candidates = game.getVacancies();
        return candidates;
    }

    private int score(Game game) {
        Character winner = game.getWinner();
        if (winner == null) return Constants.DRAW_SCORE;
        int extraWins = findWinningMoves(game, 1).size();
        return (winner.equals(getPiece())) ? Constants.WIN_SCORE + extraWins : Constants.LOSE_SCORE - extraWins;
    }

    private Set<List<Integer>> findWinningMoves(Game game, int depth) {
        if (depth <= 0) return game.getVacancies().stream()
                .filter(move -> playMove(move, game).getWinner() != null).collect(toSet());
        return game.getVacancies().stream().map(move -> playMove(move, game))
                .flatMap(child -> findWinningMoves(child, depth - 1).stream()).collect(toSet());
    }

    private Game playMove(List<Integer> move, Game game) {
        game = game.copy();
        try {
            game.set(move);
        } catch (InvalidMoveException e) {
            e.printStackTrace();
        }
        return game;
    }

    private Character getPiece() {
        return piece;
    }
}
