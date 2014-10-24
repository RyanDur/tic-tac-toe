package views;

import com.google.inject.Inject;
import controllers.GameCtrl;
import controllers.PlayerCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.GameViewFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import lang.constants;
import models.Player;

import java.io.IOException;
import java.util.function.Consumer;

public class MenuView extends Parent {

    private final GameCtrl gameCtrl;
    private final GameViewFactory gameViewFactory;
    private final BorderPane menu;
    private Button buttonOne;
    private Button buttonTwo;
    private Button replay;
    private Button reset;
    private Label messages;
    private GameView gameView;
    private Pane centerPane;
    private PlayerCtrl playerCtrl;

    @Inject
    public MenuView(GameCtrl gameCtrl, PlayerCtrl playerCtrl, GameViewFactory gameViewFactory) throws IOException {
        menu = FXMLLoader.load(getClass().getResource(constants.MENU_VIEW));
        this.playerCtrl = playerCtrl;
        this.gameCtrl = gameCtrl;
        this.gameViewFactory = gameViewFactory;
        getHeaderNodes();
        getMenuButtons();
        headerButtonVisibility(false);
        setButtons();
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

    private EventHandler<MouseEvent> twoPlayer() {
        return event -> {
            playerCtrl.setupTwoPlayer();
            setupGame();
        };
    }

    private EventHandler<MouseEvent> setOnePlayers(String player1, String player2) {
        return event -> {
            playerCtrl.setupOnePlayer(player1, player2);
            setupGame();
        };
    }

    private void setupGame() {
        menu.getChildren().removeAll(buttonOne, buttonTwo);
        centerPane = (Pane) menu.getCenter();
        centerPane.getChildren().add(gameView);
        headerButtonVisibility(false);
        gameCtrl.setup();
        Consumer<MouseEvent> play = play(playerCtrl, gameCtrl);
        gameView = gameViewFactory.createGameView(gameCtrl, playerCtrl, play);
        centerPane.getChildren().add(gameView);
    }

    private Consumer<MouseEvent> play(PlayerCtrl playerCtrl, GameCtrl gameCtrl) {
        return (MouseEvent event) -> {
            try {
                if (!gameCtrl.gameOver()) {
                    messages.setText(constants.EMPTY);
                    Player player = playerCtrl.getPlayer(gameCtrl.getBoard());
                    Label space = (Label) event.getSource();
                    player.setCoordinates(getRow(space), getColumn(space));
                    gameCtrl.setPiece(player);
                    if (playerCtrl.playerCount() == 1 && !gameCtrl.gameOver())
                        gameCtrl.setPiece(playerCtrl.getComputerPlayer(gameCtrl.getBoard()));
                }
                if (gameCtrl.gameOver()) {
                    headerButtonVisibility(true);
                    displayWinner(gameCtrl);
                }
            } catch (OutOfTurnException | NotVacantException | OutOfBoundsException e) {
                messages.setText(e.getMessage());
            }
        };
    }

    private void displayWinner(GameCtrl gameCtrl) {
        Player winner = gameCtrl.getWinner();
        if (winner == null) messages.setText(constants.DRAW_MESSAGE);
        else messages.setText(winner.getPiece() + constants.HAS_WON_MESSAGE);
    }

    private EventHandler<MouseEvent> resetMenu() {
        return event -> {
            messages.setText(constants.EMPTY);
            headerButtonVisibility(false);
            centerPane.getChildren().remove(gameView);
            setPlayerChoiceButtons();
            setButtons();
        };
    }

    private void setPlayerChoiceButtons() {
        buttonOne.setText(constants.ONE_PLAYER);
        buttonTwo.setText(constants.TWO_PLAYER);
        menu.setLeft(buttonOne);
        menu.setRight(buttonTwo);
    }

    private void headerButtonVisibility(boolean hide) {
        replay.setVisible(hide);
        reset.setVisible(hide);
    }

    private void getMenuButtons() {
        buttonOne = (Button) menu.getLeft();
        buttonTwo = (Button) menu.getRight();
    }

    private void getHeaderNodes() {
        HBox header = (HBox) menu.getTop();
        replay = (Button) header.lookup(constants.REPLAY_ID);
        reset = (Button) header.lookup(constants.RESET_ID);
        reset.setOnMouseClicked(resetMenu());
        messages = (Label) header.lookup(constants.MESSAGES_ID);
    }

    private void setButtons() {
        buttonTwo.setOnMouseClicked(twoPlayer());
        buttonOne.setOnMouseClicked(onePlayer());
    }

    private int getColumn(Node node) {
        Integer column = GridPane.getColumnIndex(node);
        return column == null ? 0 : column;
    }

    private int getRow(Node node) {
        Integer row = GridPane.getRowIndex(node);
        return row == null ? 0 : row;
    }
}
