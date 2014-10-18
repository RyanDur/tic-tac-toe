package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.PlayerFactory;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import lang.constants;
import models.Player;

import java.io.IOException;

public class GameView extends Parent {
    private final GameCtrl gameCtrl;
    private final PlayerFactory playerFactory;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private final GridPane grid;

    public GameView(GameCtrl gameCtrl, PlayerFactory playerFactory) throws IOException {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource(constants.GAME_VIEW));
        grid = (GridPane) borderPane.getCenter();
        this.gameCtrl = gameCtrl;
        this.playerFactory = playerFactory;
        this.getChildren().add(borderPane);
        fillBoard(gameCtrl.getBoard());
        setPlayers();
    }

    private void setPlayers() {
        player1 = playerFactory.createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        player2 = playerFactory.createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
    }

    private void fillBoard(Player[] board) {
        ObservableList<Node> spaces = grid.getChildren();
        for(int space = 0; space < spaces.size()-1; space++) {
            setSpace(board[space], (Label) spaces.get(space), space);
        }
    }

    private void setSpace(Player player, Label label, int space) {
        label.setId("cell" + space);
        Integer row = GridPane.getRowIndex(label);
        Integer column = GridPane.getColumnIndex(label);
        if (row == null) row = 0;
        if (column == null) column = 0;
        if (player == null) {
            label.setOnMouseClicked(setPiece(row, column));
        } else {
            label.setText(player.getPiece());
        }
    }

    private EventHandler<MouseEvent> setPiece(int x, int y) {
        return mouseEvent -> {
            try {
                if (!gameCtrl.gameOver()) {
                    Player player = getCurrentPlayer();
                    player.setCoordinates(x, y);
                    gameCtrl.setPiece(player);
                    fillBoard(gameCtrl.getBoard());
                }
            } catch (OutOfTurnException | NotVacantException | OutOfBoundsException e) {
                e.printStackTrace();
            }
        };
    }

    private Player getCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        return currentPlayer;
    }
}