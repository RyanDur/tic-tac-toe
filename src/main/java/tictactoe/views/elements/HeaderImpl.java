package tictactoe.views.elements;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import tictactoe.lang.Constants;

import java.io.IOException;

public class HeaderImpl extends Parent implements Header {

    private final Label messageArea;
    private final Button replay;
    private final Button reset;

    public HeaderImpl() {
        HBox header = getFXML();
        messageArea = (Label) header.lookup(Constants.MESSAGES_ID);
        replay = (Button) header.lookup(Constants.REPLAY_ID);
        reset = (Button) header.lookup(Constants.RESET_ID);
        this.getChildren().add(header);
    }

    @Override
    public void setMessage(String message) {
        Platform.runLater(() -> messageArea.setText(message));
    }

    @Override
    public void clearMessage() {
        setMessage(Constants.EMPTY);
    }

    @Override
    public void setReplay(EventHandler<MouseEvent> eventHandler) {
        replay.setOnMouseClicked(eventHandler);
    }

    @Override
    public void setReset(EventHandler<MouseEvent> eventHandler) {
        reset.setOnMouseClicked(eventHandler);
    }

    @Override
    public void setButtonsVisibility(boolean visible) {
        Platform.runLater(() -> {
            replay.setVisible(visible);
            reset.setVisible(visible);
        });
    }

    @Override
    public void displayWinner(Character player) {
        if (player == null) setMessage(Constants.DRAW_MESSAGE);
        else setMessage(player + Constants.HAS_WON_MESSAGE);
    }

    private HBox getFXML() {
        try {
            return FXMLLoader.load(getClass().getResource(Constants.HEADER_VIEW));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
