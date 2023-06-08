package lk.ijse.livechat.controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
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

public class Client2Controller extends Thread {
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
        Label label=new Label("Client_2".trim());
        txtOutPut.getChildren().add(label);

        new Thread(() -> {
            try {
                socket=new Socket("localhost",3063);

                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                dataInputStream=new DataInputStream(socket.getInputStream());
                while (true) {
                    message = dataInputStream.readUTF();
                    if (message.equals("Image")) {
                        int imageSize = dataInputStream.readInt();
                        byte[] imageBytes = new byte[imageSize];
                        dataInputStream.readFully(imageBytes);

                        // Process the received image data
                        ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
                        Image receivedImage = new Image(bis);

                        // Display the image in the client's UI
                        ImageView imageView = new ImageView(receivedImage);
                        Platform.runLater(() -> {
                            txtOutPut.getChildren().add(imageView);
                        });                    } else if (!message.equalsIgnoreCase("Finish")) {
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            try {
                BufferedImage image = ImageIO.read(selectedFile);

                // Convert BufferedImage to JavaFX Image
                Image javafxImage = SwingFXUtils.toFXImage(image, null);

                // Create an ImageView to display the image
                ImageView imageView = new ImageView(javafxImage);
                txtOutPut.getChildren().add(imageView);

                // Send image to the server
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] imageBytes = baos.toByteArray();

                // Send image to the server
                dataOutputStream.writeUTF("Image");
                dataOutputStream.writeInt(imageBytes.length);
                dataOutputStream.write(imageBytes);
                dataOutputStream.flush();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void btnMinimize(MouseEvent mouseEvent) {
        Stage stage = (Stage) minimize.getScene().getWindow();
        stage.setIconified(true);
    }
}
