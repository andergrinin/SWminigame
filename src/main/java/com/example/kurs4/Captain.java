package com.example.kurs4;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class Captain extends Trooper{ //24

    //Виклик метода базового класу
    public double HPalg(){return super.getHealth() + 100;} //10 //12
    private double DMGalg(){return super.getDamage() + 10;}

    //Виклик конструктора базового класу
    public Captain(String name, int level, boolean active, boolean side)
    {
        super(name, level, active, side); //25
        super.setHealth(HPalg());
        super.setDamage(DMGalg());
        if(this.getSide()) {
            try {
                setItrooper(new ImageView(new Image(new FileInputStream("src/images/acaptain.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                setItrooper(new ImageView(new Image(new FileInputStream("src/images/imcaptain.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        renew();
        this.getTrGroup().addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if (event.getButton() == MouseButton.PRIMARY && !this.isCap() && !this.getCatched()){
                this.setActive(!this.getActive());

            }
        } );

        setRank("Captain");
        setTeamCap(2);
    }

    public Captain()
    {
        super("Noname Captain", 1, false, Main.rnd.nextBoolean());
        super.setHealth(HPalg());
        super.setDamage(DMGalg());
        if(this.getSide()) {
            try {
                setItrooper(new ImageView(new Image(new FileInputStream("src/images/acaptain.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                setItrooper(new ImageView(new Image(new FileInputStream("src/images/imcaptain.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        renew();
        this.getTrGroup().addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if (event.getButton() == MouseButton.PRIMARY  && !this.isCap() && !this.getCatched()){
                this.setActive(!this.getActive());

            }
        } );

        setTeamCap(2);
        setRank("Captain");
    }

    protected Captain(Trooper tr) throws CloneNotSupportedException {
        this(tr.getName(), tr.getLevel(), false, tr.getSide());
        if(tr.getHeartpack()==null)this.setHeartpack(new int[0]);
        else this.setHeartpack(Arrays.copyOf(tr.getHeartpack(),tr.getHeartpack().length));
        this.moveAll(tr.getX()+150, tr.getY()+150);
        this.setimtext(Arrays.toString(this.getHeartpack()));
        this.setRank("Captain");
    }

}
