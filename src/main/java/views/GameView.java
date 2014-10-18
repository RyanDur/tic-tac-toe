package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.PlayerFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import lang.constants;
import models.Player;

import java.io.IOException;
import java.util.stream.Collectors;

public class GameView extends Parent {
    private final GameCtrl gameCtrl;
    private final PlayerFactory playerFactory;
    private final Label messages;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private final GridPane grid;
    private Button play;

    public GameView(GameCtrl gameCtrl, PlayerFactory playerFactory) throws IOException {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource(constants.GAME_VIEW));
        grid = (GridPane) borderPane.getCenter();
        grid.setVisible(false);
        this.gameCtrl = gameCtrl;
        this.playerFactory = playerFactory;
        this.getChildren().add(borderPane);
        play = (Button) getNode((Pane) borderPane.getTop(), constants.PLAY_ID);
        messages = (Label) getNode((Pane) borderPane.getTop(), constants.MESSAGES_ID);
        setPlay();
    }

    private void setPlay() {
        play.setOnMouseClicked(event -> {
            messages.setText("");
            play.setVisible(false);
            gameCtrl.setup();
            grid.setVisible(true);
            fillBoard(gameCtrl.getBoard());
            setPlayers();
        });
    }

    private void setPlayers() {
        player1 = playerFactory.createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        player2 = playerFactory.createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
    }

    private void fillBoard(Player[] board) {
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
                System.out.println();
                if (!gameCtrl.gameOver()) {
                    Label space = (Label) mouseEvent.getSource();
                    Player player = getCurrentPlayer();
                    player.setCoordinates(getRow(space), getColumn(space));
                    gameCtrl.setPiece(player);
                    fillBoard(gameCtrl.getBoard());
                } else {
                    setGameOverMessage();
                    play.setVisible(true);
                }
            } catch (OutOfTurnException | NotVacantException | OutOfBoundsException e) {
                e.printStackTrace();
            }
        };
    }

    private void setGameOverMessage() {
        Player winner = gameCtrl.getWinner();
        if(winner == null) {
            messages.setText(constants.DRAW_MESSAGE);
        } else {
            messages.setText(winner.getPiece() + constants.HAS_WON_MESSAGE);
        }
    }

    private Player getCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        return currentPlayer;
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

    private Node getNode(Pane pane, String playId) {
        return pane.getChildren().stream()
                .filter(node -> node.getId().equals(playId)).collect(Collectors.toList()).get(0);
    }
}