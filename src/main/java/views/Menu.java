package views;

import controllers.GameCtrl;
import factories.BoardFactory;
import factories.PlayerFactory;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import lang.constants;
import models.Player;

import java.io.IOException;

public class Menu extends Parent {
    private final PlayerFactory playerFactory;
    private Board board;
    private Player player1;
    private Player player2;

    public Menu(GameCtrl gameCtrl, PlayerFactory playerFactory, BoardFactory boardFactory) throws IOException {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource("/scenes/menu.fxml"));
        this.playerFactory = playerFactory;
        borderPane.getStylesheets().add(constants.MENU_STYLE_SHEET);
        setPlayers();
        board = boardFactory.createBoard(gameCtrl, player1, player2);
        Pane pane = (Pane) borderPane.getCenter();
        pane.getChildren().add(board);
        this.getChildren().add(borderPane);
    }

    private void setPlayers() {
        player1 = playerFactory.createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        player2 = playerFactory.createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
    }
}
