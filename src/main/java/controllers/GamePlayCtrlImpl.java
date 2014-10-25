package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import lang.constants;
import models.ComputerPlayer;
import models.Player;

import java.util.Arrays;

@Singleton
public class GamePlayCtrlImpl implements GamePlayCtrl {
    private final GameCtrl gameCtrl;
    private final PlayerCtrl playerCtrl;
    private Player player1;
    private Player player2;

    @Inject
    public GamePlayCtrlImpl(GameCtrl gameCtrl, PlayerCtrl playerCtrl) {
        this.gameCtrl = gameCtrl;
        this.playerCtrl = playerCtrl;
    }

    @Override
    public void twoPlayer() {
        set(playerCtrl.setupTwoPlayer());
        gameCtrl.setup();
    }

    @Override
    public void onePlayer(String player1, String player2) {
        set(playerCtrl.setupOnePlayer(player1, player2));
        gameCtrl.setup();
        checkComputerPlayer();
    }

    @Override
    public boolean over() {
        return gameCtrl.gameOver();
    }

    @Override
    public void set(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        if (player2 instanceof ComputerPlayer) onePlayerMove(row, column);
        else twoPlayerMove(row, column);
    }

    @Override
    public Player getWinner() {
        return gameCtrl.getWinner();
    }

    @Override
    public Player[] getBoard() {
        return gameCtrl.getBoard();
    }

    @Override
    public void setup() {
        gameCtrl.setup();
    }

    @Override
    public void reset() {
        gameCtrl.setup();
        checkComputerPlayer();
    }

    private void onePlayerMove(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        player1.setCoordinates(row, column);
        gameCtrl.setPiece(player1);
        if (!over()) computerMove(player2);
    }

    private void twoPlayerMove(int row, int column) throws OutOfBoundsException, OutOfTurnException, NotVacantException {
        if (numberOfPieces() % 2 == 0) gameCtrl.setPiece(setPlayer1(row, column));
        else gameCtrl.setPiece(setPlayer2(row, column));
    }

    private void checkComputerPlayer() {
        if (player2.getPiece().equals(constants.GAME_PIECE_ONE)) computerMove(player2);
    }

    private void computerMove(Player player) {
        try {
            ((ComputerPlayer) player).calculateBestMove(getBoard());
            gameCtrl.setPiece(player);
        } catch (OutOfBoundsException | NotVacantException | OutOfTurnException e) {
            e.printStackTrace();
        }
    }

    private Player setPlayer2(int row, int column) throws OutOfBoundsException {
        player2.setCoordinates(row, column);
        return player2;
    }

    private Player setPlayer1(int row, int column) throws OutOfBoundsException {
        player1.setCoordinates(row, column);
        return player1;
    }

    private int numberOfPieces() {
        return (int) Arrays.stream(getBoard()).filter(player -> player != null).count();
    }

    private void set(Player[] players) {
        player1 = players[0];
        player2 = players[1];
    }
}
