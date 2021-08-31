package PuppyWar.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class PowerUp {
    private int x;
    private int y;
    private int angle;
    private BufferedImage img;
    Rectangle hitBox;
    private int count = 0;

    PowerUp(int x, int y, int angle, BufferedImage img){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.img = img;
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }
    void setX(int x) { this.x = x; }
    void setY(int y) { this. y = y;}
    void setCount(int count) {this.count = count;}

    public int getX(){return x;}
    public int getCount(){return count;}

    public Rectangle getHitBoxPow(){
        return hitBox.getBounds();
    }
    void update(){
        if (getX() != 500 && count==0) {
            this.setX(500);
            this.setY(510);
        }
        if(getHitBoxPow().intersects(Game.soldier.getHitBoxS()) && count==0){
           setX(900);
           count=1;
        }
        this.hitBox.setLocation(x,y);
    }

    public void drawImage(Graphics2D g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);

        g2d.setColor(new Color(0,100,0,100));
        g2d.drawRect(x,y,this.img.getWidth(), this.img.getHeight());
    }
}
