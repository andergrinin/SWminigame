package com.example.kurs4;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Endor { //27

    private static Image img; //17
    private final static int rootHeight = 3000;
    private final static int rootWidth = 5000;
    private MiniMap miniMap;

    private static final Pane root = new Pane();
    private ArrayList<Trooper> troopers;

    private ArrayList<Base> bases; //30
    private Text imperialsoldiers; //17
    private Text rebelsoldiers; //17
    private boolean finish;



    public Text getImperialsoldiers() {        return imperialsoldiers;    }
    public Text getRebelsoldiers() {        return rebelsoldiers;    }
    public static Pane getRoot() {
        return root;
    }
    public ArrayList<Base> getBases(){return bases;}
    public static int getRootHeight() {
        return rootHeight;
    }
    public static int getRootWidth() {
        return rootWidth;
    }
    public MiniMap getMiniMap() {  return miniMap;  }


    public Endor() throws FileNotFoundException {
        this.finish = false;
        root.setMinWidth(rootWidth);
        root.setMinHeight(rootHeight);
        troopers = new ArrayList<>();
        bases = new ArrayList<>();

        Rectangle rectangle = new Rectangle(rootWidth, rootHeight);
        img = new Image(new FileInputStream("src/images/endor.jpg"));
        rectangle.setFill(new ImagePattern(img));
        root.getChildren().add(rectangle);

        imperialsoldiers = new Text();
        imperialsoldiers.setFill(Color.WHITE);
        imperialsoldiers.setFont(new Font("Times New Roman", 24));
        root.getChildren().add(imperialsoldiers);

        rebelsoldiers = new Text();
        rebelsoldiers.setFill(Color.WHITE);
        rebelsoldiers.setFont(new Font("Times New Roman", 24));
        root.getChildren().add(rebelsoldiers);

        this.troopers = new ArrayList<>();


        this.miniMap = new MiniMap();
        root.getChildren().addAll(miniMap.getPane());
    }

    public void addBase(Base bs) throws FileNotFoundException {
        bases.add(bs);
        root.getChildren().add(bs.getBaseGroup());
        miniMap.addBase(bs);

    }

    public void  miniMapInFront(){
        root.getChildren().remove(miniMap.getPane());
        root.getChildren().add(miniMap.getPane());
    }

    public void  addTrooper (Trooper tr) throws FileNotFoundException {
        this.troopers.add(tr);
        root.getChildren().add(tr.getTrGroup());
        miniMap.addTrooper(tr);



    }

    public void removeTr(Trooper tr)
    {

        System.out.println(tr.getName() + " dead...");
        root.getChildren().remove(tr.getTrGroup());
        this.troopers.remove(tr);
        this.miniMap.removeTrooper(tr);
    }

    public ArrayList<Trooper> getTroopers(){     return troopers;    }


    public void lifeCycle()
    {
        if(finish)return;
        int reb=0;
        int imp=0;
        boolean result = false;
        for(Trooper tr : troopers)
            if(tr!=null){
                if(tr.getSide())reb++;
                else imp++;
                if(!tr.getCatched() && !tr.isCap()) {
                    tr.lifeCycle();
                    if(tr instanceof General && tr.getHealth()<300)tr.UseHeartpack(); //14
                }
                if(tr.isCap())tr.newLife();
            }

        if(reb==0) result = true;
        if(imp == 0)result = true;
        if(!result)
        {
            for(int i=0;i<(troopers.size()-1);++i)
            {
                Trooper tr1=troopers.get(i);
                for(int j=i+1; j< troopers.size();++j)
                {
                    Trooper tr2=troopers.get(j);
                    if(tr1.getCatched() || tr2.getCatched()  || tr2.getSide()==tr1.getSide() || tr1.isCap() || tr2.isCap());
                    else tr1.Atack(tr2);
                    if(tr1.isCap())tr1.catchnewtr(tr2);
                }
            }
        }

        if(result)
        {
            this.finish = true;
            int finalReb = reb;
            Platform.runLater( () ->{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Кінець гри!!");
                if(finalReb ==0)alert.setHeaderText("Імперія перемогла Альянс!!!");
                else alert.setHeaderText("Альянс здолав Імперію!!!");

                alert.showAndWait();
                Platform.exit();
            });
        }

        for(int i=0;i<troopers.size();++i)
        {
            for(Base bs : bases)
            {
                if(bs.catchtr(troopers.get(i)) && !troopers.get(i).getCatched());
            }

        }
        bases.get(0).lifeCycle();
        bases.get(1).lifeCycle();
        bases.get(2).lifeCycle();
    }






}
