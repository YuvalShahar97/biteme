package client;

import controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;

public class ClientUI extends Application {
    public static ClientController chat;

    @Override
    public void start(Stage primaryStage) {
        try {
            String host = getParameters().getRaw().isEmpty() ? "localhost" : getParameters().getRaw().get(0);

            // Initialize the ClientController with the given IP address
            chat = new ClientController(host, 5555);  // Adjust port as needed
            
            // Create and start the LoginController
            LoginController loginController = new LoginController();
            loginController.start(primaryStage);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        launch(args);  // Pass the args to the JavaFX application
    }
}
