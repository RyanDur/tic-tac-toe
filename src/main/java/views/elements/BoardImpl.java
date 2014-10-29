package views.elements;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import lang.constants;

import java.io.IOException;
import java.util.function.Function;

public class BoardImpl extends Parent implements Board {
    private Function<MouseEvent, String[]> play;
    private GridPane grid;

    public BoardImpl(String[] board, Function<MouseEvent, String[]> play) {
        BorderPane borderPane = getFXML();
        this.play = play;
        grid = (GridPane) borderPane.getCenter();
        fillBoard(board);
        this.getChildren().add(borderPane);
    }

    private void fillBoard(String[] board) {
        grid.getChildren().stream().filter(space -> space instanceof Label)
                .forEach(cell -> setSpace(board, (Label) cell));
    }

    private void setSpace(String[] board, Label cell) {
        String player = board[calc(getRow(cell), getColumn(cell))];
        if (player == null) {
            cell.setText(constants.EMPTY);
            cell.setOnMouseClicked(click -> fillBoard(play.apply(click)));
        } else cell.setText(player);
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

    private BorderPane getFXML() {
        try {
            return FXMLLoader.load(getClass().getResource(constants.GAME_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}