package com.example.kurs4;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.Random;


class MyBounds{
    public double x, y, wx, wy;
    public MyBounds(double x, double y, double wx, double wy){
        this.x = x;
        this.y = y;
        this.wx= wx;
        this.wy= wy;
    }
}

public class Main extends Application {

    private static final int sceneWidth = 1920;
    private static final int sceneHeight = 1080;

    private static Endor endor; //27
    static {        try {            endor = new Endor();        } catch (FileNotFoundException e) {            e.printStackTrace();        }    }

    private static final ScrollPane scrollPane = new ScrollPane(Endor.getRoot());
    private static final Scene scene = new Scene(scrollPane,  sceneWidth, sceneHeight);

    private static double scrollX;
    private static double scrollY;

    public static Random rnd = new Random(new Date().getTime());


    public static int[] ArrayHealth;
    public static int[] ArrayCopy;
    private static Stage pStage;

    public static  boolean isCheack = false;
    public static double nextX;
    public static double nextY;



    public static int getSceneHeight() {
        return sceneHeight;
    }
    public static int getSceneWidth() {
        return sceneWidth;
    }
    public static ScrollPane getScrollPane() {
        return scrollPane;
    }
    public static Endor getEndor() {
        return endor;
    }
    public static Scene getScene() {  return scene; }

    private void setPrimaryStage(Stage pStage){
        Main.pStage = pStage;
    }






