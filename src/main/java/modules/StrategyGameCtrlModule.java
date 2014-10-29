package modules;

import com.google.inject.AbstractModule;
import models.StrategyBoardCtrl;
import models.StrategyBoardCtrlImpl;

public class StrategyGameCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new StrategyBoardCtrlModule());
        bind(StrategyBoardCtrl.class).to(StrategyBoardCtrlImpl.class);
    }
}
