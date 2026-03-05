package com.example.kurs4;

import com.example.kurs4.DnewSoldier;
import com.example.kurs4.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    @FXML
    private Label welcomeText;
    public TextField TrName;
    public CheckBox TrActive;
    public ComboBox TrSide;
    public ToggleGroup tgr;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void onOk(ActionEvent actionEvent) throws FileNotFoundException {
        String strN= TrName.getText();

        if( ! ( (strN != null) && (strN.length()>0) ) )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Введіть ім'я!");

            alert.showAndWait();

            return;
        }

        boolean chckW= TrActive.isSelected();

        if( TrSide.getValue()==null )
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            alert.setTitle("УВАГА!!!");
            alert.setHeaderText("Оберіть сторону!");

            alert.showAndWait();

            return;
        }
        String strC = TrSide.getValue().toString();
        boolean side = false;
        if(strC == "Empire")side = false;
        if(strC == "Rebellion") side = true;


        RadioButton rezrb = (RadioButton)tgr.getSelectedToggle();
        System.out.println("Ім'я класу для створення: "+rezrb.getText());

        Main.addTrooper(strN, chckW,  side, rezrb.getText() );
        DnewSoldier.window.close();
    }

    @FXML
    public void onCancel(ActionEvent actionEvent){
        System.out.println("onCancel");
        DnewSoldier.window.close();
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Dialog started!");
        TrName.setText("Luke Skywalker <3");
        TrActive.setSelected(false);
        TrSide.getItems().addAll("Empire", "Rebellion");

    }
}