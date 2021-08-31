package PuppyWar.game;

import PuppyWar.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Puppies {
    private int x;
    private int y;
    private int health;
    private int angle;
    private int direction = 1;
    private BufferedImage img;
    public ArrayList<Bullets> ammo;
    Rectangle hitBox;

    Puppies(int x, int y, int health, int angle, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.health = health;
        this.angle = angle;
        this.img = img;
        this.ammo = new ArrayList<>();
        this.hitBox = new Rectangle(x,y,this.img.getWidth(), this.img.getHeight());
    }

    void setHealthP(int health) { this.health = health;}
    void setX(int x){ this.x = x; }
    void setY(int y) { this.y = y;}

    void setImg(BufferedImage img){
        this.img = img;
    }

    public int getY(){return y;}
    public int getHealthP() {
        return health;
    }
    public BufferedImage getImg() {
        return img;
    }


    private void move(int direction){
        this.x += direction;
        this.hitBox.setLocation(x,y);
    }

    public Rectangle getHitBoxP(){
        return hitBox.getBounds();
    }

    void update() {
        move(direction);
        if (x == GameConstants.GAME_SCREEN_WIDTH - 70) {
            y += 10;
            direction = -1;
        }
        if (x == 1) {
            y += 10;
            direction = 1;
        }
        if ((Game.tickCount) % 180 == 0) {
            Bullets b = new Bullets(x, y, angle, Game.bulletImage);
            this.ammo.add(b);
        }
        this.ammo.forEach(bullets -> bullets.pupUpdate());
        checkBorder();

        for(int i=0;i<ammo.size();i++){
            if(ammo.get(i).getHitBoxB().intersects(Game.soldier.getHitBoxS())){
                System.out.print("soldier hit\n");
                ammo.remove(i);
                Game.soldier.setHealth(Game.soldier.getHealth()-10);
            }
        }
    }

    private void checkBorder() {
        if (x < 1) {
            x = 1;
        }
        if (x >= GameConstants.GAME_SCREEN_WIDTH - 70) {
            x = GameConstants.GAME_SCREEN_WIDTH - 70;
        }
    }

    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        rotation.rotate(Math.toRadians(angle), this.img.getWidth() / 2.0, this.img.getHeight() / 2.0);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
        this.ammo.forEach(bullets -> bullets.drawImage(g));

        g2d.setColor(new Color(0,100,0,100));
        g2d.drawRect(x,y,this.img.getWidth(), this.img.getHeight());
    }
}
