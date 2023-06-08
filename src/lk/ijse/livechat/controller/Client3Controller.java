package lk.ijse.livechat.controller;

import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;

public class Client3Controller extends Thread{
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
        Label label=new Label("Client_3".trim());
        txtOutput.getChildren().add(label);

        new Thread(()->{
            try {
                socket=new Socket("localhost",5702);
                dataInputStream=new DataInputStream(socket.getInputStream());
                dataOutputStream=new DataOutputStream(socket.getOutputStream());
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
                            txtOutput.getChildren().add(imageView);
                        });                } else if (!message.equalsIgnoreCase("Finish")) {
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

    public void btnSend(MouseEvent mouseEvent) throws IOException {
        dataOutputStream.writeUTF(txtInput.getText().trim());
        reply=txtInput.getText();
        HBox hbox = new HBox();
        hbox.setAlignment(Pos.CENTER_RIGHT);
        Label label3=new Label("Client3 :"+reply);
        label3.setPrefWidth(150);
        label3.setWrapText(true);
        label3.setMaxHeight(Double.MAX_VALUE);
        label3.setStyle("-fx-font-size:12px;-fx-background-color: lightblue; -fx-background-radius: 10; -fx-text-alignment:left ; -fx-padding:1 0 0 2; ");
        hbox.getChildren().add(label3);

        txtOutput.getChildren().add(hbox);

        dataOutputStream.flush();
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

                // Convert BufferedImage to JavaFX Image
                Image javafxImage = SwingFXUtils.toFXImage(image, null);

                // Create an ImageView to display the image
                ImageView imageView = new ImageView(javafxImage);
                txtOutput.getChildren().add(imageView);

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
