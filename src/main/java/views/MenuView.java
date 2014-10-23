package views;

import com.google.inject.Inject;
import controllers.GameCtrl;
import factories.GameViewFactory;
import factories.PlayerFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private Button replay;
    private Button reset;
    private Label messages;
    private GameView gameView;
    private Pane centerPane;

    @Inject
    public MenuView(GameCtrl gameCtrl, PlayerFactory playerFactory, GameViewFactory gameViewFactory) throws IOException {
        menu = FXMLLoader.load(getClass().getResource(constants.MENU_VIEW));
        this.gameCtrl = gameCtrl;
        this.playerFactory = playerFactory;
        this.gameViewFactory = gameViewFactory;
        header = (HBox) menu.getTop();
        replay = (Button) header.lookup(constants.REPLAY_ID);
        buttonOne = (Button) menu.getLeft();
        buttonTwo = (Button) menu.getRight();
        getHeaderNodes();
        headerButtonVisibility(false);
        setButtons();
        this.getChildren().add(menu);
    }

    private void getHeaderNodes() {
        reset = (Button) header.lookup(constants.RESET_ID);
        reset.setOnMouseClicked(resetMenu());
        messages = (Label) header.lookup(constants.MESSAGES_ID);
    }

    private void setButtons() {
        buttonTwo.setOnMouseClicked(twoPlayer());
        buttonOne.setOnMouseClicked(onePlayer());
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
        try {
            gameView = gameViewFactory.createGameView(gameCtrl, player1, player2, messages, reset, replay);
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert gameView != null;
        menu.getChildren().removeAll(buttonOne, buttonTwo);
        centerPane = (Pane) menu.getCenter();
        centerPane.getChildren().add(gameView);
    }

    private EventHandler<MouseEvent> twoPlayer() {
        return event -> {
            Player player1 = playerFactory.createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
            Player player2 = playerFactory.createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
            setupGame(player1, player2);
        };
    }

    private void headerButtonVisibility(boolean hide) {
        replay.setVisible(hide);
        reset.setVisible(hide);
    }

    private EventHandler<MouseEvent> resetMenu() {
        return event -> {
            messages.setText(constants.EMPTY);
            headerButtonVisibility(false);
            centerPane.getChildren().remove(gameView);
            buttonOne.setText(constants.ONE_PLAYER);
            buttonTwo.setText(constants.TWO_PLAYER);
            menu.setLeft(buttonOne);
            menu.setRight(buttonTwo);
            setButtons();
        };
    }
}
