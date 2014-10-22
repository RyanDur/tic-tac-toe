package views;

import factories.PlayerFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import lang.constants;
import models.Player;

import java.io.IOException;

public class MenuView extends Parent {

    private PlayerFactory playerFactory;
    private final Button buttonOne;
    private final Button buttonTwo;

    public MenuView(PlayerFactory playerFactory) throws IOException {
        this.playerFactory = playerFactory;
        BorderPane menu = FXMLLoader.load(getClass().getResource(constants.MENU_VIEW));
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
            Player player = playerFactory.createPlayer(gamePiece, constants.SIDE);
            playerFactory.createComputerPlayer(gamePiece1, constants.SIDE, player);
        };
    }

    private EventHandler<MouseEvent> twoPlayer() {
        return event -> {
            playerFactory.createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
            playerFactory.createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
        };
    }
}
