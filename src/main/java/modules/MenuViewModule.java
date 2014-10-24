package modules;

import com.google.inject.AbstractModule;
import factories.GameViewFactory;
import factories.GameViewFactoryImpl;

public class MenuViewModule extends AbstractModule {
    @Override
    protected void configure() {

        install(new PlayerCtrlModule());

        bind(GameViewFactory.class).to(GameViewFactoryImpl.class);
    }
}
