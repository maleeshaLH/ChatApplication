package org.example.controller;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
    public Pane perant;
    public Text txtClientName;
    @FXML
    private TextArea txtarea;

    @FXML
    private TextField txtmessage;
    Socket socket;
    ServerSocket serverSocket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    BufferedReader bufferedReader;
    private List<ClientHandler> clients = new ArrayList<>();   static String name;

    String replay ="";
    String message ="";



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        new Thread(() -> {
            try {

                    serverSocket = new ServerSocket(3001);

                socket = serverSocket.accept();
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());

                while (!message.equals("end")){
                    new ClientFormcontroller(socket,cl);
                    message=dataInputStream.readUTF();
                    txtarea.appendText("Client \n"+message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }




        }).start();

    }



    @FXML
    void btnsend(ActionEvent event) throws IOException {
        dataOutputStream.writeUTF(txtmessage.getText().trim());
        dataOutputStream.flush();
    }



    @FXML
    public void AddonAction(ActionEvent event) throws IOException {
        Stage stage1 =new Stage();
        stage1.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/client_form.fxml"))));
        stage1.setTitle("Login Form");
        stage1.centerOnScreen();

        stage1.show();
        this.perant.getChildren().clear();
        this.perant.getChildren().add(FXMLLoader.load(this.getClass().getResource("/view/client_form.fxml")));
    }
    static void writeOutput(String str) {
        try {
            FileOutputStream fos = new FileOutputStream("test.txt");
            Writer out = new OutputStreamWriter(fos, "UTF8");
            out.write(str);
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

   @FXML
   public void btnemoje(ActionEvent event) {

        String jaString = new String("\u65e5\u672c\u8a9e\u6587\u5b57\u5217");
        writeOutput(jaString);
        String inputString = readInput();
        String displayString = jaString + " " + inputString;
        //new ShowString(displayString, "Conversion Demo");
        txtmessage.setText(displayString);
    }
    static String readInput() {
        StringBuffer buffer = new StringBuffer();
        try {
            FileInputStream fis = new FileInputStream("test.txt");
            InputStreamReader isr = new InputStreamReader(fis, "UTF8");
            Reader in = new BufferedReader(isr);
            int ch;
            while ((ch = in.read()) > -1) {
                buffer.append((char)ch);
            }
            in.close();
            return buffer.toString();
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void btncameronAction(ActionEvent event) {
        try {
            String red = dataInputStream.readUTF();
            txtarea.appendText(red);

            File file = new File(red);
            FileInputStream fin =new FileInputStream(file);

            int c;
            int sz=(int)file.length();
            byte b[]=new byte [sz];
            dataOutputStream.writeUTF(String.valueOf(new Integer(sz)));
            dataOutputStream.flush();

            int j=0;
            while ((c = fin.read()) != -1) {

                b[j]=(byte)c;
                j++;
            }
            fin.close();
            dataOutputStream.flush();
            dataOutputStream.write(b,0,b.length);
            dataOutputStream.flush();
            txtarea.appendText("Size " +sz);
            txtarea.appendText("buf size "+serverSocket.getReceiveBufferSize());
            dataOutputStream.writeUTF(new String("ok"));
            dataOutputStream.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
