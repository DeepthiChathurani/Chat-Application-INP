package lk.ijse.livechat.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.net.Socket;

public class Client1Controller {
    public AnchorPane pane;
    public ImageView close;
    public ImageView minimize;
    public ScrollPane scrollPane;
    public VBox txtOutPut;
    public TextField txtInput;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Socket socket;
    BufferedReader reader;
    String message = "";
    File file;
    String reply = "";
    String path = "";

    public void initialize() {
        // Disable horizontal scroll bar
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

// Disable vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        Label label3 = new Label("Client 01".trim());
        txtOutPut.getChildren().add(label3);

        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3002);

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (true) {
                    message = dataInputStream.readUTF();
                    if (message.equals("Image")) {
                        receiveImageFromServer();
                    }else if (!message.equalsIgnoreCase("Finish")) {
                        Label label = new Label(message);
                        Platform.runLater(() -> {
                            txtOutPut.getChildren().add(label);
                        });

                    }
                }

            } catch (Exception e) {

            }

        }).start();
    }


    public void btnsend(MouseEvent mouseEvent) {
    }

    public void btnCamera(MouseEvent mouseEvent) {
    }
}
