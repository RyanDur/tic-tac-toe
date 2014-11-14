package tictactoe.views.elements;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import tictactoe.lang.Constants;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

public class MenuImpl extends Parent implements Menu {
    private final Button right;
    private final Button left;

    public MenuImpl() {
        GridPane nav = getFXML();
        left = (Button) nav.lookup(Constants.LEFT_BUTTON_ID);
        right = (Button) nav.lookup(Constants.RIGHT_BUTTON_ID);
        this.getChildren().add(nav);
    }

    @Override
    public void setUpMenu(BiConsumer<Integer, Character> game) {
        setButtons(event -> setupPlayer(game, Constants.SMALL_BOARD),
                event -> setupPlayer(game, Constants.LARGE_BOARD),
                Constants.SMALL_BOARD_BUTTON, Constants.LARGE_BOARD_BUTTON);
    }

    private void setupPlayer(BiConsumer<Integer, Character> game, int size) {
        setButtons(setupOnePlayer.apply(size, game), setupTwoPlayer.apply(size, game),
                Constants.ONE_PLAYER, Constants.TWO_PLAYER);
    }

    private void setupGame(Function<Character, EventHandler<MouseEvent>> setup) {
        setButtons(setup.apply(Constants.GAME_PIECE_TWO), setup.apply(Constants.GAME_PIECE_ONE),
                String.valueOf(Constants.GAME_PIECE_ONE), String.valueOf(Constants.GAME_PIECE_TWO));
    }

    private void setButtons(EventHandler<MouseEvent> event1, EventHandler<MouseEvent> event2, String label1, String label2) {
        left.setText(label1);
        right.setText(label2);
        left.setOnMouseClicked(event1);
        right.setOnMouseClicked(event2);
    }

    private BiFunction<Integer, BiConsumer<Integer, Character>, Function<Character, EventHandler<MouseEvent>>> setGame =
            (size, game) -> piece -> event -> game.accept(size, piece);

    private BiFunction<Integer, BiConsumer<Integer, Character>, EventHandler<MouseEvent>> setupTwoPlayer =
            (size, game) -> event -> game.accept(size, null);

    private BiFunction<Integer, BiConsumer<Integer, Character>, EventHandler<MouseEvent>> setupOnePlayer =
            (size, game) -> event -> setupGame(setGame.apply(size, game));

    private GridPane getFXML() {
        try {
            return FXMLLoader.load(getClass().getResource(Constants.MENU_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}