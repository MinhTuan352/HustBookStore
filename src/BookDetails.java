import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Modality;

public class BookDetails extends Application {
    private Book book;
    private boolean isCustomer;

    public BookDetails() {
        this.book = new Book("Sample Book", 100000, "Fiction");
        this.isCustomer = false;
    }

    public BookDetails(Book book) {
        this.book = book;
        this.isCustomer = false;
    }

    public BookDetails(Book book, boolean isCustomer) {
        this.book = book;
        this.isCustomer = isCustomer;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.getStyleClass().add("root");

        Label bookTitleLabel = new Label("Book Title: " + book.getTitle());
        bookTitleLabel.getStyleClass().add("header-label");

        Label bookPriceLabel = new Label("Price: " + book.getPrice() + " VND");
        bookPriceLabel.setStyle("-fx-font-size: 14px;");

        Label bookCategoryLabel = new Label("Category: " + book.getCategory());
        bookCategoryLabel.setStyle("-fx-font-size: 14px;");

        root.getChildren().addAll(bookTitleLabel, bookPriceLabel, bookCategoryLabel);

        if (isCustomer) {
            Button addToCartButton = new Button("Add to Cart");
            addToCartButton.getStyleClass().add("button");
            addToCartButton.setOnAction(e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText(book.getTitle() + " has been added to your cart!");
                alert.showAndWait();
            });
            root.getChildren().add(addToCartButton);
        }

        Scene scene = new Scene(root, 400, 200);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Error: Could not find /style.css in resources. Using default styling.");
        }
        primaryStage.setTitle("Book Details");
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setScene(scene);
        primaryStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}