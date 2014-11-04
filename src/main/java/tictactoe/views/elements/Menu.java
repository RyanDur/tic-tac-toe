package tictactoe.views.elements;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.function.Consumer;

public interface Menu {

    void setTwoPlayer(EventHandler<MouseEvent> twoPlayer);

    void setOnePlayer(Consumer<Character> computer);

    void reset();
}
