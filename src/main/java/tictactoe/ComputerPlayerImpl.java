package tictactoe;

import tictactoe.exceptions.InvalidMoveException;
import tictactoe.lang.Constants;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import static java.util.stream.Collectors.*;

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
    public List<Integer> getMove(Game game) {
        List<List<Integer>> maxMoves = game.getVacancies().stream().collect(
                groupingBy(getAlgo(game))).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        System.out.println();
        return maxMoves.get(random.nextInt(maxMoves.size()));
    }

    private Function<List<Integer>, Integer> getAlgo(Game game) {
        return move -> miniMaxPruneDynamicDepth(Constants.ALPHA_DEPTH, Constants.BETA_DEPTH, true, playMove(move, game), 0)[0];
    }

    private int[] miniMaxPruneDynamicDepth(int[] alpha, int beta[], boolean isComputer, Game game, int depth) {
        if (depth >= alpha[1] || depth >= beta[1] || game.isOver()) return score(game, depth);
        LinkedList<Game> children = getCandidates(game).stream()
                .map(move -> playMove(move, game)).collect(toCollection(LinkedList::new));
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

    private Set<List<Integer>> getCandidates(Game game) {
        Set<List<Integer>> candidates = findWinningMoves(game, 0);
        if (candidates.isEmpty()) candidates = findWinningMoves(game, 1);
        if (candidates.isEmpty()) candidates = game.getVacancies();
        return candidates;
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