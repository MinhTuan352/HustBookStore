import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.stage.Modality;

public class HustBookStoreApp extends Application {

    private ArrayList<Book> cart = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();
    private User currentUser = null;
    private Scene mainScene; // Lưu tham chiếu đến Scene chính

    private static class Book {
        private String name;
        private int price;
        private String category;
        private String image;

        public Book(int id, String name, int price, String category, String image) {
            this.name = name;
            this.price = price;
            this.category = category;
            this.image = image;
        }

        public String getName() { return name; }
        public double getPrice() { return price; }
        public String getCategory() { return category; }
        public String getImage() { return image; }
    }

    private static class User {
        String email, password;
        boolean verified;

        User(String email, String password) {
            this.email = email;
            this.password = password;
            this.verified = true;
        }
    }

    private ArrayList<Book> books = new ArrayList<>(Arrays.asList(
        new Book(1, "The Lord Of The Ring: The Two Towers", 567000, "Tiểu thuyết", "https://salt.tikicdn.com/cache/750x750/ts/product/a8/fe/98/acc45d6ac4b3ebea246fb8ae51d9b6ef.jpg.webp"),
        new Book(2, "Khởi Nghiệp 4.0 - Kinh Doanh Thông Minh Trong Cách Mạng Công Nghiệp 4.0", 139000, "Kinh tế", "https://salt.tikicdn.com/cache/750x750/ts/product/c6/94/39/7e9ab3cd4a2fe69c8cd813ee0c45434d.jpg.webp"),
        new Book(3, "Harry Potter Và Hòn Đá Phù Thủy: Tập 01", 135000, "Tiểu thuyết", "https://salt.tikicdn.com/cache/750x750/ts/product/d4/56/6c/35c19da940c8c805337e57f48ab1b18c.jpg.webp"),
        new Book(4, "Thám Tử Lừng Danh Conan Tập 80", 24000, "Truyện tranh", "https://salt.tikicdn.com/cache/w300/ts/product/6b/a5/86/8f231a6f31e6630e728ad2afd50bbbd2.jpg.webp"),
        new Book(5, "Sách Giáo Khoa Toán 12 Tập 2 - Chân Trời Sáng Tạo", 25000, "Sách giáo khoa", "https://salt.tikicdn.com/cache/750x750/ts/product/ee/0f/b8/3ced621501e5d338fc24eaf69333280a.png.webp")
    ));

    private ArrayList<Book> filteredBooks = new ArrayList<>(books);

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("root");

        HBox navbar = createNavbar(primaryStage);
        root.setTop(navbar);

        VBox heroSection = createHeroSection();
        VBox productSection = createProductSection();
        ScrollPane scrollPane = new ScrollPane(new VBox(heroSection, productSection));
        scrollPane.setFitToWidth(true);
        root.setCenter(scrollPane);

        HBox footer = createFooter();
        root.setBottom(footer);

