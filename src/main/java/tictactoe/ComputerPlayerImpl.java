package tictactoe;

import tictactoe.exceptions.InvalidMoveException;
import tictactoe.lang.Constants;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;

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
        List<List<Integer>> maxMoves = getCandidates(game).stream().collect(
                groupingBy(move -> negaPruneDepth(Constants.POS_INF, Constants.NEG_INF,
                        playMove(move, game), Constants.PLY, Constants.DEPTH))).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        return maxMoves.get(random.nextInt(maxMoves.size()));
    }

    private int negaPruneDepth(int alpha, int beta, Game game, int ply, int depth) {
        if (depth <= 0 || game.isOver()) return ply * score(game);
        LinkedList<Game> candidates = getCandidates(game).stream()
                .map(move -> playMove(move, game)).collect(toCollection(LinkedList::new));
        while (alpha > beta && !candidates.isEmpty())
            alpha = Math.min(alpha, -negaPruneDepth(-beta, -alpha, candidates.pop(), -ply, depth - 1));
        return alpha;
    }

    private Set<List<Integer>> getCandidates(Game game) {
        Set<List<Integer>> candidates = findWinningMoves(game, Constants.ROOT_PLY).collect(toSet());
        if (candidates.isEmpty()) candidates = findWinningMoves(game, Constants.ROOT_PLY + 1).collect(toSet());
        return candidates.isEmpty() ? game.getVacancies() : candidates;
    }

    private int score(Game game) {
        Character winner = game.getWinner();
        if (winner == null) return Constants.DRAW_SCORE;
        int extraWins = (int) findWinningMoves(game, Constants.ROOT_PLY + 1).distinct().count();
        return piece.equals(winner) ? Constants.WIN_SCORE + extraWins : Constants.LOSE_SCORE - extraWins;
    }

    private Stream<List<Integer>> findWinningMoves(Game game, int depth) {
        Stream<List<Integer>> candidates = game.getVacancies().stream();
        if (depth <= 0) return candidates.filter(move -> playMove(move, game).getWinner() != null);
        return candidates.map(move -> playMove(move, game)).flatMap(child -> findWinningMoves(child, depth - 1));
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
}