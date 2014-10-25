package modules;

import com.google.inject.AbstractModule;
import controllers.GamePlayCtrl;
import controllers.GamePlayCtrlImpl;
import factories.ViewFactory;
import factories.ViewFactoryImpl;
import views.elements.Header;
import views.elements.HeaderImpl;

public class TicTacToeModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GamePlayCtrlModule());
        bind(GamePlayCtrl.class).to(GamePlayCtrlImpl.class);
        bind(ViewFactory.class).to(ViewFactoryImpl.class);
        bind(Header.class).to(HeaderImpl.class);
    }
}
