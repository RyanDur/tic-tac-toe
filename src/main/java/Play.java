import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tictactoe.modules.TicTacToeModule;
import tictactoe.views.game.TicTacToe;
import tictactoe.views.game.TicTacToeImpl;

public class Play extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Injector injector = Guice.createInjector(new TicTacToeModule());
            TicTacToe ticTacToe = injector.getInstance(TicTacToeImpl.class);
            stage.setScene(new Scene((Parent) ticTacToe, 570, 650));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
