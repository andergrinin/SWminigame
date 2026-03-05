package com.example.kurs4;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DnewSoldier {
    public static Stage window = null;
    public static Scene scene;
    public static void display() throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        Parent root = fxmlLoader.load();
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Створення нового солдата");

        scene = new Scene(root, 600, 400);
        window.setScene(scene);

        window.showAndWait();
    }
}
