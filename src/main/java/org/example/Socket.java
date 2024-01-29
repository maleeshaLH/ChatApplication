package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Socket extends Application {

    public static void main(String []arge){launch(arge);}

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/server_form.fxml"))));
        stage.setTitle("server Form");
        stage.centerOnScreen();
        stage.show();

//        Stage stage1 =new Stage();
//        stage1.setScene(new Scene(FXMLLoader.load(this.getClass().getResource("/view/client_form.fxml"))));
//        stage1.setTitle("Login Form");
//        stage1.centerOnScreen();
//
//        stage1.show();
    }


}
