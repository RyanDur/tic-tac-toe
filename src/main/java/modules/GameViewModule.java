package modules;

import com.google.inject.AbstractModule;
import controllers.GameCtrl;
import controllers.GameCtrlImpl;
import factories.PlayerFactory;
import factories.PlayerFactoryImpl;

public class GameViewModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GameCtrlModule());
        install(new PlayerFactoryModule());
        bind(GameCtrl.class).to(GameCtrlImpl.class);
        bind(PlayerFactory.class).to(PlayerFactoryImpl.class);
    }
}
