package com.example.kurs4;

import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class MiniMap {
    final static private double SCALE = 0.1;
    private final Pane pane;
    private HashMap<ImageView, ImageView> TrMap; //14
    //private HashMap<String, ImageView> TrMap;
    //private HashMap<Trooper, ImageView> TrMap;
    //private HashMap<Base, Group> BaseMap;


    private Rectangle mapArea;
    private boolean mapOpacity;


    private Image miniMapBackground;
    //private Image miniMapBorder;

    //private final Rectangle border;


    //getters
    public Pane getPane() {
        return pane;
    }

    public Rectangle getMapArea() {
        return mapArea;
    }

    public static double getSCALE() {
        return SCALE;
    }

    public MiniMap() throws FileNotFoundException {
        this.pane = new Pane();

        this.pane.setMinWidth(Endor.getRootWidth() * MiniMap.SCALE);
        this.pane.setMinHeight(Endor.getRootHeight() * MiniMap.SCALE);

        TrMap = new HashMap<>();
        //BaseMap = new HashMap<>();

        miniMapBackground = new Image(new FileInputStream("src/images/endor.jpg"));
        this.mapOpacity = false;
        Rectangle rectangle = new Rectangle(0, 0, pane.getMinWidth(), pane.getMinHeight());
        rectangle.setFill(new ImagePattern(miniMapBackground));
        rectangle.setStroke(Paint.valueOf("White"));
        rectangle.setStrokeWidth(5);

        mapArea = new Rectangle(0, 0, Main.getSceneWidth() * MiniMap.SCALE, Main.getSceneHeight() * MiniMap.SCALE);
        mapArea.setFill(Color.TRANSPARENT);
        mapArea.setStrokeWidth(2);
        mapArea.setStroke(Paint.valueOf("Grey"));

        this.pane.getChildren().addAll(rectangle, mapArea);


        pane.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (!mapOpacity) moveto(event.getX(), event.getY());
            }
            if (event.getButton() == MouseButton.SECONDARY) {
                mapOpacity = !mapOpacity;
                if (mapOpacity) {
                    this.pane.setOpacity(0);
                } else {
                    this.pane.setOpacity(1);
                }
            }
        });

    }

    public void moveto(double x, double y) {
        if (x < mapArea.getWidth() / 2) {
            Main.getScrollPane().setHvalue(0);
        } else if (x > pane.getWidth() - mapArea.getWidth() / 2) {
            Main.getScrollPane().setHvalue(1);
        } else Main.getScrollPane().setHvalue(x / pane.getWidth());

        if (y < mapArea.getHeight() / 2) {
            Main.getScrollPane().setVvalue(0);
        } else if (y > pane.getHeight() - mapArea.getHeight() / 2) {
            Main.getScrollPane().setVvalue(1);
        } else Main.getScrollPane().setVvalue(y / pane.getHeight());
    }


    public void removeTrooper(Trooper tr){  pane.getChildren().remove(TrMap.get(tr.getItrooper()));}
    public void addTrooper(Trooper tr) throws FileNotFoundException {
        ImageView imgv;
        switch (tr.getRank())
        {
            case "Trooper":
                if(tr.getSide())imgv = new ImageView(new Image(new FileInputStream("src/images/atrooper.png")));
                else imgv = new ImageView(new Image(new FileInputStream("src/images/imtrooper.png")));

                break;
            case "Captain":
                if(tr.getSide())imgv = new ImageView(new Image(new FileInputStream("src/images/acaptain.png")));
                else imgv = new ImageView(new Image(new FileInputStream("src/images/imcaptain.png")));
                break;
            case "General":
                if(tr.getSide())imgv = new ImageView(new Image(new FileInputStream("src/images/ageneral.png")));
                else imgv = new ImageView(new Image(new FileInputStream("src/images/imgeneral.png")));
                break;
            default:
                imgv = new ImageView(new Image(new FileInputStream("src/images/atrooper.png")));
                break;
        }
        imgv.setLayoutX(tr.getX()*SCALE);
        imgv.setLayoutY(tr.getY()*SCALE);
        imgv.setPreserveRatio(true);
        imgv.setFitHeight(55*SCALE);
        TrMap.put(tr.getItrooper(), imgv);
        pane.getChildren().add(imgv);
    }



    public void addBase(Base base) throws FileNotFoundException {
        ImageView imgv = null;
        Group gr;
        Circle circle = new Circle(base.getCircle().getRadius()*MiniMap.SCALE);
        switch (base.getBaseName())
        {
            case "База Імперії":
                imgv = new ImageView(new Image(new FileInputStream("src/images/imperialbase.png")));
                imgv.setPreserveRatio(true);
                circle.setFill(Paint.valueOf("Grey"));
                imgv.setFitHeight(base.getImgBase().getFitHeight() * MiniMap.SCALE);
                break;
            case "База Повстанців":
                imgv = new ImageView(new Image(new FileInputStream("src/images/rebelbase.png")));
                imgv.setPreserveRatio(true);
                circle.setFill(Paint.valueOf("Cyan"));
                imgv.setFitHeight(base.getImgBase().getFitHeight() * MiniMap.SCALE);
                break;
            case "Нейтральна база":
                imgv = new ImageView(new Image(new FileInputStream("src/images/neutralbase.png")));
                imgv.setPreserveRatio(true);
                circle.setFill(Paint.valueOf("Lime"));
                imgv.setFitHeight(base.getImgBase().getFitHeight() * MiniMap.SCALE);
                break;
        }

        imgv.setLayoutX((base.getBaseX()-200)*MiniMap.SCALE);

        imgv.setLayoutY((base.getBaseY()-200)*MiniMap.SCALE);
        circle.setLayoutX(base.getBaseX()*MiniMap.SCALE);
        circle.setLayoutY(base.getBaseY()*MiniMap.SCALE);
        gr = new Group(imgv, circle);
        //basemap.put(base, gr);
        pane.getChildren().addAll(circle, imgv);

    }



    public void updateMap() {

        for( Trooper tr : Main.getEndor().getTroopers())
        {
            ImageView imgv = TrMap.get(tr.getItrooper());
            if(imgv != null) {
                imgv.setLayoutX(tr.getX() * MiniMap.SCALE);
                imgv.setLayoutY(tr.getY() * MiniMap.SCALE);
            }
        }
        this.pane.getChildren().remove(mapArea);
        this.pane.getChildren().add(mapArea);


    }
}
