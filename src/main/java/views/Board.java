package views;

import controllers.GameCtrl;
import javafx.geometry.Pos;
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

    public Board(GameCtrl gameCtrl) {
        this.getStylesheets().add(constants.BOARD_STYLE_SHEET);
        this.gameCtrl = gameCtrl;
        grid = new GridPane();
        grid.setId(constants.BOARD_ID);
        grid.setAlignment(Pos.CENTER);
        grid.setGridLinesVisible(true);
        label = new Label();
        GridPane.setConstraints(label, constants.SIDE, constants.SIDE);
        grid.setPrefSize(600, 600);
        fillBoard(gameCtrl.getBoard());
        this.getChildren().add(grid);
    }

    private void fillBoard(Player[] board) {
        System.out.println(board);
        for (int y = 0; y < constants.SIDE; y++) {
            for (int x = 0; x < constants.SIDE; x++) {
                Labeled space = new Label();
                //space.setAlignment(Pos.CENTER);
                int position = (x * constants.SIDE) + y;

                space.setText(" ");

                grid.add(space, x, y);
            }
        }
    }
}
