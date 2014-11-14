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
        List<List<Integer>> maxMoves = getMoves(game).parallelStream().collect(
                groupingBy(move -> negaPruneDepth(Constants.POS_INF, Constants.NEG_INF,
                        playMove(move, game), 1, Constants.DEPTH))).entrySet().stream()
                .max((score1, score2) -> score1.getKey() - score2.getKey()).get().getValue();
        return maxMoves.get(random.nextInt(maxMoves.size()));
    }

    private int negaPruneDepth(int alpha, int beta, Game game, int ply, int depth) {
        if (depth <= 0 || game.isOver()) return ply * score(game);
        LinkedList<Game> children = getMoves(game).stream()
                .map(move -> playMove(move, game)).collect(toCollection(LinkedList::new));
        while (alpha > beta && !children.isEmpty())
            alpha = Math.min(alpha, -negaPruneDepth(-beta, -alpha, children.pop(), -ply, depth - 1));
        return alpha;
    }

    private Set<List<Integer>> getMoves(Game game) {
        Set<List<Integer>> candidates = game.getVacancies();
        Set<List<Integer>> moves = findWinningMoves(candidates, game);
        if (moves.isEmpty()) moves = findLosingMoves(candidates, game);
        if (moves.isEmpty()) moves = candidates;
        return moves;
    }

    private int score(Game game) {
        Character winner = game.getWinner();
        if (winner == null) return Constants.DRAW_SCORE;
        int extraWins = findLosingMoves(game.getVacancies(), game).size();
        return (winner.equals(getPiece())) ? Constants.WIN_SCORE + extraWins : Constants.LOSE_SCORE - extraWins;
    }

    private Set<List<Integer>> findWinningMoves(Set<List<Integer>> candidates, Game game) {
        return candidates.stream()
                .filter(move -> playMove(move, game).getWinner() != null)
                .collect(toSet());
    }

    private Set<List<Integer>> findLosingMoves(Set<List<Integer>> candidates, Game game) {
        return candidates.stream()
                .map(move -> playMove(move, game))
                .flatMap(child -> findWinningMoves(child.getVacancies(), child).stream())
                .collect(toSet());
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