package org.example.controller;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.example.ClientUtil.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class ClientFormcontroller implements Initializable {

    public VBox messagContainer;
    public ScrollPane scrollPane;
    public ScrollPane pane;
    public Text txtname;
    public Text namset;
    public TextField txttype;
    public Button sendbtn;
    public Text nametxt;
    public Text namset1;
    public Button btnemoji;
    @FXML
    private AnchorPane root;

    Socket socket;
    ServerSocket serverSocket;
    String message;
    String replay;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;
    String reply;
    String name;
    public int count =1;
    public String image;
    private static final double paneHeight = 500;

    static boolean openWindow = false;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtname.setText(LoginFormcontroller.name);
      //  namset.setText(ServerFormcontroller.Allname.get(0));
       // showNameBar.setText(ServerFormcontroller.Allname.get(0));
        closePane();
        new Thread(() -> {
            try {
                    if (socket == null){
                        socket = new Socket("localhost", 3000);

                    }

                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                openWindow=true;
                System.out.println(ServerFormcontroller.name);



                while (true){
                    if(ServerFormcontroller.Allname.size()>=1) {
                        for (int i = count; i < ServerFormcontroller.Allname.size(); i++) {
                            namset.setText(namset.getText() + "," + ServerFormcontroller.Allname.get(i));
                            //showNameBar.setText(namset.getText());
                            count++;
                        }
                    }

                    replay=dataInputStream.readUTF();
                        image=message;
                    System.out.println(replay);
                    appendMessage(name +" :"+ message, "-fx-border-color: #CF76FF; -fx-background-color: #CF76FF; -fx-background-radius: 0px 20px 20px 20px; -fx-border-radius: 0px 20px 20px 20px;");

                }
            } catch (IOException e) {

                e.printStackTrace();
            }


        }).start();


    }
    @FXML
    public void txttypeOnAction(ActionEvent event) {
        sendbtnOnAction(event);
    }


    @FXML
    public void sendbtnOnAction(ActionEvent event) {
        printName();
        new Thread(() -> {

            try {
                reply = txttype.getText();
                if(reply!=null) {
                    dataOutputStream.writeUTF(reply);
                    dataOutputStream.flush();
                    appendMessageMe("Me :"+ reply, "-fx-border-color: #11D2E5; -fx-background-color: #34E9FA; -fx-background-radius: 20px 0px 20px 20px; -fx-border-radius: 20px 0px 20px 20px;");
                }
                txttype.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();

    }
    private void closePane() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), pane);
        transition.setToY(paneHeight);
        transition.play();
    }
    private void appendMessage(String message, String style) {
        if (message.matches(".*\\.(png|jpe?g|gif)$")) {
            Platform.runLater(() -> {
                try {
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(105);
                    imageView.setPreserveRatio(true);

                    VBox imageContainer = new VBox(imageView);
                    imageContainer.setAlignment(Pos.CENTER_LEFT);
                    imageContainer.setPadding(new Insets(10));
                    imageContainer.setSpacing(10);

                    Label textLabel = new Label(name+" : ");
                    textLabel.setWrapText(true);
                    textLabel.setAlignment(Pos.CENTER_LEFT);
                    VBox.setMargin(textLabel, new Insets(0, 10, 0, 0));

                    HBox imageBox = new HBox(textLabel, imageContainer);
                    imageBox.setAlignment(Pos.CENTER_LEFT);
                    imageBox.setSpacing(10);

                    messagContainer.getChildren().add(imageBox);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }else {
            Platform.runLater(() -> {
                Label messageLabel = new Label(message);
                messageLabel.setWrapText(true);
                messageLabel.setPadding(new Insets(10));
                messageLabel.setStyle(style);

                HBox messageContainer = new HBox(messageLabel);
                messageContainer.setAlignment(Pos.TOP_LEFT);
                messageContainer.setPadding(new Insets(10));
                messageContainer.setFillHeight(true);

                Label timeLabel = new Label(getCurrentTime());
                timeLabel.setStyle("-fx-font-size: 10px; -fx-text-fill: #888888;");
                timeLabel.setPadding(new Insets(0, 0, 5, 0));
                HBox.setHgrow(timeLabel, Priority.ALWAYS);

                VBox chatBubble = new VBox(timeLabel, messageContainer);
                chatBubble.setAlignment(Pos.TOP_LEFT);
                chatBubble.setPadding(new Insets(5));

                messagContainer.getChildren().add(chatBubble);
            });
        }
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

    private void printName(){
        name = txtname.getText();
    }
    public void btncamerOnAction(ActionEvent event) {
        try {
            dataOutputStream.flush();
            dataOutputStream.writeUTF(new String("Desert.jpg"));

            dataOutputStream.flush();
           // dataOutputStream.reset();
            int sz= Integer.parseInt(dataInputStream.readUTF());
            System.out.println ("Receiving "+(sz/1024)+" Bytes From Sever");
           // txtarea.appendText("Receiving "+(sz/1024)+" Bytes From Sever");

            byte b[]=new byte [sz];
            int bytesRead = dataInputStream.read(b, 0, b.length);
            for (int i = 0; i<sz; i++)
            {
                System.out.print(b[i]);
                //txtarea.appendText(String.valueOf(b[i]));
            }

            FileOutputStream fos=new FileOutputStream(new File("demo.jpg"));
            fos.write(b,0,b.length);
            System.out.println ("From Server : "+dataInputStream.readUTF());
           // txtarea.appendText("Form Server "+dataInputStream.readUTF());




        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void btnemojiOnAction(ActionEvent event) {
        if(openWindow==true){
            openPane();
            txttype.requestFocus();
            openWindow = false;
        }else {
            closePane();
            openWindow = true;
        }
    }
    private void openPane() {
        TranslateTransition transition = new TranslateTransition(Duration.seconds(0.5), pane);
        transition.setToY(0);
        transition.play();
    }

    public void gringbigeyesOnAction(ActionEvent event) {
        txttype.appendText(convertEmojiCode("U+1f603"));
    }

    public void gringfacesmileonAction(ActionEvent event) {
        txttype.appendText(convertEmojiCode("U+1f604"));
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
    private String convertEmojiCode(String emojiCode) {
        int codePoint = Integer.parseInt(emojiCode.substring(2), 16);
        return new String(Character.toChars(codePoint));
    }



}