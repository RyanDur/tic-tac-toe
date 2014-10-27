package modules;

import com.google.inject.AbstractModule;
import controllers.StrategyGameCtrl;
import controllers.StrategyGameCtrlImpl;

public class StrategyCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyGameCtrlModule());
        bind(StrategyGameCtrl.class).to(StrategyGameCtrlImpl.class);
    }
}
