import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.ArrayList;

public class CustomerOrders extends Application {
    private ArrayList<Book> cart;

    public CustomerOrders(ArrayList<Book> cart) {
        this.cart = cart;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.getStyleClass().add("root");

        Label ordersLabel = new Label("Customer Orders");
        ordersLabel.getStyleClass().add("header-label");

        ListView<Book> cartListView = new ListView<>();
        cartListView.getItems().addAll(cart);
        cartListView.getStyleClass().add("list-view");

        double total = cart.stream().mapToDouble(Book::getPrice).sum();
        Label totalLabel = new Label("Total: " + total + " VND");
        totalLabel.setStyle("-fx-font-size: 14px;");

        Button placeOrderButton = new Button("Place Order");
        placeOrderButton.getStyleClass().add("button");
        placeOrderButton.setOnAction(e -> {
            if (cart.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Empty Cart");
                alert.setHeaderText(null);
                alert.setContentText("Your cart is empty.");
                alert.showAndWait();
            } else {
                new Checkout(total).start(new Stage());
                primaryStage.close();
            }
        });

        root.getChildren().addAll(ordersLabel, cartListView, totalLabel, placeOrderButton);

        Scene scene = new Scene(root, 400, 300);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Error: Could not find /style.css in resources. Using default styling.");
        }
        primaryStage.setTitle("Customer Orders");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}