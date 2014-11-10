package tictactoe.views.elements;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import tictactoe.lang.Constants;

import java.io.IOException;
import java.util.function.Function;
import java.util.stream.IntStream;

public class BoardImpl extends Parent implements Board {
    private Function<MouseEvent, Character[]> play;
    private GridPane grid;
    private int side;
    private final BorderPane borderPane;

    public BoardImpl() {
        borderPane = getFXML();
        this.getChildren().add(borderPane);
    }

    @Override
    public void setup(Function<MouseEvent, Character[]> play, int side) {
        this.side = side;
        this.play = play;
        buildGrid(side);
        borderPane.setCenter(grid);
    }

    @Override
    public void setBoard(Character[] board) {
        grid.getChildren().stream().filter(space -> space instanceof Label)
                .forEach(cell -> setSpace(board, (Label) cell));
    }

    private void buildGrid(int side) {
        grid = new GridPane();
        grid.setGridLinesVisible(true);
        grid.setId("grid");
        IntStream.range(0, side).forEach(x -> {
            IntStream.range(0, side).forEach(y -> {
                Label cell = new Label();
                cell.alignmentProperty().setValue(Pos.CENTER);
                cell.setStyle("-fx-font-size:80;");
                cell.setPrefHeight(200);
                cell.setPrefWidth(200);
                cell.setId("cell" + ((x * side) + y));
                grid.add(cell, y, x);
            });
        });
    }

    private void setSpace(Character[] board, Label cell) {
        Character player = board[calc(getRow(cell), getColumn(cell))];
        if (player == null) {
            cell.setText(Constants.EMPTY);
            cell.setOnMouseClicked(click -> setBoard(play.apply(click)));
        } else cell.setText(String.valueOf(player));
    }

    private int calc(int row, int column) {
        return (row * side) + column;
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
            return FXMLLoader.load(getClass().getResource(Constants.GAME_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}