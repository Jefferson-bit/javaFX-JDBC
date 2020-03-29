/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/**
 *
 * @author crash
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Views.fxml"));
            ScrollPane scrollpane = loader.load();
            
            scrollpane.setFitToHeight(true);
            scrollpane.setFitToWidth(true);
            Scene mainScene = new Scene(scrollpane);
            stage.setScene(mainScene);
            stage.setTitle("Sample javaFX application");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
