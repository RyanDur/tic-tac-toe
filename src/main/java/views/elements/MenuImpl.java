package views.elements;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lang.constants;

import java.io.IOException;
import java.util.function.BiConsumer;

public class MenuImpl extends Parent implements Menu {
    private final Button right;
    private final Button left;

    public MenuImpl(BiConsumer<String, String> onePlayer, EventHandler<MouseEvent> twoPlayer) {
        GridPane nav = getFXML();
        right = (Button) nav.lookup(constants.RIGHT_BUTTON_ID);
        left = (Button) nav.lookup(constants.LEFT_BUTTON_ID);
        setOnePlayer(onePlayer);
        setTwoPlayer(twoPlayer);
        this.getChildren().add(nav);
    }

    private void setTwoPlayer(EventHandler<MouseEvent> twoPlayer) {
        right.setOnMouseClicked(twoPlayer);
    }

    private void setOnePlayer(BiConsumer<String, String> onePlayer) {
        left.setOnMouseClicked(event -> {
            left.setText(constants.GAME_PIECE_ONE);
            right.setText(constants.GAME_PIECE_TWO);
            left.setOnMouseClicked(event2 -> onePlayer.accept(constants.GAME_PIECE_ONE, constants.GAME_PIECE_TWO));
            right.setOnMouseClicked(event2 -> onePlayer.accept(constants.GAME_PIECE_TWO, constants.GAME_PIECE_ONE));
        });
    }

    private GridPane getFXML() {
        try {
            return FXMLLoader.load(getClass().getResource(constants.MENU_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
