package modules;

import com.google.inject.AbstractModule;
import factories.GameFactory;
import factories.GameFactoryImpl;

public class GameCtrlModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(GameFactory.class).to(GameFactoryImpl.class);
    }
}
