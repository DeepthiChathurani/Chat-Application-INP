package lk.ijse.livechat.util;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Navigation {
     private static AnchorPane ancLogin;
    public static void navigation(Routes route, AnchorPane pane) throws IOException {
        Navigation.ancLogin = pane;
        Navigation.ancLogin.getChildren().clear();
        Stage window = (Stage) Navigation.ancLogin.getScene().getWindow();

        switch (route) {

            case LOGIN:
                window.setTitle("Login Form");
                initUI("Login.fxml");
                break;
            case CLIENT1:
                window.setTitle("Client 1");
                initUI("Client1.fxml");
                break;
            case CLIENT2:
                window.setTitle("Client 2");
                initUI("Client2.fxml");
                break;
            case CLIENT3:
                window.setTitle("Client 3");
                initUI("Client3.fxml");
                break;
            default:
                new Alert(Alert.AlertType.ERROR, "Not suitable UI found!").show();
        }
    }
    private static void initUI(String location) throws IOException {
        Navigation.ancLogin.getChildren().add(FXMLLoader.load(Navigation.class
                .getResource("/lk/ijse/livechat/view" + location)));
    }
}
