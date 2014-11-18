package tictactoe;

import tictactoe.exceptions.InvalidMoveException;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;
import static tictactoe.lang.Constants.*;

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
                groupingBy(move -> negaPruneDynamicDepth(ALPHA_DEPTH, BETA_DEPTH,
                        play(move, game), PLY, ROOT_PLY)[0])).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        return maxMoves.get(random.nextInt(maxMoves.size()));
    }

    private int[] negaPruneDynamicDepth(int[] alpha, int[] beta, Game game, int ply, int depth) {
        if (depth >= alpha[1] || depth >= beta[1] || game.isOver()) return product(score(game, depth), ply);
        LinkedList<Game> children = getCandidates(game).stream()
                .map(move -> play(move, game)).collect(toCollection(LinkedList::new));
        while (alpha[0] > beta[0] && !children.isEmpty()) {
            int[] temp = negate(negaPruneDynamicDepth(negate(beta), negate(alpha), children.pop(), -ply, depth + 1));
            alpha = alpha[0] < temp[0] ? alpha : temp;
        }
        return alpha;
    }

    private Set<List<Integer>> getCandidates(Game game) {
        Set<List<Integer>> candidates = findWinMoves(game, ROOT_PLY).collect(toSet());
        if (candidates.isEmpty()) candidates = findWinMoves(game, ROOT_PLY + 1).collect(toSet());
        return candidates.isEmpty() ? game.getVacancies() : candidates;
    }

    private int[] score(Game game, int depth) {
        Character winner = game.getWinner();
        if (winner == null) return new int[]{DRAW_SCORE, depth};
        int extraWins = (int) findWinMoves(game, ROOT_PLY + 1).count();
        return piece.equals(winner) ? new int[]{WIN_SCORE + extraWins, depth} :
                new int[]{LOSE_SCORE - extraWins, depth};
    }

    private Stream<List<Integer>> findWinMoves(Game game, int depth) {
        Stream<List<Integer>> candidates = game.getVacancies().stream();
        if (depth <= 0) return candidates.filter(move -> play(move, game).getWinner() != null);
        return candidates.map(move -> play(move, game)).flatMap(child -> findWinMoves(child, depth - 1)).distinct();
    }

    private Game play(List<Integer> move, Game game) {
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
}