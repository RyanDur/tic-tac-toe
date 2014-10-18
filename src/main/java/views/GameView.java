package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.PlayerFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
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
        for (int y = 0; y < constants.SIDE; y++) {
            for (int x = 0; x < constants.SIDE; x++) {
                Labeled space = (Label) grid.getChildren().get(calc(x, y));
                String id = x + "," + y;
                space.setId(id);
                Player player = board[(x * constants.SIDE) + y];
                if (player == null) {
                    space.setOnMouseClicked(setPiece(x, y));
                } else {
                    space.setText(player.getPiece());
                }
            }
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

    private int calc(int x, int y) {
        return (x * constants.SIDE) + y;
    }
}
