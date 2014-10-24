package modules;

import com.google.inject.AbstractModule;
import controllers.StrategyGameCtrl;
import controllers.StrategyGameCtrlImpl;
import factories.PlayerFactory;
import factories.PlayerFactoryImpl;

public class PlayerCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyGameCtrlModule());
        bind(PlayerFactory.class).to(PlayerFactoryImpl.class);
        bind(StrategyGameCtrl.class).to(StrategyGameCtrlImpl.class);
    }
}
