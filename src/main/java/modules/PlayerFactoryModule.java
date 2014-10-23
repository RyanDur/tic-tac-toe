package modules;

import com.google.inject.AbstractModule;
import controllers.StrategyGameCtrl;
import controllers.StrategyGameCtrlImpl;

public class PlayerFactoryModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(StrategyGameCtrl.class).to(StrategyGameCtrlImpl.class);
    }
}
