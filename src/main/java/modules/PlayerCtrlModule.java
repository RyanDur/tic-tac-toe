package modules;

import com.google.inject.AbstractModule;
import controllers.StrategyCtrl;
import controllers.StrategyCtrlImpl;
import factories.PlayerFactory;
import factories.PlayerFactoryImpl;

public class PlayerCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyCtrlModule());
        bind(PlayerFactory.class).to(PlayerFactoryImpl.class);
        bind(StrategyCtrl.class).to(StrategyCtrlImpl.class);
    }
}
