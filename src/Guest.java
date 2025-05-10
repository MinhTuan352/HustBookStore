import javafx.application.Application;
import javafx.stage.Stage;

public class Guest extends Application {

    @Override
    public void start(Stage primaryStage) {
        new HustBookStoreApp().start(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}