package views;

import com.google.inject.Inject;
import controllers.GamePlayCtrl;
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
import java.util.function.Function;

public class MenuView extends Parent {

    private final GameViewFactory gameViewFactory;
    private final BorderPane menu;
    private Button buttonOne;
    private Button buttonTwo;
    private Button replay;
    private Button reset;
    private Label messages;
    private GameView gameView;
    private Pane centerPane;
    private GamePlayCtrl game;

    @Inject
    public MenuView(GamePlayCtrl gamePlayCtrl, GameViewFactory gameViewFactory) throws IOException {
        this.game = gamePlayCtrl;
        menu = FXMLLoader.load(getClass().getResource(constants.MENU_VIEW));
        centerPane = (Pane) menu.getCenter();
        this.getChildren().add(menu);
        this.gameViewFactory = gameViewFactory;
        getHeaderNodes();
        getMenuButtons();
        headerButtonVisibility(false);
        setButtons();
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
            game.twoPlayer();
            setupGame();
        };
    }

    private EventHandler<MouseEvent> setOnePlayers(String player1, String player2) {
        return event -> {
            game.onePlayer(player1, player2);
            setupGame();
        };
    }

    private void setupGame() {
        try {
            messages.setText(constants.EMPTY);
            game.setup();
            menu.getChildren().removeAll(buttonOne, buttonTwo);
            headerButtonVisibility(false);
            Function<MouseEvent, Player[]> play = play(game);
            gameView = gameViewFactory.createGameView(game.getBoard(), play);
            centerPane.getChildren().add(gameView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Function<MouseEvent, Player[]> play(GamePlayCtrl game) {
        return (MouseEvent event) -> {
            try {
                if (!game.over()) {
                    messages.setText(constants.EMPTY);
                    Label space = (Label) event.getSource();
                    game.set(getRow(space), getColumn(space));
                }
                if (game.over()) {
                    headerButtonVisibility(true);
                    displayWinner(game.getWinner());
                }
            } catch (OutOfBoundsException | NotVacantException | OutOfTurnException e) {
                messages.setText(e.getMessage());
            }
            return game.getBoard();
        };
    }

    private void displayWinner(Player winner) {
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

    private EventHandler<MouseEvent> resetGame() {
        return event -> {
            centerPane.getChildren().remove(gameView);
            setupGame();
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
        messages = (Label) header.lookup(constants.MESSAGES_ID);
    }

    private void setButtons() {
        buttonTwo.setOnMouseClicked(twoPlayer());
        buttonOne.setOnMouseClicked(onePlayer());
        reset.setOnMouseClicked(resetMenu());
        replay.setOnMouseClicked(resetGame());
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
