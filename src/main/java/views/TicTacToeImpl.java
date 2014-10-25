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
import views.elements.HeaderView;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class TicTacToeImpl extends Parent implements TicTacToe {
    private Pane centerPane;
    private GamePlayCtrl game;
    private HeaderView header;
    private ViewFactory viewFactory;
    private final BorderPane ticTacToe;

    @Inject
    public TicTacToeImpl(GamePlayCtrl game, ViewFactory viewFactory, HeaderView header) {
        ticTacToe = getFXML();
        this.getChildren().add(ticTacToe);
        this.viewFactory = viewFactory;
        this.game = game;
        centerPane = (Pane) ticTacToe.getCenter();
        this.header = setupHeader(header);
        setupMenu();
    }

    private EventHandler<MouseEvent> resetMenu() {
        return event -> {
            clearHeader(header);
            setupMenu();
        };
    }

    private EventHandler<MouseEvent> resetGame() {
        return event -> {
            clearHeader(header);
            game.reset();
            setupBoard();
        };
    }

    private EventHandler<MouseEvent> setTwoPlayer() {
        return event -> {
            game.twoPlayer();
            setupBoard();
        };
    }

    private BiConsumer<String, String> setOnePlayer() {
        return (player1, player2) -> {
            game.onePlayer(player1, player2);
            setupBoard();
        };
    }

    private Function<MouseEvent, Player[]> play(GamePlayCtrl game) {
        return click -> {
            try {
                if (!game.over()) {
                    header.clearMessage();
                    Label space = (Label) click.getSource();
                    game.set(getRow(space), getColumn(space));
                }
                if (game.over()) {
                    header.setButtonsVisibility(true);
                    header.displayWinner(game.getWinner());
                }
            } catch (OutOfBoundsException | NotVacantException | OutOfTurnException e) {
                header.setMessage(e.getMessage());
            }
            return game.getBoard();
        };
    }

    private void setupBoard() {
        swapCenter((Node) viewFactory.createGameView(game.getBoard(), play(game)));
    }

    private void setupMenu() {
        swapCenter((Node) viewFactory.createMenu(setOnePlayer(), setTwoPlayer()));
    }

    private HeaderView setupHeader(HeaderView header) {
        Pane headerPane = (Pane) ticTacToe.getTop();
        headerPane.getChildren().add((Node) header);
        header.setButtonsVisibility(false);
        header.setReplay(resetGame());
        header.setReset(resetMenu());
        return header;
    }

    private void clearHeader(HeaderView header) {
        header.clearMessage();
        header.setButtonsVisibility(false);
    }

    private void swapCenter(Node node) {
        centerPane.getChildren().clear();
        centerPane.getChildren().add(node);
    }

    private int getColumn(Node node) {
        Integer column = GridPane.getColumnIndex(node);
        return column == null ? 0 : column;
    }

    private int getRow(Node node) {
        Integer row = GridPane.getRowIndex(node);
        return row == null ? 0 : row;
    }

    private BorderPane getFXML() {
        try {
            return FXMLLoader.load(getClass().getResource(constants.TIC_TAC_TOE_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
