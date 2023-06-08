package lk.ijse.livechat.controller;

import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerController {
    public AnchorPane Pane;
    public ScrollPane scrollPane;
    public VBox txtOutPut;
    public ImageView minimize;
    public TextField txtInput;
    public ImageView close;
    public ImageView send;

    ServerSocket serverSocket1;
    Socket socket1;
    DataInputStream dataInputStream1;
    DataOutputStream dataOutputStream1;

    ServerSocket serverSocket2;
    Socket socket2;
    DataInputStream dataInputStream2;
    DataOutputStream dataOutputStream2;


    ServerSocket serverSocket3;
    Socket socket3;
    DataInputStream dataInputStream3;
    DataOutputStream dataOutputStream3;


    String message1 = "";
    String message2 = "";
    String message3 = "";

    public void initialize() {
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

// Disable vertical scroll bar
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        new Thread(() -> {
            try {
                serverSocket1 = new ServerSocket(3004);
                Label label = new Label("Server Start");
                txtOutPut.getChildren().add(label);
                socket1 = serverSocket1.accept();
                Label label2 = new Label("\nClient1 Start");
                Platform.runLater(() -> {
                    txtOutPut.getChildren().add(label2);
                });
                dataInputStream1 = new DataInputStream(socket1.getInputStream());
                dataOutputStream1 = new DataOutputStream(socket1.getOutputStream());

                while (!message1.equalsIgnoreCase("Finish")) {
                    message1 = dataInputStream1.readUTF();
                    Label label3 = new Label("\nClient1: " + message1.trim());
                    Platform.runLater(() -> {
                        txtOutPut.getChildren().add(label3);
                    });
                    dataOutputStream2.writeUTF("Client1 :" + message1.trim());
                    dataOutputStream3.writeUTF("Client1 :" + message1.trim());
                    dataOutputStream2.flush();
                    dataOutputStream3.flush();
                }
                dataInputStream1.close();
                dataOutputStream1.close();
            } catch (IOException e) {
            }
        }).start();

        new Thread(() -> {
            try {
                serverSocket2 = new ServerSocket(3063);
                Label label4 = new Label("Server Start");
                txtOutPut.getChildren().add(label4);
                socket2 = serverSocket2.accept();
                Label label5 = new Label("\nClient2 Start");
                Platform.runLater(() -> {
                    txtOutPut.getChildren().add(label5);
                });

                dataInputStream2 = new DataInputStream(socket2.getInputStream());
                dataOutputStream2 = new DataOutputStream(socket2.getOutputStream());

                while (!message2.equalsIgnoreCase("Finish")) {
                    message2 = dataInputStream2.readUTF();
                    Label label6 = new Label("\nClient2: " + message2.trim());
                    Platform.runLater(() -> {
                        txtOutPut.getChildren().add(label6);
                    });
                    dataOutputStream1.writeUTF("Client2 :" + message2.trim());
                    dataOutputStream3.writeUTF("Client2 :" + message2.trim());

                    dataOutputStream1.flush();
                    dataOutputStream3.flush();


                }
                dataInputStream2.close();
                dataOutputStream2.close();


            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                serverSocket3 = new ServerSocket(5702);
                Label label = new Label("Server Start");
                txtOutPut.getChildren().add(label);
                socket3 = serverSocket3.accept();
                Label label1 = new Label("\nClient3 Start");
                Platform.runLater(() -> {
                    txtOutPut.getChildren().add(label1);
                });

                dataInputStream3 = new DataInputStream(socket3.getInputStream());
                dataOutputStream3 = new DataOutputStream(socket3.getOutputStream());


                while (!message3.equalsIgnoreCase("Finish")) {
                    message3 = dataInputStream3.readUTF();
                    Label label2 = new Label("\nClient3: " + message3.trim());
                    Platform.runLater(() -> {
                        txtOutPut.getChildren().add(label2);
                    });
                    /*if (message.equals("Image")) {
                        forwardImageToClient(dataInputStream, dataOutputStream);
                        forwardImageToClient(dataInputStream, dataOutputStream2);
                    }*/
                    dataOutputStream1.writeUTF("Client3 :" + message3.trim());
                    dataOutputStream2.writeUTF("Client3 :" + message3.trim());

                    dataOutputStream1.flush();
                    dataOutputStream2.flush();


                }
                dataInputStream3.close();
                dataOutputStream3.close();


            } catch (Exception e) {
            }
        }).start();
    }
    public void btnClose(MouseEvent mouseEvent) {
        Stage stage = (Stage) close.getScene().getWindow();
        stage.close();
    }

    public void btnMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

    public void btnSend(MouseEvent mouseEvent) throws IOException {
        dataOutputStream1.writeUTF("Server :" + txtInput.getText().trim());
        Label label = new Label("\n\t\t\t\t\t\t\t\t\t\t\tServer :" + txtInput.getText());
        txtOutPut.getChildren().add(label);
        dataOutputStream1.flush();
        txtInput.clear();

        dataOutputStream2.writeUTF("Server :" + txtInput.getText().trim());
        Label label1 = new Label("\n\t\t\t\t\t\t\t\t\t\t\tServer :" + txtInput.getText());
        txtOutPut.getChildren().add(label1);
        dataOutputStream2.flush();
        txtInput.clear();

        dataOutputStream3.writeUTF("Server :" + txtInput.getText().trim());
        Label label2 = new Label("\n\t\t\t\t\t\t\t\t\t\t\tServer :" + txtInput.getText());
        txtOutPut.getChildren().add(label2);
        dataOutputStream3.flush();
        txtInput.clear();
    }

    public void btnCamera(MouseEvent mouseEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            try {
                BufferedImage image = ImageIO.read(selectedFile);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] imageBytes = baos.toByteArray();

                Image serverImage = new Image(new ByteArrayInputStream(imageBytes));
                ImageView imageView = new ImageView(serverImage);
                // Send image to all clients
                dataOutputStream1.writeUTF("Image");
                dataOutputStream1.writeInt(imageBytes.length);
                dataOutputStream1.write(imageBytes);

                dataOutputStream1.flush();

                dataOutputStream2.writeUTF("Image");
                dataOutputStream2.writeInt(imageBytes.length);
                dataOutputStream2.write(imageBytes);
                dataOutputStream2.flush();

                dataOutputStream3.writeUTF("Image");
                dataOutputStream3.writeInt(imageBytes.length);
                dataOutputStream3.write(imageBytes);

                dataOutputStream3.flush();

                // Display the image in the server's UI
                Platform.runLater(() -> {
                    txtOutPut.getChildren().add(imageView);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
