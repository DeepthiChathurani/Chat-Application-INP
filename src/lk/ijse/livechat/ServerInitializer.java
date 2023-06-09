package lk.ijse.livechat;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ServerInitializer extends Application {
    public static void main(String[] args) {
              launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene
                (new Scene(FXMLLoader.
                        load(getClass().
                                getResource("/lk/ijse/livechat/view/Server.fxml"))));
        primaryStage.setTitle("Server");
        primaryStage.show();
    }
}
