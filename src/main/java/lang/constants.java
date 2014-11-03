package lang;

public interface constants {
    Character GAME_PIECE_ONE = 'X';
    Character GAME_PIECE_TWO = 'O';
    int SIDE = 3;
    String GAME_VIEW = "/scenes/game.fxml";
    String MESSAGES_ID = "#messages";
    String DRAW_MESSAGE = "The Game is a Draw!";
    String NOT_VACANT_MESSAGE = "Space is occupied,\n please choose another.";
    String OUT_OF_BOUNDS_MESSAGE = "You chose a space out of bounds,\n HOW!!??";
    String EMPTY = "";
    String OUT_OF_TURN_MESSAGE = "It's not your turn!";
    int WIN_WEIGHT = 1;
    int LOSE_WEIGHT = -1;
    int DRAW_WEIGHT = 0;
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
}
