import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modules.MenuViewModule;
import views.MenuView;

public class Start extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Injector injector = Guice.createInjector(new MenuViewModule());
            MenuView menuView = injector.getInstance(MenuView.class);
            stage.setScene(new Scene(menuView, 570, 650));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
