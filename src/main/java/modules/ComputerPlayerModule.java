package modules;

import com.google.inject.AbstractModule;
import tictactoe.ComputerAI;
import tictactoe.ComputerAIImpl;

public class ComputerPlayerModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ComputerAI.class).to(ComputerAIImpl.class);
    }
}
