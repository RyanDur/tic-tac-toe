package views;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import lang.constants;

import java.io.IOException;

public class HeaderViewImpl extends Parent implements HeaderView {

    private final Label messageArea;
    private final Button replay;
    private final Button reset;

    public HeaderViewImpl() throws IOException {
        HBox header = FXMLLoader.load(getClass().getResource(constants.HEADER_VIEW));
        messageArea = (Label) header.lookup(constants.MESSAGES_ID);
        replay = (Button) header.lookup(constants.REPLAY_ID);
        reset = (Button) header.lookup(constants.RESET_ID);
        this.getChildren().add(header);
    }

    @Override
    public void setMessage(String message) {
        Platform.runLater(() -> messageArea.setText(message));
    }

    @Override
    public void clearMessage() {
        setMessage(constants.EMPTY);
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
}
