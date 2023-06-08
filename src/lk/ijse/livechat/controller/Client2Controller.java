package lk.ijse.livechat.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;

public class Client2Controller {
    public AnchorPane pane;
    public ScrollPane scrollPane;
    public VBox txtOutPut;
    public TextField txtInput;
    public ImageView minimize;
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
        Label label=new Label("Client 02".trim());
        txtOutPut.getChildren().add(label);

        new Thread(() -> {
            try {
                socket=new Socket("localhost",3060);

                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                dataInputStream=new DataInputStream(socket.getInputStream());
                while (true) {
                    message = dataInputStream.readUTF();
                    if (message.equals("Image")) {
                        //receiveImageFromServer();
                    } else if (!message.equalsIgnoreCase("Finish")) {
                        Label label1 = new Label(message);
                        Platform.runLater(() -> {
                          txtOutPut.getChildren().add(label1);
                        });

                    }
                }


            }catch (Exception e){

            }

        }).start();
    }

    public void btnClose(MouseEvent mouseEvent) {
        Stage stage =(Stage)close.getScene().getWindow();
        stage.close();
    }

    public void btnSend(MouseEvent mouseEvent) {
        try {
            dataOutputStream.writeUTF(txtInput.getText().trim());
            reply=txtInput.getText();
            Label label3=new Label("\n\t\t\t\t\t\t\t\tClient2 :"+reply);
            txtOutPut.getChildren().add(label3);
            dataOutputStream.flush();
            txtInput.clear();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void btnCamera(MouseEvent mouseEvent) {
    }

    public void btnMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
