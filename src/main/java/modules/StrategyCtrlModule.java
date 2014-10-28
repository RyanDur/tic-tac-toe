package modules;

import com.google.inject.AbstractModule;
import models.StrategyGame;
import models.StrategyGameImpl;

public class StrategyCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyGameCtrlModule());
        bind(StrategyGame.class).to(StrategyGameImpl.class);
    }
}
