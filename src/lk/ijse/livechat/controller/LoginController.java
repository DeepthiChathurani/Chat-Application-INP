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

    public void btnLogin(ActionEvent actionEvent) throws IOException {
        String userName= txtUserName.getText();
        if(userName.equals("Client1")){
            Navigation.navigation(Routes.CLIENT1,pane);
        } else if (userName.equals("Client2")) {
            Navigation.navigation(Routes.CLIENT2,pane);
        }else if (userName.equals("Client3")) {
            Navigation.navigation(Routes.CLIENT3,pane);
        }else {
            lblMessage.setVisible(true);
//            lblLoginMessage.setText("Invalid Login, Please try again!");
        }
    }
}
