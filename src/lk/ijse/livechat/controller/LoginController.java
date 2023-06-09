package lk.ijse.livechat.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lk.ijse.livechat.util.Navigation;
import lk.ijse.livechat.util.Routes;
import lk.ijse.livechat.util.formDecorator;

import javax.swing.text.Style;
import java.io.IOException;

public class LoginController {

    public TextField txtUserName;
    public Label lblMessage;
    public AnchorPane pane;

    public String userName;
    public ImageView close;
    public ImageView minimize;
    private Object Style;

    public void btnLogin(ActionEvent actionEvent) {

        userName = txtUserName.getText();
        Parent root= null;
        if (userName.equals("Client1")) {

            try {
                root = FXMLLoader.load(getClass().getResource("/lk/ijse/livechat/view/Client1.fxml"));
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                Scene scene = new Scene(new formDecorator(stage,root));
                scene.setFill(null);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (userName.equals("Client2")) {

            try {
                root = FXMLLoader.load(getClass().getResource("/lk/ijse/livechat/view/Client2.fxml"));
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                Scene scene = new Scene(new formDecorator(stage,root));
                scene.setFill(null);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else if (userName.equals("Client3")) {

            try {
                root = FXMLLoader.load(getClass().getResource("/lk/ijse/livechat/view/Client3.fxml"));
                Stage stage = new Stage();
                stage.initStyle(StageStyle.TRANSPARENT);
                Scene scene = new Scene(new formDecorator(stage,root));
                scene.setFill(null);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            lblMessage.setVisible(true);
        }
    }

    public void btnClose(MouseEvent mouseEvent) {
        Stage stage=(Stage) close.getScene().getWindow();
        stage.close();
    }

    public void btnMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
