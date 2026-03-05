package com.example.kurs4;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class RebelBase extends Base{

    public RebelBase(double x, double y) throws FileNotFoundException {
        circle = new Circle(x,y,300.0);
        circle.setFill(Paint.valueOf("Cyan"));


        imgBase=new ImageView(new Image(new FileInputStream("src/images/rebelbase.png")));
        imgBase.setFitHeight(400);
        imgBase.setFitWidth(400);

        imgBase.setX(x-200);
        imgBase.setY(y-200);
        this.name="База Повстанців";
        BaseText = new Label(name);
        BaseText.setTextFill(Paint.valueOf("Black"));
        BaseText.setFont(new Font(40));
        BaseText.setText(name);
        BaseText.setLayoutY(y-250);
        BaseText.setLayoutX(x-140);


        this.side = 1;
        this.x = x;
        this.y = y;
        this.xout = x+400;
        this.yout = y+400;
        this.BaseGroup = new Group(circle, imgBase, BaseText);

    }


}
