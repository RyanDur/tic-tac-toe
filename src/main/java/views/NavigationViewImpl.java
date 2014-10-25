package views;

import controllers.GamePlayCtrl;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import lang.constants;

import java.io.IOException;

public class NavigationViewImpl extends Parent implements NavigationView {
    private final Button right;
    private final Button left;
    private GamePlayCtrl game;

    public NavigationViewImpl(GamePlayCtrl game) throws IOException {
        GridPane nav = FXMLLoader.load(getClass().getResource(constants.NAVIGATION_VIEW));
        right = (Button) nav.lookup(constants.RIGHT_BUTTON_ID);
        left = (Button) nav.lookup(constants.LEFT_BUTTON_ID);
        this.game = game;
        this.getChildren().add(nav);
    }

    @Override
    public void setTwoPlayer() {
        right.setOnMouseClicked(event -> game.twoPlayer());
    }

    @Override
    public void setOnePlayer() {
        left.setOnMouseClicked(event -> {
            left.setText(constants.GAME_PIECE_ONE);
            right.setText(constants.GAME_PIECE_TWO);
            left.setOnMouseClicked(event2 -> game.onePlayer(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO));
            right.setOnMouseClicked(event2 -> game.onePlayer(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE));
        });
    }
}
