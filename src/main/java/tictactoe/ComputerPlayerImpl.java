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
                groupingBy(move -> negaPruneDepth(POS_INF, NEG_INF, play(move, game), PLY, DEPTH))).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        return maxMoves.get(random.nextInt(maxMoves.size()));
    }

    private int negaPruneDepth(int alpha, int beta, Game game, int ply, int depth) {
        if (depth <= 0 || game.isOver()) return ply * score(game);
        LinkedList<Game> candidates = getCandidates(game).stream()
                .map(move -> play(move, game)).collect(toCollection(LinkedList::new));
        while (alpha > beta && !candidates.isEmpty())
            alpha = Math.min(alpha, -negaPruneDepth(-beta, -alpha, candidates.pop(), -ply, depth - 1));
        return alpha;
    }

    private Set<List<Integer>> getCandidates(Game game) {
        Set<List<Integer>> candidates = findWinMoves(game, ROOT_PLY).collect(toSet());
        if (candidates.isEmpty()) candidates = findWinMoves(game, ROOT_PLY + 1).collect(toSet());
        return candidates.isEmpty() ? game.getVacancies() : candidates;
    }

    private int score(Game game) {
        Character winner = game.getWinner();
        if (winner == null) return DRAW_SCORE;
        int extraWins = (int) findWinMoves(game, ROOT_PLY + 1).distinct().count();
        return piece.equals(winner) ? WIN_SCORE + extraWins : LOSE_SCORE - extraWins;
    }

    private Stream<List<Integer>> findWinMoves(Game game, int depth) {
        Stream<List<Integer>> candidates = game.getVacancies().stream();
        if (depth <= 0) return candidates.filter(move -> play(move, game).getWinner() != null);
        return candidates.map(move -> play(move, game)).flatMap(child -> findWinMoves(child, depth - 1));
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
}