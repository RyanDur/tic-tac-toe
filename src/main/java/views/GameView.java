package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import factories.PlayerFactory;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lang.constants;
import models.Player;

import java.io.IOException;

public class GameView extends Parent {
    private final GameCtrl gameCtrl;
    private final PlayerFactory playerFactory;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private final GridPane grid;
    private final BorderPane borderPane;
    private Button play;

    public GameView(GameCtrl gameCtrl, PlayerFactory playerFactory) throws IOException {
        borderPane = FXMLLoader.load(getClass().getResource(constants.GAME_VIEW));
        grid = (GridPane) borderPane.getCenter();
        grid.setVisible(false);
        this.gameCtrl = gameCtrl;
        this.playerFactory = playerFactory;
        this.getChildren().add(borderPane);
        setPlay();
    }

    private void setPlay() {
        HBox header = (HBox) borderPane.getTop();
        header.getChildren().stream()
                .filter(node -> node.getId().equals(constants.PLAY_ID))
                .forEach(node -> play = (Button) node);

        play.setOnMouseClicked(event -> {
            grid.setVisible(true);
            fillBoard(gameCtrl.getBoard());
            setPlayers();
        });
    }

    private void setPlayers() {
        player1 = playerFactory.createPlayer(constants.GAME_PIECE_ONE, constants.SIDE);
        player2 = playerFactory.createPlayer(constants.GAME_PIECE_TWO, constants.SIDE);
    }

    private void fillBoard(Player[] board) {
        grid.getChildren().stream().filter(space -> space instanceof Label)
                .forEach(label -> setSpace(board, (Label) label));
    }

    private void setSpace(Player[] board, Label label) {
        int position = calc(getRow(label), getColumn(label));
        Player player = board[position];
        if (player == null) {
            label.setOnMouseClicked(setPiece());
        } else {
            label.setText(player.getPiece());
        }
    }

    private EventHandler<MouseEvent> setPiece() {
        return mouseEvent -> {
            try {
                System.out.println();
                if (!gameCtrl.gameOver()) {
                    Label space = (Label) mouseEvent.getSource();
                    Player player = getCurrentPlayer();
                    player.setCoordinates(getRow(space), getColumn(space));
                    gameCtrl.setPiece(player);
                    fillBoard(gameCtrl.getBoard());
                }
            } catch (OutOfTurnException | NotVacantException | OutOfBoundsException e) {
                e.printStackTrace();
            }
        };
    }

    private Player getCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        return currentPlayer;
    }

    private int calc(int row, int column) {
        return (row * constants.SIDE) + column;
    }

    private int getColumn(Label label) {
        Integer column = GridPane.getColumnIndex(label);
        if (column == null) column = 0;
        return column;
    }

    private int getRow(Label label) {
        Integer row = GridPane.getRowIndex(label);
        if (row == null) row = 0;
        return row;
    }
}