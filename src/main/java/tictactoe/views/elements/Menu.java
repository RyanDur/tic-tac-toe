package tictactoe.views.elements;

import java.util.function.BiConsumer;

public interface Menu {

    void setUpMenu(BiConsumer<Integer, Character> computer);
}
