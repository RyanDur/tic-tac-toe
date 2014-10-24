package views;

import controllers.GameCtrl;
import controllers.PlayerCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfTurnException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import lang.constants;
import models.ComputerPlayer;
import models.Player;

import java.io.IOException;
import java.util.function.Function;

public class GameView extends Parent {
    private final PlayerCtrl playerCtrl;
    private Function<MouseEvent, Player[]> play;
    private GameCtrl gameCtrl;
    private GridPane grid;

    public GameView(GameCtrl gameCtrl, PlayerCtrl playerCtrl, Function<MouseEvent, Player[]> play) throws IOException {
        this.playerCtrl = playerCtrl;
        this.play = play;
        BorderPane borderPane = FXMLLoader.load(getClass().getResource(constants.GAME_VIEW));
        this.gameCtrl = gameCtrl;
        grid = (GridPane) borderPane.getCenter();
        this.getChildren().add(borderPane);
        setPlay();
    }

    private void setPlay() {
        clearBoard();
        gameCtrl.setup();
        fillBoard(gameCtrl.getBoard());
        checkForComputer();
    }

    private void checkForComputer() {
        try {
            ComputerPlayer computer = playerCtrl.getComputerPlayer(gameCtrl.getBoard());
            if (computer != null && computer.getPiece().equals(constants.GAME_PIECE_ONE)) gameCtrl.setPiece(computer);
        } catch (OutOfTurnException | NotVacantException e) {
            e.printStackTrace();
        }
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