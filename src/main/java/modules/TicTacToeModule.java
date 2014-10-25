package modules;

import com.google.inject.AbstractModule;
import controllers.GamePlayCtrl;
import controllers.GamePlayCtrlImpl;
import factories.ViewFactory;
import factories.ViewFactoryImpl;
import views.elements.HeaderView;
import views.elements.HeaderViewImpl;

public class TicTacToeModule extends AbstractModule {
    @Override
    protected void configure() {
        install(new GamePlayCtrlModule());
        bind(GamePlayCtrl.class).to(GamePlayCtrlImpl.class);
        bind(ViewFactory.class).to(ViewFactoryImpl.class);
        bind(HeaderView.class).to(HeaderViewImpl.class);
    }
}
