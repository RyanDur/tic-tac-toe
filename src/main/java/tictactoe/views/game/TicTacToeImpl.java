package tictactoe.views.game;

import com.google.inject.Inject;
import tictactoe.exceptions.NotVacantException;
import tictactoe.exceptions.OutOfBoundsException;
import tictactoe.exceptions.OutOfTurnException;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import tictactoe.lang.Constants;
import tictactoe.game.Game;
import tictactoe.views.elements.Board;
import tictactoe.views.elements.Header;
import tictactoe.views.elements.Menu;

import java.io.IOException;
import java.util.function.Consumer;
import java.util.function.Function;

public class TicTacToeImpl extends Parent implements TicTacToe {
    private Pane centerPane;
    private Game game;
    private Header header;
    private final BorderPane ticTacToe;
    private Menu menu;
    private Board board;

    @Inject
    public TicTacToeImpl(Game game, Header header, Menu menu, Board board) {
        ticTacToe = getFXML();
        this.menu = menu;
        this.board = board;
        this.getChildren().add(ticTacToe);
        this.game = game;
        centerPane = (Pane) ticTacToe.getCenter();
        this.header = setupHeader(header);
        setupMenu();
    }

    private EventHandler<MouseEvent> resetMenu() {
        return event -> {
            game.reset();
            menu.reset();
            setupMenu();
        };
    }

    private EventHandler<MouseEvent> resetGame() {
        return event -> setupBoard();
    }

    private EventHandler<MouseEvent> setTwoPlayer() {
        return event -> setupBoard();
    }

    private Consumer<Character> setOnePlayer() {
        return (piece) -> {
            game.setComputer(piece);
            setupBoard();
        };
    }

    private Function<MouseEvent, Character[]> play(Game game) {
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
        clearHeader(header);
        game.setup();
        board.setPlay(play(game));
        board.setBoard(game.getBoard());
        swapCenter((Node) board);
    }

    private void setupMenu() {
        clearHeader(header);
        menu.setOnePlayer(setOnePlayer());
        menu.setTwoPlayer(setTwoPlayer());
        swapCenter((Node) menu);
    }

    private Header setupHeader(Header header) {
        Pane headerPane = (Pane) ticTacToe.getTop();
        headerPane.getChildren().add((Node) header);
        header.setButtonsVisibility(false);
        header.setReplay(resetGame());
        header.setReset(resetMenu());
        return header;
    }

    private void clearHeader(Header header) {
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
            return FXMLLoader.load(getClass().getResource(Constants.TIC_TAC_TOE_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}