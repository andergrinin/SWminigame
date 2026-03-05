package com.example.kurs4;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class CapsController implements Initializable {
    @FXML
    private Label welcomeText;
    public ComboBox Cap1;
    public ComboBox Cap2;
    public ComboBox Cap3;
    public ToggleGroup tgr;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void onOk(ActionEvent actionEvent) throws FileNotFoundException {

        if( Cap1.getValue()==null )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Ви не вибрали 1 капітана!");

            alert.showAndWait();

            return;
        }

        if( Cap2.getValue()==null )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Ви не вибрали 2 капітана!");

            alert.showAndWait();

            return;
        }

        if( Cap3.getValue()==null )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Ви не вибрали 3 капітана!");

            alert.showAndWait();

            return;
        }
        String str1 = Cap1.getValue().toString();
        String str2 = Cap2.getValue().toString();
        String str3 = Cap3.getValue().toString();
        Trooper tr1 = null, tr2 = null, tr3 = null;

        if(str1.equals(str2) || str2.equals(str3) || str3.equals(str1))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Ви вибрали однакові, так не можна!");

            alert.showAndWait();

            return;
        }
//        for(Trooper tr : Main.getEndor().getTroopers())
//        {
//            if(tr.forCap() == str1)tr1=tr;
//            else if(tr.forCap() == str2)tr2=tr;
//            else if(tr.forCap() == str3)tr3=tr;
//        }


        System.out.println(str1 + str2 + str3);
        //if(tr1!= null && tr2!= null && tr3!= null) Main.makeCap(tr1, tr2, tr3);
        Main.makeCap(str1, str2, str3);
       // Main.addTrooper(strN, chckW,  side, rezrb.getText() );
        DnewCaps.window.close();
    }

    @FXML
    public void onCancel(ActionEvent actionEvent){
        System.out.println("onCancel");
        DnewCaps.window.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Dialog started!");
        for(Trooper tr : Main.getEndor().getTroopers())
        {
            Cap1.getItems().add(tr.forCap());
            Cap2.getItems().add(tr.forCap());
            Cap3.getItems().add(tr.forCap());
        }

        //Cap1.getItems().addAll("Empire", "Rebellion");

    }



}
