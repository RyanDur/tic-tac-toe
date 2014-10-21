package modules;

import com.google.inject.AbstractModule;
import factories.BoardFactory;
import factories.BoardFactoryImpl;
import factories.GameFactory;
import factories.GameFactoryImpl;

public class GameCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GameFactory.class).to(GameFactoryImpl.class);
        bind(BoardFactory.class).to(BoardFactoryImpl.class);
    }
}
