package views;

import controllers.GameCtrl;
import exceptions.NotVacantException;
import exceptions.OutOfBoundsException;
import exceptions.OutOfTurnException;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.GridPane;
import lang.constants;
import models.Player;


public class Board extends Parent {

    private final GameCtrl gameCtrl;
    private final GridPane grid;
    private final Label label;
    private final Player player1;
    private final Player player2;
    private Player currentPlayer;

    public Board(GameCtrl gameCtrl, Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.getStylesheets().add(constants.BOARD_STYLE_SHEET);
        this.gameCtrl = gameCtrl;
        grid = new GridPane();
        grid.setId(constants.BOARD_ID);
        label = new Label();
        GridPane.setConstraints(label, constants.SIDE, constants.SIDE);
        fillBoard(gameCtrl.getBoard());
        this.getChildren().add(grid);
    }

    private void fillBoard(Player[] board) {
        for (int y = 0; y < constants.SIDE; y++) {
            for (int x = 0; x < constants.SIDE; x++) {
                Labeled space = new Label();
                String id = x + "," + y;
                space.setId(id);
                final int finalY = y;
                final int finalX = x;
                Player player = board[(x*constants.SIDE)+y];
                if(player == null) {
                    space.setOnMouseClicked(mouseEvent -> {
                        try {
                            setPiece(finalX, finalY);
                        } catch (OutOfBoundsException e) {
                            e.printStackTrace();
                        }
                    });
                } else {
                    space.setText(player.getPiece());
                }

                grid.add(space, x, y);
            }
        }
    }

    private void setPiece(int x, int y) throws OutOfBoundsException {
        try {
            Player player = getCurrentPlayer();
            player.setCoordinates(x, y);
            gameCtrl.setPiece(player);
            fillBoard(gameCtrl.getBoard());
        } catch (OutOfTurnException | NotVacantException e) {
            e.printStackTrace();
        }
    }

    private Player getCurrentPlayer() {
        if (currentPlayer == player1) {
            currentPlayer = player2;
        } else {
            currentPlayer = player1;
        }
        return currentPlayer;
    }
}
