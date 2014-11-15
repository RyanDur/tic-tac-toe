package tictactoe.views.elements;

import java.util.function.BiConsumer;

public interface Menu {

    void setup(BiConsumer<Integer, Character> computer);
}
