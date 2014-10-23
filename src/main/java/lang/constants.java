package lang;

import java.util.Arrays;
import java.util.List;

public class constants {
    public static final String GAME_PIECE_ONE = "X";
    public static final String GAME_PIECE_TWO = "O";
    public static final int SIDE = 3;
    public static final String GAME_VIEW = "/scenes/game.fxml";
    public static final String MESSAGES_ID = "#messages";
    public static final String DRAW_MESSAGE = "The Game is a Draw!";
    public static final String NOT_VACANT_MESSAGE = "Space is occupied,\n please choose another.";
    public static final String OUT_OF_BOUNDS_MESSAGE = "You chose a space out of bounds, HOW!!??";
    public static final String OUT_OF_TURN_MESSAGE = "It's not your turn!";
    public static final String EMPTY = "";
    public static final List<Integer[]> CORNERS = Arrays.asList(
            new Integer[]{0, 0},
            new Integer[]{0, SIDE - 1},
            new Integer[]{SIDE - 1, 0},
            new Integer[]{SIDE - 1, SIDE - 1});;
    public static final int WIN_WEIGHT = 1;
    public static final int LOSE_WEIGHT = -1;
    public static final int DRAW_WEIGHT = 0;
    public static final String MENU_VIEW = "/scenes/menu.fxml";
    public static final String REPLAY_ID = "#replay";
    public static final String RESET_ID = "#reset";
    public static final String ONE_PLAYER = "1 Player";
    public static final String TWO_PLAYER = "2 Player";
    public static String HAS_WON_MESSAGE = " Has Won!!";
}
