package modules;

import com.google.inject.AbstractModule;
import controllers.GameCtrl;
import controllers.GameCtrlImpl;
import controllers.PlayerCtrl;
import controllers.PlayerCtrlImpl;

public class GamePlayCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GameCtrlModule());
        install(new PlayerCtrlModule());
        bind(GameCtrl.class).to(GameCtrlImpl.class);
        bind(PlayerCtrl.class).to(PlayerCtrlImpl.class);
    }
}
