package tictactoe.lang;

public interface Constants {
    Character GAME_PIECE_ONE = 'X';
    Character GAME_PIECE_TWO = 'O';
    int SMALL_BOARD = 3;
    int LARGE_BOARD = 4;
    String GAME_VIEW = "/scenes/game.fxml";
    String MESSAGES_ID = "#messages";
    String DRAW_MESSAGE = "The Game is a Draw!";
    String NOT_VACANT_MESSAGE = "Space is occupied,\n please choose another.";
    String EMPTY = "";
    int WIN_SCORE = 1;
    int LOSE_SCORE = -1;
    int DRAW_SCORE = 0;
    String TIC_TAC_TOE_VIEW = "/scenes/tictactoe.fxml";
    String REPLAY_ID = "#replay";
    String RESET_ID = "#reset";
    String ONE_PLAYER = "1 Player";
    String TWO_PLAYER = "2 Player";
    String HAS_WON_MESSAGE = " Has Won!!";
    String HEADER_VIEW = "/scenes/header.fxml";
    String RIGHT_BUTTON_ID = "#right";
    String LEFT_BUTTON_ID = "#left";
    String MENU_VIEW = "/scenes/menu.fxml";
    int POS_INF = Integer.MAX_VALUE;
    int NEG_INF = Integer.MIN_VALUE + 1;
    String INVALID_MOVE = "Sorry,\nCan't go there.";
    String SMALL_BOARD_BUTTON = "3 X 3";
    String LARGE_BOARD_BUTTON = "4 X 4";
    int DEPTH = 5;
}
