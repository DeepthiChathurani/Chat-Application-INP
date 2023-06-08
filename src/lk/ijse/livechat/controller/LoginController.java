package lk.ijse.livechat.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.livechat.util.Navigation;
import lk.ijse.livechat.util.Routes;

import java.io.IOException;

public class LoginController {

    public TextField txtUserName;
    public Label lblMessage;
    public AnchorPane pane;

    public String userName;

    public void btnLogin(ActionEvent actionEvent) {
        userName = txtUserName.getText();
        if (userName.equals("Client1")) {
            try {
                Navigation.navigation(Routes.CLIENT1, pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (userName.equals("Client2")) {

            try {
                Navigation.navigation(Routes.CLIENT2, pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (userName.equals("Client3")) {

            try {
                Navigation.navigation(Routes.CLIENT3, pane);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            lblMessage.setVisible(true);
        }
    }
}
