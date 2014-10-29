package modules;

import com.google.inject.AbstractModule;
import factories.BoardFactory;
import factories.BoardFactoryImpl;

public class GameCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BoardFactory.class).to(BoardFactoryImpl.class);
    }
}
