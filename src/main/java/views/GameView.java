package views;

import javafx.scene.input.MouseEvent;
import models.Player;

import java.util.function.Function;

public interface GameView {
    void setup(Player[] board);

    void setPlay(Function<MouseEvent, Player[]> play);
}
