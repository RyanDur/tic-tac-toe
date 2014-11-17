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
        LinkedList<Game> children = getCandidates(game).stream()
                .map(move -> playMove(move, game)).collect(toCollection(LinkedList::new));
        while (alpha > beta && !children.isEmpty())
            alpha = Math.min(alpha, -negaPruneDepth(-beta, -alpha, children.pop(), -ply, depth - 1));
        return alpha;
    }

    private Set<List<Integer>> getCandidates(Game game) {
        Set<List<Integer>> candidates = findWinningMoves(game, Constants.ROOT_PLY);
        if (candidates.isEmpty()) candidates = findWinningMoves(game, Constants.ROOT_PLY + 1);
        return candidates.isEmpty() ? game.getVacancies() : candidates;
    }

    private int score(Game game) {
        Character winner = game.getWinner();
        if (winner == null) return Constants.DRAW_SCORE;
        int extraWins = findWinningMoves(game, Constants.ROOT_PLY + 1).size();
        return (winner.equals(getPiece())) ? Constants.WIN_SCORE + extraWins : Constants.LOSE_SCORE - extraWins;
    }

    private Set<List<Integer>> findWinningMoves(Game game, int depth) {
        Stream<List<Integer>> candidates = game.getVacancies().stream();
        if (depth <= 0) return candidates.filter(move -> playMove(move, game).getWinner() != null).collect(toSet());
        return candidates.map(move -> playMove(move, game))
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