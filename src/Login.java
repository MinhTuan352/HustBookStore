import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Login extends Application {

    private static class User {
        String username;
        String password;
        String role;

        User(String username, String password, String role) {
            this.username = username;
            this.password = password;
            this.role = role;
        }
    }

    private static User[] users = {
        new User("customer", "cust123", "Customer"),
        new User("admin", "admin123", "Admin"),
        new User("guest", "", "Guest")
    };

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(10);
        root.getStyleClass().add("root");

        Label loginLabel = new Label("Login to HustBookStore");
        loginLabel.getStyleClass().add("header-label");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.getStyleClass().add("text-field");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.getStyleClass().add("text-field");

        Button loginButton = new Button("Login");
        loginButton.getStyleClass().add("button");
        loginButton.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();

            User user = null;
            for (User u : users) {
                if (u.username.equals(username) && u.password.equals(password)) {
                    user = u;
                    break;
                }
            }

            if (user == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Failed");
                alert.setHeaderText(null);
                alert.setContentText("Invalid username or password.");
                alert.showAndWait();
            } else {
                primaryStage.close();
                // Sử dụng thuộc tính role để điều hướng
                if ("Customer".equals(user.role)) {
                    new HustBookStoreApp().start(new Stage());
                } else if ("Admin".equals(user.role)) {
                    new HustBookStoreApp().start(new Stage());
                } else {
                    new HustBookStoreApp().start(new Stage());
                }
            }
        });

        Button registerButton = new Button("Register");
        registerButton.getStyleClass().add("button");
        registerButton.setOnAction(e -> {
            VBox regRoot = new VBox(10);
            regRoot.getStyleClass().add("root");

            Label regLabel = new Label("Register for HustBookStore");
            regLabel.getStyleClass().add("header-label");

            TextField regUsernameField = new TextField();
            regUsernameField.setPromptText("Username");
            regUsernameField.getStyleClass().add("text-field");

            PasswordField regPasswordField = new PasswordField();
            regPasswordField.setPromptText("Password");
            regPasswordField.getStyleClass().add("text-field");

            ChoiceBox<String> roleChoice = new ChoiceBox<>();
            roleChoice.getItems().addAll("Customer", "Admin");
            roleChoice.setValue("Customer");
            roleChoice.getStyleClass().add("text-field");

            Button confirmButton = new Button("Confirm Registration");
            confirmButton.getStyleClass().add("button");
            confirmButton.setOnAction(event -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Registration Success");
                alert.setHeaderText(null);
                alert.setContentText("Account created! Username: " + regUsernameField.getText() + ", Role: " + roleChoice.getValue());
                alert.showAndWait();
            });

            regRoot.getChildren().addAll(regLabel, regUsernameField, regPasswordField, roleChoice, confirmButton);

            Scene regScene = new Scene(regRoot, 400, 250);
            java.net.URL cssUrl = getClass().getResource("/style.css");
            if (cssUrl != null) {
                regScene.getStylesheets().add(cssUrl.toExternalForm());
            }
            Stage regStage = new Stage();
            regStage.setTitle("Register");
            regStage.setScene(regScene);
            regStage.show();
        });

        Button forgotPasswordButton = new Button("Forgot Password");
        forgotPasswordButton.getStyleClass().add("button");
        forgotPasswordButton.setOnAction(e -> {
            VBox forgotPasswordRoot = new VBox(10);
            forgotPasswordRoot.getStyleClass().add("root");

            Label forgotPasswordLabel = new Label("Reset Password");
            forgotPasswordLabel.getStyleClass().add("header-label");

            TextField usernameFieldFP = new TextField();
            usernameFieldFP.setPromptText("Username");
            usernameFieldFP.getStyleClass().add("text-field");

            PasswordField newPasswordField = new PasswordField();
            newPasswordField.setPromptText("New Password");
            newPasswordField.getStyleClass().add("text-field");

            Button resetButton = new Button("Reset Password");
            resetButton.getStyleClass().add("button");
            resetButton.setOnAction(event -> {
                String username = usernameFieldFP.getText();
                String newPassword = newPasswordField.getText();

                boolean userFound = false;
                for (User u : users) {
                    if (u.username.equals(username)) {
                        u.password = newPassword;
                        userFound = true;
                        break;
                    }
                }

                Alert alert = new Alert(userFound ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR);
                alert.setTitle(userFound ? "Password Reset Success" : "Password Reset Failed");
                alert.setHeaderText(null);
                alert.setContentText(userFound ? "Password has been reset successfully." : "Username not found.");
                alert.showAndWait();
            });

            forgotPasswordRoot.getChildren().addAll(forgotPasswordLabel, usernameFieldFP, newPasswordField, resetButton);

            Scene forgotPasswordScene = new Scene(forgotPasswordRoot, 400, 200);
            java.net.URL cssUrl = getClass().getResource("/style.css");
            if (cssUrl != null) {
                forgotPasswordScene.getStylesheets().add(cssUrl.toExternalForm());
            }
            Stage forgotPasswordStage = new Stage();
            forgotPasswordStage.setTitle("Forgot Password");
            forgotPasswordStage.setScene(forgotPasswordScene);
            forgotPasswordStage.show();
        });

        root.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton, registerButton, forgotPasswordButton);

        Scene scene = new Scene(root, 400, 200);
        java.net.URL cssUrl = getClass().getResource("/style.css");
        if (cssUrl != null) {
            scene.getStylesheets().add(cssUrl.toExternalForm());
        } else {
            System.err.println("Error: Could not find /style.css in resources. Using default styling.");
        }
        primaryStage.setTitle("Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}