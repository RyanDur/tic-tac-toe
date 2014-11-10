package tictactoe.views.elements;

import javafx.scene.input.MouseEvent;

import java.util.function.Function;

public interface Board {


    void setup(Function<MouseEvent, Character[]> play, int side);

    void setBoard(Character[] board);
}
