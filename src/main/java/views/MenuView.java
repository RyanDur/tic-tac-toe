package views;

import factories.PlayerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import lang.constants;
import models.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuView extends Parent {

    private PlayerFactory playerFactory;

    public MenuView(PlayerFactory playerFactory) throws IOException {
        this.playerFactory = playerFactory;
        List<Player> players = new ArrayList<>();
        BorderPane menu = FXMLLoader.load(getClass().getResource(constants.MENU_VIEW));
        Button twoPlayer = (Button) menu.getRight();
        twoPlayer.setOnMouseClicked(event -> {
            players.add(playerFactory.createPlayer(constants.GAME_PIECE_ONE, constants.SIDE));
            players.add(playerFactory.createPlayer(constants.GAME_PIECE_TWO, constants.SIDE));
        });
        this.getChildren().add(menu);
    }
}
