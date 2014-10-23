package modules;

import com.google.inject.AbstractModule;
import controllers.GameCtrl;
import controllers.GameCtrlImpl;
import factories.GameViewFactory;
import factories.GameViewFactoryImpl;
import factories.PlayerFactory;
import factories.PlayerFactoryImpl;

public class MenuViewModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new PlayerFactoryModule());
        install(new GameCtrlModule());
        bind(GameCtrl.class).to(GameCtrlImpl.class);
        bind(PlayerFactory.class).to(PlayerFactoryImpl.class);
        bind(GameViewFactory.class).to(GameViewFactoryImpl.class);
    }
}
