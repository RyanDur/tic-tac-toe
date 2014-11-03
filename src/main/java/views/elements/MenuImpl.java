package views.elements;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import lang.constants;

import java.io.IOException;
import java.util.function.Consumer;

public class MenuImpl extends Parent implements Menu {
    private final Button right;
    private final Button left;

    public MenuImpl() {
        GridPane nav = getFXML();
        right = (Button) nav.lookup(constants.RIGHT_BUTTON_ID);
        left = (Button) nav.lookup(constants.LEFT_BUTTON_ID);
        this.getChildren().add(nav);
    }

    @Override
    public void setTwoPlayer(EventHandler<MouseEvent> twoPlayer) {
        right.setOnMouseClicked(twoPlayer);
    }

    @Override
    public void setOnePlayer(Consumer<Character> computer) {
        left.setOnMouseClicked(event -> {
            left.setText(String.valueOf(constants.GAME_PIECE_ONE));
            right.setText(String.valueOf(constants.GAME_PIECE_TWO));
            left.setOnMouseClicked(event2 -> computer.accept(constants.GAME_PIECE_TWO));
            right.setOnMouseClicked(event2 -> computer.accept(constants.GAME_PIECE_ONE));
        });
    }

    @Override
    public void reset() {
        Platform.runLater(() -> {
            left.setText(constants.ONE_PLAYER);
            right.setText(constants.TWO_PLAYER);
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
