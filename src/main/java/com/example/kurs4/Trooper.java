package com.example.kurs4;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Trooper implements Cloneable{
//1
    private String rank;
    private boolean isCap;
    private int teamCap;
    private Group TrGroup;
    private ImageView itrooper; //16
    private Rectangle hpRect; //16
    private Rectangle acRect; //16
    private Label itext; //16
    private String name;
    private int level;
    private double health;
    private double damage;
    private int []heartpack; //6
    private int delayy;
    private boolean active;
    private double aimX, aimY;
    private double maxhp;
    private final double stepX=2.0;
    private final double stepY=2.0;
    private boolean catched;
    private boolean claimed;
    private boolean side;
    ArrayList<Trooper> insidetr = new ArrayList<>();


    public double HPalg(){
        return 100+(level*2.0);
    } //10
    //конструктор класу
    public Trooper(String name, int level, boolean active, boolean side){ //13
        this.name=name;
        this.level = level;
        this.active = active;
        this.side = side;
        //setName(name);
        //setLevel(level);
        teamCap = 1;


        setDel(0);
        this.rank = "Trooper";
        claimed = false;
        int x= Main.rnd.nextInt((int)Main.getEndor().getRootWidth());
        int y= Main.rnd.nextInt((int)Main.getEndor().getRootHeight());
        this.active = active;
        acRect = new Rectangle(50, 65, Paint.valueOf("Orange"));
        acRect.setX(x);
        acRect.setY(y+30);
        if(active)acRect.setFill(Paint.valueOf("Red"));

        if(side)
        {
        try {
            itrooper=new ImageView(new Image(new FileInputStream("src/images/atrooper.png")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }}
        else {
            try {
                itrooper=new ImageView(new Image(new FileInputStream("src/images/imtrooper.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        itrooper.setFitHeight(55);
        itrooper.setFitWidth(40);

        itrooper.setX(x+5);
        itrooper.setY(y+35);
        hpRect = new Rectangle(health, 10.0, Paint.valueOf("Green"));
        //hpRect.setWidth(getHealth());
        hpRect.setX(x);
        hpRect.setY(y+25);
        setHealth(HPalg());
        setDamage(health/10 + level);
        maxhp=getHealth();
        itext=new Label(name+  " LVL: " + level + " [ ]");
        itext.setTextFill(Paint.valueOf("White"));
        itext.setFont(new Font(15));
        itext.setText(name+  " LVL: " + level + " [ ]" );
        itext.setLayoutX(x);
        itext.setLayoutY(y);

        aimX= Main.rnd.nextInt((int)Main.getEndor().getRootWidth());
        aimY= Main.rnd.nextInt((int)Main.getEndor().getRootHeight());

        this.TrGroup = new Group(acRect, itrooper, hpRect, itext);

        TrGroup.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{ //19
            if (event.getButton() == MouseButton.PRIMARY && !isCap && !catched){
                this.active = !this.active;
                System.out.println(name + "isActive"); //20

            }
        } );
    }


    public Trooper(){ //2 //12 //13
        this.name="Noname Trooper";
        this.level = 1;
        this.active = false;
        this.side = Main.rnd.nextBoolean();
        teamCap = 1;


        setDel(0);
        this.rank = "Trooper";
        claimed = false;
        int x= Main.rnd.nextInt((int)Main.getEndor().getRootWidth());
        int y= Main.rnd.nextInt((int)Main.getEndor().getRootHeight());

        acRect = new Rectangle(50, 65, Paint.valueOf("Orange"));
        acRect.setX(x);
        acRect.setY(y+30);
        if(active)acRect.setFill(Paint.valueOf("Red"));

        if(side)
        {
            try {
                itrooper=new ImageView(new Image(new FileInputStream("src/images/atrooper.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }}
        else {
            try {
                itrooper=new ImageView(new Image(new FileInputStream("src/images/imtrooper.png")));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
        itrooper.setFitHeight(55);
        itrooper.setFitWidth(40);

        itrooper.setX(x+5);
        itrooper.setY(y+35);
        hpRect = new Rectangle(health, 10.0, Paint.valueOf("Green"));
        hpRect.setX(x);
        hpRect.setY(y+25);
        setHealth(HPalg());
        setDamage(health/10 + level);
        maxhp=getHealth();
        itext=new Label(name+  " LVL: " + level + " [ ]");
        itext.setTextFill(Paint.valueOf("White"));
        itext.setFont(new Font(15));
        itext.setText(name+  " LVL: " + level + " [ ]" );
        itext.setLayoutX(x);
        itext.setLayoutY(y);

        aimX= Main.rnd.nextInt((int)Main.getEndor().getRootWidth());
        aimY= Main.rnd.nextInt((int)Main.getEndor().getRootHeight());

        this.TrGroup = new Group(acRect, itrooper, hpRect, itext);

        TrGroup.addEventHandler(MouseEvent.MOUSE_CLICKED, event ->{
            if (event.getButton() == MouseButton.PRIMARY && !isCap && !catched){
                this.active = !this.active;
                System.out.println(name + "isActive"); //20
            }
        } );
    }



    public void lifeCycle() //35
    {
        //boolean cappresent=false;
        for(Trooper tr : Main.getEndor().getTroopers())
        {
            if(tr.isCap() && tr.getTeamCap()==this.teamCap)
            {
                aimX=tr.getX();
                aimY = tr.getY();
            }
        }
        if(!active)
        {
            acRect.setFill(Paint.valueOf("Orange"));
            double x = itext.getLayoutX();
            double y = itext.getLayoutY();

            double dx = aimX - x;
            dx = (Math.abs(dx) > stepX) ? Math.signum(dx) * stepX : dx;
            double dy = aimY - y;
            dy = (Math.abs(dy) > stepY) ? Math.signum(dy) * stepY : dy;


            itext.setLayoutX(x + dx);
            itext.setLayoutY(y + dy);

            acRect.setX(x + dx);
            acRect.setY(y + dy + 30);

            itrooper.setX(x + dx + 5);
            itrooper.setY(y + dy + 35);

            hpRect.setX(x + dx);
            hpRect.setY(y + dy + 25);
            setHealth(health);
            if ((Math.abs(aimX - (x + dx)) == 0.0) && (Math.abs(aimY - (y + dy)) == 0.0)) {
                aimX = Main.rnd.nextInt((int) Main.getEndor().getRootWidth());
                aimY = Main.rnd.nextInt((int) Main.getEndor().getRootHeight());
            }
        }
        else if(active)acRect.setFill(Paint.valueOf("Red"));
    }


    public MyBounds getMyBounds(){
        return new MyBounds( itrooper.getX(), itrooper.getY(),
                itrooper.boundsInParentProperty().get().getWidth(),
                itrooper.boundsInParentProperty().get().getHeight() );
    }

    public boolean catchnewtr(Trooper tr)
    {
        MyBounds bounds = tr.getMyBounds();
        boolean can=false;
        if( itrooper.boundsInParentProperty().get().contains(
                bounds.x, bounds.y ) )can = true;

        if( itrooper.boundsInParentProperty().get().contains(
                bounds.x+bounds.wx, bounds.y ) )can = true;

        if( itrooper.boundsInParentProperty().get().contains(
                bounds.x, bounds.y+bounds.wy ) )can = true;

        if( itrooper.boundsInParentProperty().get().contains(
                bounds.x+bounds.wx, bounds.y+bounds.wy ) )can = true;
        if(tr.getRank() == "Trooper" && this.getTeamCap()!=1)can = false;
        if(tr.getRank() == "Captain"&& this.getTeamCap()!=2)can = false;
        if(tr.getRank() == "General"&& this.getTeamCap()!=3)can = false;

        if( can == false )return false;

        tr.setActive(false);
        tr.getAcRect().setFill(Paint.valueOf("Orange"));
        insidetr.add(tr);

        tr.moveAll(itrooper.getX()+80, itrooper.getY());
        tr.setCatched(true);
        return true;
    }

    public void newLife()
    {
        double y = this.getY();
        double x = this.getX();
        int row=0;
        for(int i=0;i<insidetr.size(); ++i)
        {
            if(insidetr.get(i).getX() + 90*(i+1) > Main.getEndor().getRootWidth())row++;
            if(insidetr.get(i).getY() + 80*row > Main.getEndor().getRootHeight())row=-1*row;
            insidetr.get(i).moveAll(x+90*(i+1),y+80*row);

        }
        int size1 = 0, size2=0, size3=0;
        for(Trooper tr : Main.getEndor().getTroopers())
        {
            if(tr.isCap() && tr.getTeamCap()==1)size1=tr.insize();
            if(tr.isCap() && tr.getTeamCap()==2)size2=tr.insize();
            if(tr.isCap() && tr.getTeamCap()==3)size3=tr.insize();
        }

        if((size1+size2+size3) == Main.getEndor().getTroopers().size()-3 )
        {
            if(size1 == Math.max(size1, Math.max(size2,size3))&& size1!=size2 && size1 != size3) System.out.println("Team 1 win");
            else if(size2 == Math.max(size1, Math.max(size2,size3)) && size2!=size1 && size2 != size3) System.out.println("Team 2 win");
            else if(size3 == Math.max(size1, Math.max(size2,size3))&& size3!=size1 && size3 != size2) System.out.println("Team 3 win");
            else System.out.println("No winners");
        }
    }

    public int insize()
    {
        return insidetr.size();
    }

    public void moveAll(double ax, double ay)
    {
        itext.setLayoutX(ax);
        itext.setLayoutY(ay);

        acRect.setX(ax);
        acRect.setY(ay+30);

        itrooper.setX(ax+5);
        itrooper.setY(ay+35);

        hpRect.setX(ax);
        hpRect.setY(ay+25);
    }
    public void up(){
        moveAll(itext.getLayoutX(),itext.getLayoutY()-8);
    }
    public void down(){
        moveAll(itext.getLayoutX(),itext.getLayoutY()+8);
    }
    public void right(){
        moveAll(itext.getLayoutX()+8,itext.getLayoutY());
    }
    public void left(){
        moveAll(itext.getLayoutX()-8,itext.getLayoutY());
    }


    public void Atack(Trooper tr) { //35
        if (tr.itrooper.boundsInParentProperty().get().contains(
                itrooper.getX() + itrooper.boundsInParentProperty().get().getWidth() / 2.0,
                itrooper.getY() + itrooper.boundsInParentProperty().get().getHeight() / 2.0)) {

            if(delayy<=0 && tr.getDel()<=0){
                health-=tr.getDamage();
                this.newHP(health);
                tr.setHealth(tr.getHealth()-damage);
                tr.newHP(getHealth());
                System.out.println(name + " fight " + tr.getName());
                tr.setDel(2*60);
            }
//            if(delayy<=0 ){
//                tr.setHealth(tr.getHealth()-damage);
//                tr.newHP(getHealth());
//                System.out.println(name + " atack " + tr.getName());
//                tr.setDel(2*60);
//            }
            else tr.setDel(tr.getDel()-1);

            if(tr.getHealth()<=0)
            {
                Main.getEndor().removeTr(tr);
            }
        }
    }

    @Override
    public String toString() { //5
        return "Soldier{" +
                "rank='" + rank + '\'' +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", health=" + health +
                ", damage=" + damage +
                ", heartpack=" + Arrays.toString(heartpack) +
                '}';
    }
    public String forCap()
    {
        return name + " LVL: " + level + " dmg: " + damage;
    }
//3
    protected Trooper(Trooper tr) throws CloneNotSupportedException { //7 //10 //12 //13
        this(tr.getName(), tr.getLevel(), false, tr.getSide());
        if(tr.getHeartpack()==null)this.heartpack = new int[0];
        else this.heartpack = Arrays.copyOf(tr.getHeartpack(),tr.getHeartpack().length); //8
        this.moveAll(tr.getX()+150, tr.getY()+150);
        itext.setText(name+  " LVL: " + level +" "+ Arrays.toString(heartpack) ); //8
    }

    @Override
    public boolean equals(Object o) { //5
        if (this == o) return true;
        if (!(o instanceof Trooper)) return false;
        Trooper trooper = (Trooper) o;
        return getLevel() == trooper.getLevel() && Double.compare(trooper.getHealth(), getHealth()) == 0 && Double.compare(trooper.getDamage(), getDamage()) == 0 && getRank().equals(trooper.getRank()) && getName().equals(trooper.getName()) && Arrays.equals(getHeartpack(), trooper.getHeartpack());
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getRank(), getName(), getLevel(), getHealth(), getDamage());
        result = 31 * result + Arrays.hashCode(getHeartpack());
        return result;
    }



    public void newHP(double hp){hpRect.setWidth(hp);}
    public int[] getHeartpack(){return heartpack;}

    public void addHeartpack(int hp)
    {
        if(heartpack == null)heartpack = new int[1];
        else heartpack = Arrays.copyOf(heartpack, heartpack.length+1);
        heartpack[heartpack.length-1]=hp;
        itext.setText(name+  " LVL: " + level +" "+ Arrays.toString(heartpack) );
    }
    public void UseHeartpack(){
        if(heartpack==null);
        else if(heartpack.length <=1)
        {
            setHealth(getHealth() + (double)heartpack[0]);
            //hpRect.setWidth(getHealth());
            heartpack = null;
            itext.setText(name+  " LVL: " + level +" []" );
        }
        else {
            setHealth(getHealth() + (double)heartpack[heartpack.length-1]);
            heartpack = Arrays.copyOf(heartpack, heartpack.length-1);
            //hpRect.setWidth(getHealth());
            itext.setText(name+  " LVL: " + level +" "+ Arrays.toString(heartpack) );
        }

    }
    public void newMove(){
        boolean cappr=false;
        for(Trooper tr : Main.getEndor().getTroopers())
        {
            if(tr.isCap())
            {
                cappr=true;
                aimX=tr.getX();
                aimY = tr.getY();
            }
        }
        if(!cappr) {
            aimX = Main.rnd.nextInt((int) Main.getEndor().getRootWidth());
            aimY = Main.rnd.nextInt((int) Main.getEndor().getRootHeight());
        }
    }
    public void renew(){itrooper.setFitHeight(55);
        itrooper.setFitWidth(40);this.TrGroup = new Group(acRect, getItrooper(), itext,  hpRect);}
    public ImageView getItrooper(){return itrooper;}
    public void setItrooper(ImageView im){this.itrooper = im;}
    public double getX(){return itext.getLayoutX();}
    public double getY(){return itext.getLayoutY();}
    public void setRank(String r){rank = r;}
    public String getRank(){return rank;}
    public Group getTrGroup(){ return TrGroup;}
    public double getMaxhp(){return maxhp;}
    public int getSizeHeart(){
        if(heartpack==null)return 0;
        else return heartpack.length;}
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getDel() {
        return delayy;
    }
    public void setDel(int a) {
        delayy = a;
    }
    public double getHealth() {
        return health;
    }
    public void setHealth(double health) {
        this.health = health;
        if(health>150.0)
        {
            hpRect.setFill(Paint.valueOf("Yellow"));
            hpRect.setWidth(health*0.25);
        }
        else {
            hpRect.setWidth(health);
            hpRect.setFill(Paint.valueOf("Green"));
        }
    }
    public double getDamage() {
        return damage;
    }
    public void setDamage(double damage) {
        this.damage = damage;
    }
    public void setCatched(boolean cat){this.catched = cat;}
    public boolean getCatched(){return catched;}
    public void setClaimed(boolean cat){this.claimed = cat;}
    public boolean getSide(){return side;}
    public void setSide(boolean cat){this.side = cat;}
    public boolean getClaimed(){return claimed;}
    public void setimtext(String arr){ itext.setText(name+  " LVL: " + level +" "+ arr );}
    public void setActive(boolean cat){this.active = cat;}
    public boolean getActive(){return active;}

    public boolean isCap() {        return isCap;    }

    public void setCap(boolean cap) {        isCap = cap;    }

    public int getTeamCap() {        return teamCap;    }

    public void setTeamCap(int teamCap) {        this.teamCap = teamCap;}



    public void setItext(Label itext) {
        this.itext = itext;
    }

    public void setHeartpack(int[] heartpack) {
        this.heartpack = heartpack;
    }

    public Rectangle getAcRect() {
        return acRect;
    }
}
