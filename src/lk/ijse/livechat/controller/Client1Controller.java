package lk.ijse.livechat.controller;

import javafx.application.Platform;
import javafx.scene.Node;
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
import java.net.Socket;

public class Client1Controller  {
    public AnchorPane pane;
    public ImageView close;
    public ImageView minimize;
    public ScrollPane scrollPane;
    public VBox txtOutPut;
    public TextField txtInput;
    public ImageView btnclose;

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
        Label label3 = new Label("Client_1".trim());
        txtOutPut.getChildren().add(label3);

        new Thread(() -> {
            try {
                socket = new Socket("localhost", 3008);

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                while (true) {
                    message = dataInputStream.readUTF();
                    if (message.equals("Image")) {
                        int imageSize = dataInputStream.readShort();
                        byte[] imageBytes = new byte[imageSize];
                        dataInputStream.readFully(imageBytes);

                        // Process the received image data
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                        Image receivedImage = new Image(bis);

                        // Display the image in the client's UI
                        ImageView imageView = new ImageView(receivedImage);
                        Platform.runLater(() -> {
                            txtOutPut.getChildren().add(imageView);
                        });
                    }
                    /*if (message.equals("Image")) {
                        receiveImageFromServer();*/
                 if (!message.equalsIgnoreCase("Finish")) {
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


    public void btnSend(MouseEvent mouseEvent) {
        try {
            dataOutputStream.writeUTF(txtInput.getText().trim());
            reply=txtInput.getText();
            Label label3=new Label("\n\t\t\t\t\t\t\t\tClient1 :"+reply);
            txtOutPut.getChildren().add(label3);
            dataOutputStream.flush();
            txtInput.clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void btnCamera() throws IOException {
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
               // dataOutputStream.writeUTF("Image");
                //dataOutputStream.writeInt(imageBytes.length);
                //dataOutputStream.write(imageBytes);
                dataOutputStream.write(imageBytes,0, imageBytes.length);
                Platform.runLater(() -> {
                    txtOutPut.getChildren().add(imageView);

                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnClose(MouseEvent event) {
        Stage stage=(Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

    public void btnMinimize(MouseEvent event) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }

}
