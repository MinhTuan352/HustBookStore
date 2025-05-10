import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Library extends Application {

    private boolean isCustomer;

    public Library(boolean isCustomer) {
        this.isCustomer = isCustomer;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.getStyleClass().add("root");

        Label libraryLabel = new Label(isCustomer ? "Customer Library" : "Guest Library");
        libraryLabel.getStyleClass().add("header-label");

        ListView<Book> bookListView = new ListView<>();
        bookListView.getItems().addAll(
            new Book("Book 1", 50000, "Fiction"),
            new Book("Book 2", 75000, "Non-Fiction"),
            new Book("Book 3", 30000, "Sci-Fi")
        );
        bookListView.getStyleClass().add("list-view");

        Button viewDetailsButton = new Button("View Details");
        viewDetailsButton.getStyleClass().add("button");
        viewDetailsButton.setOnAction(e -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                new BookDetails(selectedBook, isCustomer).start(new Stage());
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText(null);
                alert.setContentText("Please select a book to view details.");
                alert.showAndWait();
            }
        });

        root.getChildren().addAll(libraryLabel, bookListView, viewDetailsButton);

        Scene scene = new Scene(root, 400, 300);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Error: Could not find /style.css in resources. Using default styling.");
        }
        primaryStage.setTitle(isCustomer ? "Customer Library" : "Guest Library");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}