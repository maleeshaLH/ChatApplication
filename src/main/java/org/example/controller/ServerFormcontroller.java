package org.example.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.ClientUtil.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ServerFormcontroller implements Initializable {

    public AnchorPane root;
    public TextField txtname;
    public Button btnadd;

    Socket socket;
    ServerSocket serverSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;
    private List<ClientHandler> clients = new ArrayList<>();   static String name;
    public static ArrayList<String> Allname = new ArrayList<>();


    String replay ="";
    String message ="";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        txtname.setText(LoginFormcontroller.name);
        new Thread(() -> {
            try {
                if(serverSocket==null){
                    serverSocket = new ServerSocket(3000);
                }

                while (!serverSocket.isClosed()){
                    socket = serverSocket.accept();
                   ClientHandler clientHandler= new ClientHandler(socket,clients);
                   clients.add(clientHandler);
                    System.out.println("client connected");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

    }

    @FXML
    public void txtnameOnAction(ActionEvent event) {
        btnaddOnAction(event);
    }

    public void btnaddOnAction(ActionEvent event) {
        name = txtname.getText();
        Allname.add(name);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/client_form.fxml"));
        Parent root1 = null;
        ClientFormcontroller controller = new ClientFormcontroller();
        try {
            root1 = (Parent) fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        fxmlLoader.setController(controller);
        Stage stage = new Stage();
        stage.setScene(new Scene(root1));
        stage.setTitle("Client");

        stage.show();
        txtname.clear();
    }
}
