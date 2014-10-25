package views.elements;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import models.Player;

public interface Header {
    void setMessage(String message);

    void clearMessage();

    void setReplay(EventHandler<MouseEvent> eventHandler);

    void setReset(EventHandler<MouseEvent> eventHandler);

    void setButtonsVisibility(boolean visible);

    void displayWinner(Player player);
}
