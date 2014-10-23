package views;

import controllers.GameCtrl;
import factories.GameViewFactory;
import factories.PlayerFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lang.constants;
import models.Player;

import java.io.IOException;

public class MenuView extends Parent {

    private final GameCtrl gameCtrl;
    private PlayerFactory playerFactory;
    private final GameViewFactory gameViewFactory;
    private final Button buttonOne;
    private final Button buttonTwo;
    private final BorderPane menu;
    private final HBox header;

    public MenuView(GameCtrl gameCtrl, PlayerFactory playerFactory, GameViewFactory gameViewFactory) throws IOException {
        this.gameCtrl = gameCtrl;
        this.playerFactory = playerFactory;
        this.gameViewFactory = gameViewFactory;
        menu = FXMLLoader.load(getClass().getResource(constants.MENU_VIEW));
        header = (HBox) menu.getTop();
        buttonOne = (Button) menu.getLeft();
        buttonTwo = (Button) menu.getRight();
        buttonTwo.setOnMouseClicked(twoPlayer());
        buttonOne.setOnMouseClicked(onePlayer());
        this.getChildren().add(menu);
    }

    private EventHandler<MouseEvent> onePlayer() {
        return event -> {
            buttonOne.setText(constants.GAME_PIECE_ONE);
            buttonTwo.setText(constants.GAME_PIECE_TWO);

            buttonOne.setOnMouseClicked(setOnePlayers(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO));
            buttonTwo.setOnMouseClicked(setOnePlayers(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE));
        };
    }

    private EventHandler<MouseEvent> setOnePlayers(String gamePiece, String gamePiece1) {
        return event -> {
            Player player1 = playerFactory.createPlayer(gamePiece, constants.SIDE);
            Player player2 = playerFactory.createComputerPlayer(gamePiece1, constants.SIDE, player1);
            setupGame(player1, player2);
        };
    }

    private void setupGame(Player player1, Player player2) {
        GameView gameView = null;
        try {
            gameView = gameViewFactory.createGameView(gameCtrl, player1, player2, header);
        } catch (IOException e) {
            e.printStackTrace();
        }
        menu.getChildren().remove(buttonOne);
        menu.getChildren().remove(buttonTwo);
        Pane pane = (Pane) menu.getCenter();
        pane.getChildren().add(gameView);
    }

    private EventHandler<MouseEvent> twoPlayer() {
        return event -> {
            Player player1 = playerFactory.createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
            Player player2 = playerFactory.createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
            setupGame(player1, player2);
        };
    }
}
