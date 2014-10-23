package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import lang.constants;
import models.ComputerPlayer;
import models.Player;

import java.io.IOException;

public class GameView extends Parent {
    private GameCtrl gameCtrl;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private GridPane grid;
    private Label messages;
    private Button replay;
    private Button reset;

    public GameView(GameCtrl gameCtrl, Player player1, Player player2, Label messages, Button reset, Button replay) throws IOException {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource(constants.GAME_VIEW));
        this.reset = reset;
        this.replay = replay;
        this.messages = messages;
        this.gameCtrl = gameCtrl;
        this.player1 = player1;
        this.player2 = player2;
        grid = (GridPane) borderPane.getCenter();
        this.getChildren().add(borderPane);
        setPlay();
        checkForComputer();
    }

    private void checkForComputer() {
        if(player2 instanceof ComputerPlayer && player2.getPiece().equals(constants.GAME_PIECE_ONE)) {
            computerPlay();
        }
    }

    private void computerPlay() {
        try {
            ((ComputerPlayer) player2).calculateBestMove(gameCtrl.getBoard());
            gameCtrl.setPiece(player2);
            fillBoard(gameCtrl.getBoard());
            currentPlayer = player1;
        } catch (OutOfBoundsException | NotVacantException | OutOfTurnException e) {
            e.printStackTrace();
        }
    }

    private void setPlay() {
        setupHeaderButtons(false);
        clearBoard();
        messages.setText(constants.EMPTY);
        gameCtrl.setup();
        fillBoard(gameCtrl.getBoard());
    }

    private void fillBoard(Player[] board) {
        if (gameCtrl.gameOver()) {
            setGameOver();
        }
        grid.getChildren().stream().filter(space -> space instanceof Label)
                .forEach(label -> setSpace(board, (Label) label));
    }

    private void setSpace(Player[] board, Label label) {
        int position = calc(getRow(label), getColumn(label));
        Player player = board[position];
        if (player == null) {
            label.setOnMouseClicked(setPiece());
        } else {
            label.setText(player.getPiece());
        }
    }

    private EventHandler<MouseEvent> setPiece() {
        return mouseEvent -> {
            try {
                messages.setText(constants.EMPTY);
                if (!gameCtrl.gameOver()) {
                    Label space = (Label) mouseEvent.getSource();
                    Player player = getCurrentPlayer();
                    player.setCoordinates(getRow(space), getColumn(space));
                    gameCtrl.setPiece(player);
                    fillBoard(gameCtrl.getBoard());
                    if(player2 instanceof ComputerPlayer) {
                        computerPlay();
                    }
                }
            } catch (OutOfTurnException | NotVacantException | OutOfBoundsException e) {
                currentPlayer = getLastPlayer();
                messages.setText(e.getMessage());
            }
        };
    }

    private void clearBoard() {
        grid.getChildren().stream().
                filter(space -> space instanceof Label).
                forEach(space -> {
                    Label label = (Label) space;
                    label.setText(constants.EMPTY);
                });
    }

    private void setGameOver() {
        setupHeaderButtons(true);
        Player winner = gameCtrl.getWinner();
        if (winner == null) {
            messages.setText(constants.DRAW_MESSAGE);
        } else {
            messages.setText(winner.getPiece() + constants.HAS_WON_MESSAGE);
        }
    }

    private void setupHeaderButtons(boolean visible) {
        replay.setVisible(visible);
        reset.setVisible(visible);
        replay.setOnMouseClicked(event -> setPlay());
    }

    private Player getCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        return currentPlayer;
    }

    private Player getLastPlayer() {
        return getCurrentPlayer();
    }

    private int calc(int row, int column) {
        return (row * constants.SIDE) + column;
    }

    private int getColumn(Node node) {
        Integer column = GridPane.getColumnIndex(node);
        return column == null ? 0 : column;
    }

    private int getRow(Node node) {
        Integer row = GridPane.getRowIndex(node);
        return row == null ? 0 : row;
    }
}