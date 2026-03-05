package com.example.kurs4;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

public class General extends Captain{ //24

    //Виклик метода базового класу
    public double HPalg(){return super.getHealth() + 300;} //10

    //Виклик конструктора базового класу
    public General(String name,  int level, boolean active, boolean side)
    {
        super(name, level, active, side); //25
        super.setHealth(HPalg());
        super.setDamage(100);
        if(this.getSide()) {
            try {
                setItrooper(new ImageView(new Image(new FileInputStream("src/images/ageneral.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                setItrooper(new ImageView(new Image(new FileInputStream("src/images/imgeneral.png"))));
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
        setRank("General");
        setTeamCap(3);
    }

    public General()
    {
        super("Noname General", 1, false, Main.rnd.nextBoolean());
        super.setHealth(HPalg());
        super.setDamage(100);
        if(this.getSide()) {
            try {
                setItrooper(new ImageView(new Image(new FileInputStream("src/images/ageneral.png"))));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            try {
                setItrooper(new ImageView(new Image(new FileInputStream("src/images/imgeneral.png"))));
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
        setRank("General");
        setTeamCap(3);
    }

    protected General(Trooper tr) throws CloneNotSupportedException {
        this(tr.getName(), tr.getLevel(), false, tr.getSide());
        if(tr.getHeartpack()==null)this.setHeartpack(new int[0]);
        else this.setHeartpack(Arrays.copyOf(tr.getHeartpack(),tr.getHeartpack().length));
        this.moveAll(tr.getX()+150, tr.getY()+150);
        this.setimtext(Arrays.toString(this.getHeartpack()));
        this.setRank("General");
    }


}
