package modules;

import com.google.inject.AbstractModule;
import controllers.StrategyBoardCtrl;
import controllers.StrategyBoardCtrlImpl;
import factories.PlayerFactory;
import factories.PlayerFactoryImpl;

public class PlayerCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyBoardCtrlModule());
        bind(PlayerFactory.class).to(PlayerFactoryImpl.class);
        bind(StrategyBoardCtrl.class).to(StrategyBoardCtrlImpl.class);
    }
}
