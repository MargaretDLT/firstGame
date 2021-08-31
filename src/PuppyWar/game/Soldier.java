package PuppyWar.game;



import PuppyWar.GameConstants;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author anthony-pc
 */
public class Soldier{
    private int x;
    private int y;
    private int health;
    private int angle;
    private BufferedImage img;
    private boolean RightPressed;
    private boolean LeftPressed;
    private boolean ShootPressed;
    public ArrayList<Bullets> ammo;
    Rectangle hitBox;
    private int powAttack=0;


    Soldier(int x, int y, int angle, int health, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.img = img;
        this.angle = angle;
        this.health = health;
        this.ammo = new ArrayList<>();
        this.hitBox = new Rectangle(x, y, this.img.getWidth(), this.img.getHeight());
    }

    void setX(int x){ this.x = x; }

    void setY(int y) { this.y = y;}
    void setImg(BufferedImage img){
        this.img = img;
    }

    void setHealth(int health) { this.health = health;}

    public BufferedImage getImg(){
        return img;
    }

    public int getHealth() {
        return health;
    }

    public Rectangle getHitBoxS(){
        return hitBox.getBounds();
    }

    void toggleRightPressed() {
        this.RightPressed = true;
    }

    void toggleLeftPressed() {
        this.LeftPressed = true;
    }

    void toggleShootPressed() {
        this.ShootPressed = true;
    }

    void unToggleRightPressed() {
        this.RightPressed = false;
    }

    void unToggleLeftPressed() {
        this.LeftPressed = false;
    }

    void unToggleShootPressed() {
        this.ShootPressed = false;
    }

    void imgCheck(){
        if(getImg() == Game.bulletImage2){
            powAttack = 20;
        }
        else{
            powAttack = 0;
        }
    }

    void update() {
        if (this.LeftPressed) {
            this.moveLeft();
        }
        if (this.RightPressed) {
            this.moveRight();
        }
        if(this.ShootPressed && Game.tickCount %40==0){
            Bullets b = new Bullets(x,y, angle, Game.bulletImage);
            this.ammo.add(b);
        }
        this.ammo.forEach(bullets -> bullets.update());
        checkBorder();

        for(int i=0;i<ammo.size();i++){

            if (ammo.get(i).getHitBoxB().intersects(Game.puppies1.get(0).getHitBoxP())) {
                imgCheck();
                ammo.remove(i);
                Game.puppies1.get(0).setHealthP(Game.puppies1.get(0).getHealthP()-(10+powAttack));
            }
            else if (ammo.get(i).getHitBoxB().intersects(Game.puppies1.get(1).getHitBoxP())) {
                imgCheck();
                ammo.remove(i);
                Game.puppies1.get(1).setHealthP(Game.puppies1.get(1).getHealthP()-(10+powAttack));
            }
            else if (ammo.get(i).getHitBoxB().intersects(Game.puppies1.get(2).getHitBoxP())) {
                imgCheck();
                ammo.remove(i);
                Game.puppies1.get(2).setHealthP(Game.puppies1.get(2).getHealthP()-(10+powAttack));
            }
            else if (ammo.get(i).getHitBoxB().intersects(Game.puppies1.get(3).getHitBoxP())) {
                imgCheck();
                ammo.remove(i);
                Game.puppies1.get(3).setHealthP(Game.puppies1.get(3).getHealthP()-(10+powAttack));
            }
            else if (ammo.get(i).getHitBoxB().intersects(Game.puppies1.get(4).getHitBoxP())) {
                imgCheck();
                ammo.remove(i);
                Game.puppies1.get(4).setHealthP(Game.puppies1.get(4).getHealthP()-(10+powAttack));
            }
            else if (ammo.get(i).getHitBoxB().intersects(Game.puppies1.get(5).getHitBoxP())) {
                imgCheck();
                ammo.remove(i);
                Game.puppies1.get(5).setHealthP(Game.puppies1.get(5).getHealthP()-(10+powAttack));
            }
            else if(ammo.get(i).getY()<=0){
                ammo.remove(i);
            }
        }
    }

    private void moveLeft() {
        x = x - 5;
        this.hitBox.setLocation(x,y);
    }

    private void moveRight() {
        x = x + 5;
        this.hitBox.setLocation(x,y);
    }

    private void checkBorder() {
        if (x < 10) {
            x = 10;
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
