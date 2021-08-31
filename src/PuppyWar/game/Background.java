package PuppyWar.game;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public class Background {
    private BufferedImage img;
    private int x;
    private int y;

    Background (int x, int y,BufferedImage img){
        this.img = img;
        this.x = x;
        this.y = y;
    }


    void setX(int x){ this.x = x; }

    void setY(int y) { this. y = y;}

    void drawImage(Graphics g) {
        AffineTransform rotation = AffineTransform.getTranslateInstance(x, y);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(this.img, rotation, null);
    }
}
