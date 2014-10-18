import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import modules.GameViewModule;
import views.GameView;

public class Start extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Injector injector = Guice.createInjector(new GameViewModule());
        GameView gameView = injector.getInstance(GameView.class);
        stage.setScene(new Scene(gameView, 600, 600));
        stage.show();
    }
}
