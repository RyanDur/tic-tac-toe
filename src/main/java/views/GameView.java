package views;

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

public class GameView extends Parent {
    private Player[] board;
    private Function<MouseEvent, Player[]> play;
    private GridPane grid;

    public GameView(Player[] board, Function<MouseEvent, Player[]> play) throws IOException {
        this.board = board;
        this.play = play;
        BorderPane borderPane = FXMLLoader.load(getClass().getResource(constants.GAME_VIEW));
        grid = (GridPane) borderPane.getCenter();
        this.getChildren().add(borderPane);
        setPlay();
    }

    private void setPlay() {
        clearBoard();
        fillBoard(board);
    }

    private void fillBoard(Player[] board) {
        grid.getChildren().stream().filter(space -> space instanceof Label)
                .forEach(label -> setSpace(board, (Label) label));
    }

    private void setSpace(Player[] board, Label label) {
        int position = calc(getRow(label), getColumn(label));
        Player player = board[position];
        if (player == null) label.setOnMouseClicked(event -> fillBoard(play.apply(event)));
        else label.setText(player.getPiece());
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