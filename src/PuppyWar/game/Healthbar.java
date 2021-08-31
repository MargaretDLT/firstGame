package PuppyWar.game;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Healthbar {
    private BufferedImage img;
    private int x;
    private int y;

    Healthbar(int x, int y, BufferedImage img){
        this.x = x;
        this.y = y;
        this.img = img;
    }
    void setX(int x) { this.x = x; }
    void setY(int y) { this. y = y;}

    void setImg(BufferedImage img){
        this.img = img;
    }


    public void drawImage(Graphics2D g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(this.img, x, y, null);
    }
}
