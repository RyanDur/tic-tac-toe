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
    private Function<MouseEvent, Character[]> play;
    private GridPane grid;

    public BoardImpl() {
        BorderPane borderPane = getFXML();
        grid = (GridPane) borderPane.getCenter();
        this.getChildren().add(borderPane);
    }

    @Override
    public void setPlay(Function<MouseEvent, Character[]> play) {
        this.play = play;
    }

    @Override
    public void setBoard(Character[] board) {
        grid.getChildren().stream().filter(space -> space instanceof Label)
                .forEach(cell -> setSpace(board, (Label) cell));
    }

    private void setSpace(Character[] board, Label cell) {
        Character player = board[calc(getRow(cell), getColumn(cell))];
        if (player == null) {
            cell.setText(constants.EMPTY);
            cell.setOnMouseClicked(click -> setBoard(play.apply(click)));
        } else cell.setText(String.valueOf(player));
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