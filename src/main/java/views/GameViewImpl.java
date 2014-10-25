package views;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import lang.constants;
import models.Player;

import java.io.IOException;
import java.util.function.Function;

public class GameViewImpl extends Parent implements GameView {
    private Function<MouseEvent, Player[]> play;
    private GridPane grid;

    public GameViewImpl() throws IOException {
        BorderPane borderPane = FXMLLoader.load(getClass().getResource(constants.GAME_VIEW));
        grid = (GridPane) borderPane.getCenter();
        this.getChildren().add(borderPane);
    }


    @Override
    public void setup(Player[] board) {
        Platform.runLater(() -> fillBoard(board));
    }

    @Override
    public void clear() {
        Platform.runLater(this::clearBoard);
    }

    @Override
    public void setPlay(Function<MouseEvent, Player[]> play) {
        this.play = play;
    }


    private void fillBoard(Player[] board) {
        grid.getChildren().stream().filter(space -> space instanceof Label)
                .forEach(label -> setSpace(board, (Label) label));
    }

    private void setSpace(Player[] board, Label cell) {
        Player player = board[calc(getRow(cell), getColumn(cell))];
        if (player == null) cell.setOnMouseClicked(event -> fillBoard(play.apply(event)));
        else cell.setText(player.getPiece());
    }

    private void clearBoard() {
        grid.getChildren().stream().
                filter(space -> space instanceof Label).
                forEach(space -> ((Label) space).setText(constants.EMPTY));
    }

    private int calc(int row, int column) {
        return (row * constants.SIDE) + column;
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