package modules;

import com.google.inject.AbstractModule;
import controllers.GamePlayCtrl;
import controllers.GamePlayCtrlImpl;
import factories.GameViewFactory;
import factories.GameViewFactoryImpl;

public class MenuViewModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GamePlayCtrlModule());
        install(new PlayerCtrlModule());
        bind(GamePlayCtrl.class).to(GamePlayCtrlImpl.class);
        bind(GameViewFactory.class).to(GameViewFactoryImpl.class);
    }
}
