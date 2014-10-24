package modules;

import com.google.inject.AbstractModule;
import factories.StrategyGameFactory;
import factories.StrategyGameFactoryImpl;

public class StrategyGameCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyGameFactoryModule());
        bind(StrategyGameFactory.class).to(StrategyGameFactoryImpl.class);
    }
}
