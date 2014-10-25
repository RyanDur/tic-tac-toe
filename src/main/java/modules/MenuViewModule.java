package modules;

import com.google.inject.AbstractModule;
import controllers.GamePlayCtrl;
import controllers.GamePlayCtrlImpl;
import factories.ViewFactory;
import factories.ViewFactoryImpl;
import views.HeaderView;
import views.HeaderViewImpl;

public class MenuViewModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GamePlayCtrlModule());
        bind(GamePlayCtrl.class).to(GamePlayCtrlImpl.class);
        bind(ViewFactory.class).to(ViewFactoryImpl.class);
        bind(HeaderView.class).to(HeaderViewImpl.class);
    }
}