    @Override
    public void start(Stage primaryStage) throws IOException {

        setPrimaryStage(primaryStage);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        endor.addBase(new ImperialBase(4000, 2000));
        endor.addBase(new RebelBase(1000, 1000));
        endor.addBase(new NeutralBase(2500,1500));

        endor.addTrooper(new Trooper("Rex", 5,  false,true));
        endor.addTrooper(new Trooper("Lex", 1,  false, true));
        endor.addTrooper(new Trooper("Kenobi", 3,  false,false));
        endor.addTrooper(new Captain("Cole", 4,  false,true));
        endor.addTrooper(new Captain("Henk", 4,  false, false));
        endor.addTrooper(new Captain("Redle", 4,  false, false));
        endor.addTrooper(new General("Jackz", 4,  false, true));
        endor.addTrooper(new General("Jame", 4,  false, true));
        endor.addTrooper(new General("Yondu", 4,  false, false));
        endor.addTrooper(new Trooper());
        endor.addTrooper(new Captain());
        endor.addTrooper(new General());





        endor.miniMapInFront();


        scene.addEventHandler(KeyEvent.KEY_PRESSED, event ->{
            for (Trooper tr : endor.getTroopers()){
                if (tr.getActive() && !tr.getCatched()){
                    if (event.getCode() == KeyCode.W){ //21
                        if (tr.getItrooper().getY() >=0)
                            tr.up();
                    }
                    if (event.getCode() == KeyCode.S){ //21
                        if (tr.getItrooper().getY() + tr.getItrooper().getFitHeight()
                                <= endor.getRoot().getHeight())
                            tr.down();
                    }
                    if (event.getCode() == KeyCode.A){ //21
                        if (tr.getItrooper().getX() >=0)
                            tr.left();
                    }
                    if (event.getCode() == KeyCode.D){ //21
                        if (tr.getItrooper().getX() + tr.getItrooper().getFitWidth() + 150
                                <= endor.getRoot().getWidth())
                            tr.right();
                    }
                }
            }
            if (event.getCode() == KeyCode.DELETE){ //22
                for (int i = 0; i < endor.getTroopers().size(); ++i){
                    Trooper tr = endor.getTroopers().get(i);
                    if (tr.getActive()){
                        endor.removeTr(tr);
                    }
                }
            }
            if (event.getCode() == KeyCode.ESCAPE){ //23
                for (Trooper tr : endor.getTroopers()){
                    if (tr.getActive()){
                        tr.setActive(false);
                    }
                }
            }

            if (event.getCode() == KeyCode.INSERT ){ //26
                try {
                    DnewSoldier.display();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (event.getCode() == KeyCode.G ){
                try {
                    DnewCaps.caps();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (event.getCode() == KeyCode.C) {
                for (Trooper tr : endor.getTroopers()) {
                    if (tr.getActive()) {
                        switch (tr.getRank())
                        {
                            case "Trooper":
                                try {
                                    endor.addTrooper(new Trooper(tr));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (CloneNotSupportedException e) {
                                    e.printStackTrace();
                                }
                                break;
                            case "Captain":
                                try {
                                    endor.addTrooper(new Captain(tr));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (CloneNotSupportedException e) {
                                    e.printStackTrace();
                                }
                            case "General":
                                try {
                                    endor.addTrooper(new General(tr));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (CloneNotSupportedException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }


                    }
                }
            }

        });
        
        scrollPane.viewportBoundsProperty().addListener((observable, oldBounds, bounds) -> {

            Main.scrollX = -1 * (int) bounds.getMinX();
            Main.scrollY = -1 * (int) bounds.getMinY();


            updateChordINFO();


//            if (endor.gettrs().size() == 0){
//                currentStatusINFO();
//            }

            endor.getMiniMap().getPane().setLayoutX(scrollX + 1310);
            endor.getMiniMap().getPane().setLayoutY(scrollY + scene.getHeight() - endor.getMiniMap().getPane().getHeight() - 650);
            endor.getMiniMap().getMapArea().setLayoutX(scrollX*MiniMap.getSCALE());
            endor.getMiniMap().getMapArea().setLayoutY(scrollY*MiniMap.getSCALE());
        });



        //FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        primaryStage.setMaximized(true);
        primaryStage.setTitle("SW BATTLEGROUNDS");
        primaryStage.setScene(scene);

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {



                currInfo();
                endor.lifeCycle();
                endor.getMiniMap().updateMap();



            }
        };

        timer.start();
        primaryStage.show();

    }

    public void currInfo()
    {
        int emp=0;
        int reb=0;
        for(Trooper tr : endor.getTroopers())
        {
            if(tr.getSide())
            {
                reb++;
            }
            else emp++;
        }
        endor.getRebelsoldiers().setText("Rebel army: " + reb);
        endor.getImperialsoldiers().setText("Imperial army: " + emp);
    }
    public void updateChordINFO(){
        endor.getImperialsoldiers().setX(scrollX + 20);
        endor.getImperialsoldiers().setY(scrollY + 60);

        endor.getRebelsoldiers().setX(scrollX + 20);
        endor.getRebelsoldiers().setY(scrollY + 90);
    }

    public static void addTrooper(String strN, boolean chckW,  boolean side, String className ) throws FileNotFoundException {
        System.out.println(strN+chckW+side+className);

        switch (className) {
            case "Captain":
                endor.addTrooper(new Captain(strN, Main.rnd.nextInt(15), chckW, side));
                break;
            case "General":
                endor.addTrooper(new General(strN, Main.rnd.nextInt(15), chckW, side));
                break;
            default:
                endor.addTrooper(new Trooper(strN, Main.rnd.nextInt(15), chckW, side));
                break;

        }
    }
//    public static void makeCap(Trooper tr1, Trooper tr2, Trooper tr3)
//    {
//        tr1.setCap(true);
//        tr2.setCap(true);
//        tr3.setCap(true);
//        tr1.setTeamCap(1);
//        tr2.setTeamCap(2);
//        tr3.setTeamCap(3);
//    }
    public static void makeCap(String s1, String s2, String s3)
    {
        for(Trooper tr : endor.getTroopers())
        {
            if(s1.equals(tr.forCap()))
            {
                tr.setCap(true);
                tr.setTeamCap(1);
                tr.getAcRect().setFill(Paint.valueOf("Green"));

            }
            else if(s2.equals(tr.forCap()))
            {
                tr.setCap(true);
                tr.setTeamCap(2);
                tr.getAcRect().setFill(Paint.valueOf("Blue"));

            }
            else if(s3.equals(tr.forCap()))
            {
                tr.setCap(true);
                tr.setTeamCap(3);
                tr.getAcRect().setFill(Paint.valueOf("Gray"));
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}