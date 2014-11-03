package views.elements;

import javafx.scene.input.MouseEvent;

import java.util.function.Function;

public interface Board {


    void setPlay(Function<MouseEvent, Character[]> play);

    void setBoard(Character[] board);
}
