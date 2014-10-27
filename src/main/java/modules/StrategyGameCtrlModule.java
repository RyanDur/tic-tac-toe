package modules;

import com.google.inject.AbstractModule;
import factories.BoardFactory;
import factories.BoardFactoryImpl;
import factories.GameTreeFactory;
import factories.GameTreeFactoryImpl;

public class StrategyGameCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GameTreeFactory.class).to(GameTreeFactoryImpl.class);
        bind(BoardFactory.class).to(BoardFactoryImpl.class);
    }
}
