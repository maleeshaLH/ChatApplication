package org.example.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ClientFormcontroller implements Initializable {

    public VBox messagContainer
            ;
    public ScrollPane scrollPane;
    public ScrollPane pane;
    @FXML
    private AnchorPane root;

    @FXML
    private TextArea txtarea;

    @FXML
    private TextField txtmessage;

    Socket socket;
    ServerSocket serverSocket;
    String message;
    String replay;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;
    String reply;

    static boolean openWindow = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new Thread(() -> {
            try {

                    socket = new Socket("localhost", 3001);

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());


                while (true){
                    replay=dataInputStream.readUTF();
                    txtarea.appendText("Client \n"+replay);
                    System.out.println(replay);
                }
            } catch (IOException e) {

                e.printStackTrace();
            }


        }).start();


    }
    private void appendMessageMe(String message, String style) {
        Platform.runLater(() -> {
            Label messageLabel = new Label(message);
            messageLabel.setWrapText(true);
            //messageLabel.setPrefWidth(200);
            messageLabel.setPadding(new Insets(10));
            messageLabel.setStyle(style);

            messageLabel.setPrefHeight(Region.USE_COMPUTED_SIZE);

            HBox messageContainer = new HBox(messageLabel);
            messageContainer.setAlignment(Pos.TOP_RIGHT);
            messageContainer.setPadding(new Insets(10));
            messageContainer.setFillHeight(true);

            Label timeLabel = new Label(getCurrentTime());
            timeLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #888888;");
            timeLabel.setPadding(new Insets(0, 0, 5, 0));
            HBox.setHgrow(timeLabel, Priority.ALWAYS);

            VBox chatBubble = new VBox(timeLabel, messageContainer);
            chatBubble.setAlignment(Pos.TOP_RIGHT);
            chatBubble.setPadding(new Insets(5));

            messagContainer.getChildren().add(chatBubble);
        });
    }
    private String getCurrentTime() {
        LocalTime currentTime = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        return currentTime.format(formatter);
    }
    @FXML
    void btnsend(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtmessage.getText().trim());
        dataOutputStream.flush();
        appendMessageMe("Me :"+ reply, "-fx-border-color: #11D2E5; -fx-background-color: #34E9FA; -fx-background-radius: 20px 0px 20px 20px; -fx-border-radius: 20px 0px 20px 20px;");



    }

    public void btncamerOnAction(ActionEvent event) {
        try {
            dataOutputStream.flush();
            dataOutputStream.writeUTF(new String("Desert.jpg"));

            dataOutputStream.flush();
           // dataOutputStream.reset();
            int sz= Integer.parseInt(dataInputStream.readUTF());
            System.out.println ("Receiving "+(sz/1024)+" Bytes From Sever");
            txtarea.appendText("Receiving "+(sz/1024)+" Bytes From Sever");

            byte b[]=new byte [sz];
            int bytesRead = dataInputStream.read(b, 0, b.length);
            for (int i = 0; i<sz; i++)
            {
                System.out.print(b[i]);
                txtarea.appendText(String.valueOf(b[i]));
            }

            FileOutputStream fos=new FileOutputStream(new File("demo.jpg"));
            fos.write(b,0,b.length);
            System.out.println ("From Server : "+dataInputStream.readUTF());
            txtarea.appendText("Form Server "+dataInputStream.readUTF());

            


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnemoje(ActionEvent event) {


    }

    public void gringbigeyesOnAction(ActionEvent event) {

    }

    public void gringfacesmileonAction(ActionEvent event) {

    }

    public void smillyonAction(ActionEvent event) {

    }

    public void upsidedownonaction(ActionEvent event) {

    }

    public void facewithtearsjoyonAction(ActionEvent event) {

    }

    public void rollingfacewithtearsjoyonAction(ActionEvent event) {

    }

    public void vinkifaceonAction(ActionEvent event) {

    }

    public void savoringfoodonAction(ActionEvent event) {

    }
}