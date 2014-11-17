package tictactoe.views.elements;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import tictactoe.lang.Constants;

import java.io.IOException;
import java.util.function.*;

public class MenuImpl extends Parent implements Menu {
    private final BiConsumer<String, EventHandler<MouseEvent>> leftButton;
    private final BiConsumer<String, EventHandler<MouseEvent>> rightButton;
    private final int size;
    private GridPane menu;

    public MenuImpl() {
        menu = getFXML();
        size = Constants.SMALL_BOARD;
        leftButton = getButton(Constants.LEFT_BUTTON_ID);
        rightButton = getButton(Constants.RIGHT_BUTTON_ID);
        this.getChildren().add(menu);
    }

    @Override
    public void setup(BiConsumer<Integer, Character> game) {
        leftButton.accept(Constants.SMALL_BOARD_BUTTON, event -> setupPlayer(game, Constants.SMALL_BOARD));
        rightButton.accept(Constants.LARGE_BOARD_BUTTON, event -> setupPlayer(game, Constants.LARGE_BOARD));
    }

    private void setupPlayer(BiConsumer<Integer, Character> game, int size) {
        leftButton.accept(Constants.ONE_PLAYER, event -> setupGame(setGame.apply(size, game)));
        rightButton.accept(Constants.TWO_PLAYER, event -> game.accept(size, null));
    }

    private void setupGame(Function<Character, EventHandler<MouseEvent>> game) {
        leftButton.accept(String.valueOf(Constants.GAME_PIECE_ONE), game.apply(Constants.GAME_PIECE_TWO));
        rightButton.accept(String.valueOf(Constants.GAME_PIECE_TWO), game.apply(Constants.GAME_PIECE_ONE));
    }

    private BiConsumer<String, EventHandler<MouseEvent>> getButton(String buttonId) {
        Button button = (Button) menu.lookup(buttonId);
        return (label, event) -> {
            button.setText(label);
            button.setOnMouseClicked(event);
        };
    }

    private BiFunction<Integer, BiConsumer<Integer, Character>, Function<Character, EventHandler<MouseEvent>>> setGame =
            (size, game) -> piece -> event -> game.accept(size, piece);

    private GridPane getFXML() {
        try {
            return FXMLLoader.load(getClass().getResource(Constants.MENU_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}