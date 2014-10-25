package views;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lang.constants;

import java.io.IOException;
import java.util.function.BiConsumer;

public class NavigationViewImpl extends Parent implements NavigationView {
    private final Button right;
    private final Button left;

    public NavigationViewImpl() throws IOException {
        GridPane nav = FXMLLoader.load(getClass().getResource(constants.NAVIGATION_VIEW));
        right = (Button) nav.lookup(constants.RIGHT_BUTTON_ID);
        left = (Button) nav.lookup(constants.LEFT_BUTTON_ID);
        this.getChildren().add(nav);
    }

    @Override
    public void setTwoPlayer(EventHandler<MouseEvent> twoPlayer) {
        right.setOnMouseClicked(twoPlayer);
    }

    @Override
    public void setOnePlayer(BiConsumer<String, String> onePlayer) {
        left.setOnMouseClicked(event -> {
            left.setText(constants.GAME_PIECE_ONE);
            right.setText(constants.GAME_PIECE_TWO);
            left.setOnMouseClicked(event2 -> onePlayer.accept(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO));
            right.setOnMouseClicked(event2 -> onePlayer.accept(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE));
        });
    }
}
