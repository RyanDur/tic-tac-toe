package views;

import com.google.inject.Inject;
import controllers.GamePlayCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.ViewFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import lang.constants;
import models.Player;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class TicTacToeImpl extends Parent {
    private GameViewImpl gameView;
    private Pane centerPane;
    private GamePlayCtrl game;
    private HeaderView header;
    private ViewFactory viewFactory;
    private final BorderPane menu;
    private NavigationView nav;

    @Inject
    public TicTacToeImpl(GamePlayCtrl game, ViewFactory viewFactory, HeaderView header) throws IOException {
        menu = FXMLLoader.load(getClass().getResource(constants.MENU_VIEW));
        this.getChildren().add(menu);
        this.viewFactory = viewFactory;
        this.game = game;
        centerPane = (Pane) menu.getCenter();
        setHeader(header);
        setCenter((Node) getNav());
    }

    private void setupGame() {
        try {
            header.clearMessage();
            header.setButtonsVisibility(false);
            gameView = getGameView();
            swapCenter((Node) nav, gameView);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Function<MouseEvent, Player[]> play(GamePlayCtrl game) {
        return (MouseEvent event) -> {
            try {
                if (!game.over()) {
                    header.clearMessage();
                    Label space = (Label) event.getSource();
                    game.set(getRow(space), getColumn(space));
                }
                if (game.over()) {
                    header.setButtonsVisibility(true);
                    displayWinner(game.getWinner());
                }
            } catch (OutOfBoundsException | NotVacantException | OutOfTurnException e) {
                header.setMessage(e.getMessage());
            }
            return game.getBoard();
        };
    }

    private void displayWinner(Player winner) {
        if (winner == null) header.setMessage(constants.DRAW_MESSAGE);
        else header.setMessage(winner.getPiece() + constants.HAS_WON_MESSAGE);
    }

    private EventHandler<MouseEvent> resetMenu() {
        return event -> {
            header.clearMessage();
            header.setButtonsVisibility(false);
            swapCenter(gameView, (Node) getNav());
        };
    }

    private EventHandler<MouseEvent> resetGame() {
        return event -> {
            removeCenter(gameView);
            game.reset();
            setupGame();
        };
    }

    private int getColumn(Node node) {
        Integer column = GridPane.getColumnIndex(node);
        return column == null ? 0 : column;
    }

    private int getRow(Node node) {
        Integer row = GridPane.getRowIndex(node);
        return row == null ? 0 : row;
    }

    private void removeCenter(Node node) {
        centerPane.getChildren().remove(node);
    }

    private void setCenter(Node node) {
        centerPane.getChildren().add(node);
    }

    private void swapCenter(Node node1, Node node2) {
        removeCenter(node1);
        setCenter(node2);
    }

    private NavigationView getNav() {
        try {
            nav = viewFactory.createNav();
            nav.setOnePlayer(getOnePlayer());
            nav.setTwoPlayer(getTwoPlayer());
            return nav;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private EventHandler<MouseEvent> getTwoPlayer() {
        return event -> {
            game.twoPlayer();
            setupGame();
        };
    }

    private BiConsumer<String, String> getOnePlayer() {
        return (player1, player2) -> {
            game.onePlayer(player1, player2);
            setupGame();
        };
    }

    private GameViewImpl getGameView() throws IOException {
        return viewFactory.createGameView();
    }

    private void setHeader(HeaderView header) {
        this.header = header;
        Pane headerPane = (Pane) menu.getTop();
        this.header.setButtonsVisibility(false);
        headerPane.getChildren().add((Node) this.header);
        this.header.setReplay(resetGame());
        this.header.setReset(resetMenu());
    }
}
