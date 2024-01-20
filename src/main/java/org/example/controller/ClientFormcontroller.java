package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ClientFormcontroller {

    @FXML
    private AnchorPane root;

    @FXML
    private TextArea txtarea;

    @FXML
    private TextField txtmessage;

    Socket remoteSocket;
    ServerSocket serverSocket;
    String message;

public void initialize(){
   // new Thread (()->{});

        Scanner input = new Scanner(System.in);
    //new Thread (()->{});

    try {
         remoteSocket = new Socket("localhost", 3002);
        System.out.println("3");
        DataOutputStream dataOutputStream = new DataOutputStream(remoteSocket.getOutputStream());
        System.out.println("4");
        dataOutputStream.writeUTF("Hello server...!");

        DataInputStream dataInputStream = new DataInputStream(remoteSocket.getInputStream());

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

        String masssage ="";
        String replay ="";

        while (!masssage.equals("end")) {
           replay =bufferedReader.readLine();
           dataOutputStream.writeUTF(replay);
           dataOutputStream.flush();

            masssage = dataInputStream.readUTF();
            txtarea.setText("Server"+masssage);
        }
        dataInputStream.close();
        dataOutputStream.close();
        bufferedReader.close();
        remoteSocket.close();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }

}



    @FXML
    void btnsend(ActionEvent event) {


    }

}
