package views;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.function.BiConsumer;

public interface NavigationView {
    void setTwoPlayer(EventHandler<MouseEvent> twoPlayer);

    void setOnePlayer(BiConsumer<String, String> onePlayer);
}