        mainScene = new Scene(root, 1200, 800); // Lưu Scene vào biến instance
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            mainScene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Error: Could not find /style.css in resources. Using default styling.");
        }

        primaryStage.setTitle("Hust Book Store");
        primaryStage.setMaximized(true);
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    private HBox createNavbar(Stage primaryStage) {
        HBox navbar = new HBox(20);
        navbar.getStyleClass().add("navbar");

        Label logo = new Label("HustBookStore");
        logo.getStyleClass().add("logo");

        TextField searchBar = new TextField();
        searchBar.setPromptText("Tìm kiếm sách...");
        Button searchButton = new Button("Tìm");
        searchButton.setOnAction(e -> {
            String searchTerm = searchBar.getText().toLowerCase();
            filteredBooks = new ArrayList<>();
            for (Book book : books) {
                if (book.getName().toLowerCase().contains(searchTerm)) {
                    filteredBooks.add(book);
                }
            }
            updateProductGrid((GridPane) mainScene.lookup("#productGrid"));
        });
        HBox searchBox = new HBox(searchBar, searchButton);
        searchBox.getStyleClass().add("search-bar");

        Hyperlink cartLink = new Hyperlink("Giỏ Hàng (0)");
        cartLink.setId("cartLink");
        cartLink.setOnAction(e -> showCartDialog(primaryStage));
        Button authButton = new Button(currentUser == null ? "Đăng Nhập/Đăng Ký" : "Đăng Xuất");
        authButton.setId("authButton");
        authButton.setOnAction(e -> {
            if (currentUser == null) {
                showAuthModal(primaryStage);
            } else {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Bạn có chắc muốn đăng xuất?");
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        currentUser = null;
                        cart.clear();
                        authButton.setText("Đăng Nhập/Đăng Ký");
                        cartLink.setText("Giỏ Hàng (0)");
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đăng xuất thành công!");
                        alert.showAndWait();
                    }
                });
            }
        });
        HBox cartLoginBox = new HBox(20, cartLink, authButton);
        cartLoginBox.getStyleClass().add("cart-login");

        navbar.getChildren().addAll(logo, searchBox, cartLoginBox);
        return navbar;
    }

    private VBox createHeroSection() {
        VBox heroSection = new VBox(20);
        heroSection.getStyleClass().add("hero-section");

        Label title = new Label("Khám Phá Thế Giới Sách");
        title.getStyleClass().add("hero-title");

        Label subtitle = new Label("Tìm và mua những cuốn sách yêu thích của bạn ngay hôm nay!");
        subtitle.getStyleClass().add("hero-subtitle");

        Button ctaButton = new Button("Mua Ngay");
        ctaButton.getStyleClass().add("cta-button");
        ctaButton.setOnAction(e -> {
            // Cuộn xuống phần sản phẩm
            // JavaFX không có scrollTo trực tiếp, nên chỉ làm nổi bật phần sản phẩm
        });

        heroSection.getChildren().addAll(title, subtitle, ctaButton);
        return heroSection;
    }

    private VBox createProductSection() {
        VBox productSection = new VBox(20);
        productSection.getStyleClass().add("products-section");

        Label productsTitle = new Label("Sách Nổi Bật");
        productsTitle.getStyleClass().add("products-title");

        ComboBox<String> categoryFilter = new ComboBox<>();
        categoryFilter.getItems().addAll("Tất cả", "Tiểu thuyết", "Kinh tế", "Truyện tranh", "Sách giáo khoa");
        categoryFilter.setValue("Tất cả");
        categoryFilter.setOnAction(e -> {
            String category = categoryFilter.getValue();
            filteredBooks = new ArrayList<>();
            for (Book book : books) {
                if (category.equals("Tất cả") || book.getCategory().equals(category)) {
                    filteredBooks.add(book);
                }
            }
            updateProductGrid((GridPane) mainScene.lookup("#productGrid"));
        });
        HBox filterSection = new HBox(categoryFilter);
        filterSection.getStyleClass().add("filter-section");

        GridPane productGrid = new GridPane();
        productGrid.setId("productGrid");
        productGrid.getStyleClass().add("product-grid");

        // Chỉ thêm productGrid vào productSection, không gọi updateProductGrid() ở đây
        productSection.getChildren().addAll(productsTitle, filterSection, productGrid);

        // Gọi updateProductGrid sau khi Scene đã được hiển thị
        productSection.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                updateProductGrid(productGrid);
            }
        });

        return productSection;
    }

    private void updateProductGrid(GridPane productGrid) {
        if (productGrid == null) return;
        productGrid.getChildren().clear();

        int column = 0, row = 0;
        for (Book book : filteredBooks) {
            VBox card = new VBox(10);
            card.getStyleClass().add("product-card");

            ImageView imageView = new ImageView(new Image(book.getImage()));
            imageView.getStyleClass().add("product-image");

            Label nameLabel = new Label(book.getName());
            nameLabel.getStyleClass().add("product-name");

            Label priceLabel = new Label(book.getPrice() + " VNĐ");
            priceLabel.getStyleClass().add("product-price");

            Button addButton = new Button("Thêm vào giỏ");
            addButton.setOnAction(e -> {
                cart.add(book);
                Hyperlink cartLink = (Hyperlink) mainScene.lookup("#cartLink");
                if (cartLink != null) cartLink.setText("Giỏ Hàng (" + cart.size() + ")");
            });

            VBox productInfo = new VBox(10, nameLabel, priceLabel, addButton);
            productInfo.getStyleClass().add("product-info");

            card.getChildren().addAll(imageView, productInfo);
            productGrid.add(card, column, row);

            column++;
            if (column == 5) {
                column = 0;
                row++;
            }
        }
    }

    private void showCartDialog(Stage owner) {
        Stage cartStage = new Stage();
        cartStage.initModality(Modality.APPLICATION_MODAL);
        cartStage.initOwner(owner);

        VBox cartSection = new VBox(10);
        cartSection.getStyleClass().add("cart-section");

        Label cartTitle = new Label("Giỏ Hàng");
        cartTitle.getStyleClass().add("cart-title");

        ListView<HBox> cartItems = new ListView<>();
        for (Book item : cart) {
            HBox itemBox = new HBox(10);
            itemBox.getStyleClass().add("cart-item");

            Label itemLabel = new Label(item.getName() + " - " + item.getPrice() + " VNĐ");
            Button removeButton = new Button("Xóa");
            removeButton.setOnAction(e -> {
                cart.remove(item);
                showCartDialog(owner);
                Hyperlink cartLink = (Hyperlink) mainScene.lookup("#cartLink");
                if (cartLink != null) cartLink.setText("Giỏ Hàng (" + cart.size() + ")");
            });

            itemBox.getChildren().addAll(itemLabel, removeButton);
            cartItems.getItems().add(itemBox);
        }

        double total = cart.stream().mapToDouble(Book::getPrice).sum();
        Label cartTotal = new Label("Tổng: " + total + " VNĐ");
        cartTotal.getStyleClass().add("cart-total");

        Button placeOrderButton = new Button("Đặt Hàng");
        placeOrderButton.setOnAction(e -> {
            if (cart.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Giỏ hàng trống!");
                alert.showAndWait();
            } else if (currentUser == null) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Vui lòng đăng nhập để đặt hàng!");
                alert.showAndWait();
                showAuthModal(owner);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đặt hàng thành công! Tổng tiền: " + total + " VNĐ. Vui lòng kiểm tra email để hoàn tất.");
                alert.showAndWait();
                cart.clear();
                Hyperlink cartLink = (Hyperlink) mainScene.lookup("#cartLink");
                if (cartLink != null) cartLink.setText("Giỏ Hàng (0)");
                cartStage.close();
            }
        });

        cartSection.getChildren().addAll(cartTitle, cartItems, cartTotal, placeOrderButton);

        Scene cartScene = new Scene(cartSection, 300, 400);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            cartScene.getStylesheets().add(cssUrl.toExternalForm());
        }
        cartStage.setTitle("Giỏ Hàng");
        cartStage.setScene(cartScene);
        cartStage.show();
    }

    private void showAuthModal(Stage owner) {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.initOwner(owner);

        VBox modalContent = new VBox(10);
        modalContent.getStyleClass().add("modal-content");

        Label loginTitle = new Label("Đăng Nhập");
        TextField loginEmail = new TextField();
        loginEmail.setPromptText("Email");
        PasswordField loginPassword = new PasswordField();
        loginPassword.setPromptText("Mật khẩu");
        Button loginButton = new Button("Đăng Nhập");
        Hyperlink toRegister = new Hyperlink("Chưa có tài khoản? Đăng ký");
        VBox loginForm = new VBox(10, loginTitle, loginEmail, loginPassword, loginButton, toRegister);
        loginForm.setId("loginForm");

        Hyperlink forgotPassword = new Hyperlink("Quên mật khẩu?");
        forgotPassword.setOnAction(e -> {
            Stage forgotPasswordStage = new Stage();
            forgotPasswordStage.initModality(Modality.APPLICATION_MODAL);
            forgotPasswordStage.initOwner(owner);

            VBox forgotPasswordContent = new VBox(10);
            forgotPasswordContent.getStyleClass().add("modal-content");

            Label forgotPasswordTitle = new Label("Quên Mật Khẩu");
            TextField emailField = new TextField();
            emailField.setPromptText("Email");
            PasswordField newPasswordField = new PasswordField();
            newPasswordField.setPromptText("Mật khẩu mới");
            Button resetPasswordButton = new Button("Đặt lại mật khẩu");

            resetPasswordButton.setOnAction(event -> {
                String email = emailField.getText();
                String newPassword = newPasswordField.getText();
                User user = users.stream().filter(u -> u.email.equals(email)).findFirst().orElse(null);
                if (user != null) {
                    user.password = newPassword;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đặt lại mật khẩu thành công!");
                    alert.showAndWait();
                    forgotPasswordStage.close();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Email không tồn tại!");
                    alert.showAndWait();
                }
            });

            forgotPasswordContent.getChildren().addAll(forgotPasswordTitle, emailField, newPasswordField, resetPasswordButton);

            Scene forgotPasswordScene = new Scene(forgotPasswordContent, 300, 200);
            java.net.URL cssUrl = getClass().getResource("/style.css");
            if (cssUrl != null) {
                forgotPasswordScene.getStylesheets().add(cssUrl.toExternalForm());
            }
            forgotPasswordStage.setTitle("Quên Mật Khẩu");
            forgotPasswordStage.setScene(forgotPasswordScene);
            forgotPasswordStage.show();
        });

        loginForm.getChildren().add(forgotPassword);

        Label regTitle = new Label("Đăng Ký");
        TextField regHoTen = new TextField();
        regHoTen.setPromptText("Họ tên");
        TextField regSoDienThoai = new TextField();
        regSoDienThoai.setPromptText("Số điện thoại");
        TextField regEmail = new TextField();
        regEmail.setPromptText("Email");
        PasswordField regPassword = new PasswordField();
        regPassword.setPromptText("Mật khẩu");
        RadioButton male = new RadioButton("Nam");
        RadioButton female = new RadioButton("Nữ");
        ToggleGroup genderGroup = new ToggleGroup();
        male.setToggleGroup(genderGroup);
        female.setToggleGroup(genderGroup);
        HBox genderBox = new HBox(20, male, female);
        DatePicker regNgaySinh = new DatePicker();
        regNgaySinh.setPromptText("Ngày sinh");
        Button registerButton = new Button("Đăng Ký");
        Hyperlink toLogin = new Hyperlink("Đã có tài khoản? Đăng nhập");
        VBox registerForm = new VBox(10, regTitle, regHoTen, regSoDienThoai, regEmail, regPassword, genderBox, regNgaySinh, registerButton, toLogin);
        registerForm.setId("registerForm");
        registerForm.setVisible(false);

        loginButton.setOnAction(e -> {
            String email = loginEmail.getText();
            String password = loginPassword.getText();
            User user = users.stream().filter(u -> u.email.equals(email) && u.password.equals(password) && u.verified).findFirst().orElse(null);
            if (user != null) {
                currentUser = user;
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đăng nhập thành công với " + email + "!");
                alert.showAndWait();
                modalStage.close();
                Button authButton = (Button) mainScene.lookup("#authButton");
                if (authButton != null) authButton.setText("Đăng Xuất");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Email hoặc mật khẩu không đúng!");
                alert.showAndWait();
            }
        });

        registerButton.setOnAction(e -> {
            String hoTen = regHoTen.getText();
            String soDienThoai = regSoDienThoai.getText();
            String email = regEmail.getText();
            String password = regPassword.getText();
            String gender = male.isSelected() ? "Nam" : female.isSelected() ? "Nữ" : null;
            String ngaySinh = regNgaySinh.getValue() != null ? regNgaySinh.getValue().toString() : null;

            if (hoTen.isEmpty() || soDienThoai.isEmpty() || email.isEmpty() || password.isEmpty() || gender == null || ngaySinh == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Vui lòng điền đầy đủ thông tin!");
                alert.showAndWait();
                return;
            }

            User user = new User(email, password);
            users.add(user);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Đăng ký thành công! Vui lòng đăng nhập.");
            alert.showAndWait();
            loginForm.setVisible(true);
            registerForm.setVisible(false);
        });

        toRegister.setOnAction(e -> {
            loginForm.setVisible(false);
            registerForm.setVisible(true);
        });

        toLogin.setOnAction(e -> {
            loginForm.setVisible(true);
            registerForm.setVisible(false);
        });

        modalContent.getChildren().addAll(loginForm, registerForm);

        Scene modalScene = new Scene(modalContent, 300, 400);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            modalScene.getStylesheets().add(cssUrl.toExternalForm());
        }
        modalStage.setTitle("Đăng Nhập/Đăng Ký");
        modalStage.setScene(modalScene);
        modalStage.show();
    }

    private HBox createFooter() {
        HBox footer = new HBox();
        footer.getStyleClass().add("footer");

        Label footerText = new Label("© 2024.2 OOP HustBookStore. Made by Trịnh Thành Trung (PhD).");
        footerText.getStyleClass().add("footer-text");

        footer.getChildren().add(footerText);
        return footer;
    }

    public static void main(String[] args) {
        launch(args);
    }
}