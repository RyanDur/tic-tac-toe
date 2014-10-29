package modules;

import com.google.inject.AbstractModule;
import models.StrategyGameCtrl;
import models.StrategyGameCtrlImpl;

public class ComputerCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyGameCtrlModule());
        bind(StrategyGameCtrl.class).to(StrategyGameCtrlImpl.class);
    }
}
