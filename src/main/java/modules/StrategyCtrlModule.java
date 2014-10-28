package modules;

import com.google.inject.AbstractModule;
import controllers.StrategyBoardCtrl;
import controllers.StrategyBoardCtrlImpl;

public class StrategyCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyGameCtrlModule());
        bind(StrategyBoardCtrl.class).to(StrategyBoardCtrlImpl.class);
    }
}
