import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class OrderSummary extends Application {
    private static class Order {
        private String bookTitle;
        private int quantity;

        public Order(String bookTitle, int quantity) {
            this.bookTitle = bookTitle;
            this.quantity = quantity;
        }

        @Override
        public String toString() {
            return bookTitle + ": " + quantity + " orders";
        }
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.getStyleClass().add("root");

        Label summaryLabel = new Label("Order Summary");
        summaryLabel.getStyleClass().add("header-label");

        ListView<Order> orderListView = new ListView<>();
        orderListView.getItems().addAll(
            new Order("Book 1", 5),
            new Order("Book 2", 3),
            new Order("Book 3", 7)
        );
        orderListView.getStyleClass().add("list-view");

        root.getChildren().addAll(summaryLabel, orderListView);

        Scene scene = new Scene(root, 400, 300);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Error: Could not find /style.css in resources. Using default styling.");
        }
        primaryStage.setTitle("Order Summary");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}