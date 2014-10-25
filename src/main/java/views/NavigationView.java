package views;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.util.function.BiConsumer;

public interface NavigationView {
    void setTwoPlayer(EventHandler<MouseEvent> event);

    void setOnePlayer(BiConsumer<String, String> event);
}
