import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class AddBook extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.getStyleClass().add("root");

        Label addBookLabel = new Label("Add Book to Inventory");
        addBookLabel.getStyleClass().add("header-label");

        TextField bookTitleField = new TextField();
        bookTitleField.setPromptText("Book Title");
        bookTitleField.getStyleClass().add("text-field");

        TextField bookPriceField = new TextField();
        bookPriceField.setPromptText("Price");
        bookPriceField.getStyleClass().add("text-field");

        TextField bookCategoryField = new TextField();
        bookCategoryField.setPromptText("Category");
        bookCategoryField.getStyleClass().add("text-field");

        Button addBookButton = new Button("Add Book");
        addBookButton.getStyleClass().add("button");
        addBookButton.setOnAction(e -> {
            String title = bookTitleField.getText();
            String priceText = bookPriceField.getText();
            String category = bookCategoryField.getText();

            if (title.isEmpty() || priceText.isEmpty() || category.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("Book added: " + title + ", " + price + " VND, Category: " + category);
                alert.showAndWait();

                bookTitleField.clear();
                bookPriceField.clear();
                bookCategoryField.clear();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Price must be a number.");
                alert.showAndWait();
            }
        });

        root.getChildren().addAll(addBookLabel, bookTitleField, bookPriceField, bookCategoryField, addBookButton);

        Scene scene = new Scene(root, 400, 300);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Error: Could not find /style.css in resources. Using default styling.");
        }
        primaryStage.setTitle("Add Book");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}