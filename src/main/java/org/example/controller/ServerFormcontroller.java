package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerFormcontroller {

    @FXML
    private TextArea txtarea;

    @FXML
    private TextField txtmessage;
    Socket remoteSocket;
    ServerSocket serverSocket;

    public void initialize(){
        Scanner scanner = new Scanner(System.in);
        try {
             serverSocket = new ServerSocket(3002);
            System.out.println("1");
            //System.out.println("Server is started");
            txtarea.setText("Server is started");
            System.out.println("5");
            Socket localSocket = serverSocket.accept();
            System.out.println("2");
            System.out.println("Server is connected");

            DataOutputStream dataOutputStream = new DataOutputStream(localSocket.getOutputStream());

            DataInputStream dataInputStream = new DataInputStream((localSocket.getInputStream()));

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            String massage = "";

            String replay = "";

            while (!massage.equals("end")) {
               massage = dataInputStream.readUTF();
               txtarea.setText("Client "+massage);

               replay = bufferedReader.readLine();

               dataOutputStream.writeUTF(replay);
               dataOutputStream.flush();

            }
            dataInputStream.close();
            dataOutputStream.close();
            bufferedReader.close();
            localSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void btnsend(ActionEvent event) {

    }

}
