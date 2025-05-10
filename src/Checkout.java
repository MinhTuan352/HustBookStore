import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Checkout extends Application {
    private double total = 0;

    public Checkout() {
        this.total = 0;
    }

    public Checkout(double total) {
        this.total = total;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.getStyleClass().add("root");

        Label checkoutLabel = new Label("Sales Statistics");
        checkoutLabel.getStyleClass().add("header-label");

        Label totalLabel = new Label("Total Sales: " + total + " VND");
        totalLabel.setStyle("-fx-font-size: 14px;");

        root.getChildren().addAll(checkoutLabel, totalLabel);

        Scene scene = new Scene(root, 400, 200);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Error: Could not find /style.css in resources. Using default styling.");
        }
        primaryStage.setTitle("Checkout");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}