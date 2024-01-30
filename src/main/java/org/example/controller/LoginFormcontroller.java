package org.example.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class LoginFormcontroller {
    public AnchorPane root;
    public TextField txtname;
    public Button btnadd;
    public static ArrayList<String> Allname = new ArrayList<>();
    static String name;



    public void btnaddOnAction(ActionEvent event) throws IOException {
        name = txtname.getText();
        Allname.add(name);
       FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/server_form.fxml"));
        Parent parent = null;

        ClientFormcontroller clientFormcontroller = new ClientFormcontroller();
        parent = fxmlLoader.load();

        fxmlLoader.setController(clientFormcontroller);
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Client");
        stage.show();
        txtname.clear();

    }

    public void txtnameOnAction(ActionEvent event) throws IOException {
        btnaddOnAction(event);
    }
}
