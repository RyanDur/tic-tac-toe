package tictactoe;

import tictactoe.exceptions.InvalidMoveException;
import tictactoe.lang.Constants;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        List<List<Integer>> maxMoves = getCandidates(game).parallelStream().collect(
                groupingBy(move -> negaPruneDynamicDepth(Constants.ALPHA_DEPTH, Constants.BETA_DEPTH,
                        playMove(move, game), Constants.PLY, 0)[0])).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        System.out.println();
        return maxMoves.get(random.nextInt(maxMoves.size()));
    }

    private int[] negaPruneDynamicDepth(int[] alpha, int[] beta, Game game, int ply, int depth) {
        if (depth >= alpha[1] || depth >= beta[1] || game.isOver()) return product(score(game, depth), ply);
        LinkedList<Game> children = getCandidates(game).stream()
                .map(move -> playMove(move, game)).collect(toCollection(LinkedList::new));
        while (alpha[0] > beta[0] && !children.isEmpty()) {
            int[] temp = negate(negaPruneDynamicDepth(negate(beta), negate(alpha), children.pop(), -ply, depth + 1));
            alpha = alpha[0] < temp[0] ? alpha : temp;
        }
        return alpha;
    }

    private Set<List<Integer>> getCandidates(Game game) {
        Set<List<Integer>> candidates = findWinningMoves(game, Constants.ROOT_PLY);
        if (candidates.isEmpty()) candidates = findWinningMoves(game, Constants.ROOT_PLY + 1);
        if (candidates.isEmpty()) candidates = game.getVacancies();
        return candidates;
    }

    private int[] score(Game game, int depth) {
        Character winner = game.getWinner();
        if (winner == null) return new int[]{Constants.DRAW_SCORE, depth};
        int extraWins = findWinningMoves(game, Constants.ROOT_PLY + 1).size();
        return (winner.equals(getPiece())) ? new int[]{Constants.WIN_SCORE + extraWins, depth} :
                new int[]{Constants.LOSE_SCORE - extraWins, depth};
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

    private int[] product(int[] src, int ply) {
        return new int[]{ply * src[0], src[1]};
    }

    private int[] negate(int[] src) {
        return new int[]{-src[0], src[1]};
    }

    private Character getPiece() {
        return piece;
    }
}
