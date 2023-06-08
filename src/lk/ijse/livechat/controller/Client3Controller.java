package lk.ijse.livechat.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client3Controller {
    public AnchorPane pane;
    public ImageView minimize;
    public ScrollPane scrollPane;
    public VBox txtOutput;
    public TextField txtInput;
    public ImageView close;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    String message="";
    String reply="";

    public void initialize(){

        // Disable horizontal scroll bar
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

// Disable vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Label label=new Label("Client 03".trim());
        txtOutput.getChildren().add(label);

        new Thread(()->{
            try {
                socket=new Socket("localhost",5700);
                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                while (true) {
                    message = dataInputStream.readUTF();
                    if (message.equals("Image")) {
                  //      receiveImageFromServer();
                    } else if (!message.equalsIgnoreCase("Finish")) {
                        Label label1 = new Label(message);
                        Platform.runLater(() -> {
                            txtOutput.getChildren().add(label1);
                        });

                    }
                }
            } catch (IOException e) {

            }

        }).start();
    }

    public void btnClose(MouseEvent mouseEvent) {
        Stage stage =(Stage)close.getScene().getWindow();
        stage.close();
    }

    public void btnSend(MouseEvent mouseEvent) {
    }

    public void btnCamera(MouseEvent mouseEvent) {
    }

    public void btnMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
