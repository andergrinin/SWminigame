package com.example.kurs4;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Circle;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Base {

    //11
    protected String name;
    protected double x;
    protected double y;
    protected double xout;
    protected double yout;
    protected Circle circle; //18
    protected Group BaseGroup;
    protected byte side;


    ArrayList<Trooper> inside = new ArrayList<>();

    protected ImageView imgBase; //18
    protected Label BaseText; //18


    public boolean catchtr(Trooper tr)
    {
        MyBounds bounds = tr.getMyBounds();
        boolean can=false;
        if( imgBase.boundsInParentProperty().get().contains(
                bounds.x, bounds.y ) )can = true;

        if( imgBase.boundsInParentProperty().get().contains(
                bounds.x+bounds.wx, bounds.y ) )can = true;

        if( imgBase.boundsInParentProperty().get().contains(
                bounds.x, bounds.y+bounds.wy ) )can = true;

        if( imgBase.boundsInParentProperty().get().contains(
                bounds.x+bounds.wx, bounds.y+bounds.wy ) )can = true;
        if(tr.getDel()>0)
        {can=false;
            tr.setDel(tr.getDel()-1);}
        if(side == 0 && tr.getSide())can=false;
        if(side == 1 && !tr.getSide())can=false;
        for(Trooper tr2 : Main.getEndor().getTroopers())
        {
            if(tr2.isCap())can = false;
        }

        if( can == false )return false;

        tr.setDel(10*60);
        inside.add(tr);

        System.out.println(tr.toString() + "   IN"); //5
        tr.moveAll(x, y);
        tr.setCatched(true);
        return true;
    }


    public void lifeCycle()
    {
        ArrayList<Trooper> tmpinside = new ArrayList<>();


        for(int i=0;i<inside.size(); ++i)
        {
            if(inside.get(i).getSizeHeart()<3 && !inside.get(i).getClaimed()) {
                inside.get(i).addHeartpack(Main.rnd.nextInt(19)+1);
                inside.get(i).setClaimed(true);
            }
            if(inside.get(i).getHealth()<100 && inside.get(i).getHeartpack()!=null)inside.get(i).UseHeartpack();
            inside.get(i).setDel(inside.get(i).getDel()-1);
            if(inside.get(i).getDel() < 1 ){
                inside.get(i).moveAll(xout, yout);
                inside.get(i).newMove();
                inside.get(i).setCatched(false);
                inside.get(i).setClaimed(false);
                System.out.println(inside.get(i).toString() + "   OUT"); //5
            }
            else tmpinside.add(inside.get(i));
        }
        inside = tmpinside;
    }


    public ImageView getImgBase() {
        return this.imgBase;
    }
    public double getBaseX(){return x;}
    public double getBaseY(){return y;}
    public Group getBaseGroup(){return BaseGroup;}
    public Circle getCircle() {
        return circle;
    }
    public String getBaseName() {
        return name;
    }
    public void setSide(byte i){this.side = i;}
    public int getSide(){return side;}


}
