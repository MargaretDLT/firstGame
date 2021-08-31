package PuppyWar.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import static java.awt.Transparency.TRANSLUCENT;

public class Bullets {
    private int x, y;
    private int angle;
    private BufferedImage bulletImage;
    Rectangle hitBox;

    public Bullets(int x, int y, int angle, BufferedImage bulletImage){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.bulletImage = bulletImage;
        this.hitBox = new Rectangle(x,y,this.bulletImage.getWidth(),this.bulletImage.getHeight());
    }
    public Rectangle getHitBoxB(){
        return hitBox.getBounds();
    }
    void setImgB(BufferedImage bulletImage){
        this.bulletImage = bulletImage;
    }

    public int getY(){
        return y;
    }

    public BufferedImage getImg(){
        return bulletImage;
    }

    public void  shootUp(){
        y = y - 5;

        if(Game.powerUp.getCount()>=1 && Game.powerUp.getCount()<150){
            setImgB(Game.bulletImage2);
            Game.powerUp.setCount(Game.powerUp.getCount()+1);
        }
        this.hitBox.setLocation(x,y);
    }
    public void shootDown(){
        y = y + 5;
        this.hitBox.setLocation(x,y);
    }
    public void update(){
        shootUp();
    }
    public void pupUpdate(){
        shootDown();
    }
    public void drawImage(Graphics g){
            AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
            rotation.rotate(Math.toRadians(angle), this.bulletImage.getWidth() / 2.0, this.bulletImage.getHeight() / 2.0);
            Graphics2D g2d = (Graphics2D) g;
            g2d.drawImage(this.bulletImage, rotation, null);

            g2d.setColor(new Color(0,100,0,100));
            g2d.drawRect(x,y,this.bulletImage.getWidth(), this.bulletImage.getHeight());
    }
}
