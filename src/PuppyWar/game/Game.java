/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PuppyWar.game;


import PuppyWar.GameConstants;
import PuppyWar.Launcher;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;

import static javax.imageio.ImageIO.read;

/**
 * @author margaret
 * @author anthony-pc
 */
public class Game extends JPanel implements Runnable {

    private BufferedImage world;
    public static BufferedImage bulletImage;
    public static BufferedImage bulletImage2;
    public static BufferedImage pupImage;
    public static Soldier soldier;
    private Launcher lf;
    private long tick = 0;
    static long tickCount = 0;
    private int currentTick = 0;
    public static int puppyCount = 6;
    private Background background;
    private Healthbar healthbar;
    private WinnerSymbol winnerSymbol;
    public static PowerUp powerUp;
    public static ArrayList<Puppies> puppies1;
    private Music music;

    public Game(Launcher lf){
        this.lf = lf;
    }

    @Override
    public void run(){
        try {
            music = new Music(1, "TMP.wav");
        }catch (Exception e){
            System.out.print(e.getStackTrace()+"no resourses found");
        }


        try {
            BufferedImage s2Img = null;
            BufferedImage s3Img = null;
            BufferedImage s4Img = null;
            BufferedImage h2Img = null;
            BufferedImage h3Img = null;
            BufferedImage h4Img = null;
            BufferedImage powImg = null;

            try {
                s2Img = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("soldier2.png")));
                s3Img = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("soldier3.png")));
                s4Img = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("soldier4.png")));
                h2Img = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("health2.png")));
                h3Img = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("health3.png")));
                h4Img = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("health4.png")));
                powImg = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("pow.png")));

            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }

            this.resetGame();
            while (true) {
                this.tick++;
                this.soldier.update();
                this.repaint();   // redraw game
                tickCount++;

                for(int i=0; i<Game.puppies1.size();i++){
                    //if(Game.puppies1.get(i).getY()!=600) {
                        this.puppies1.get(i).update();
                   //}
                }

                if(soldier.getHealth() <= 150 && soldier.getHealth()>100){
                    soldier.setImg(s2Img);
                    healthbar.setImg(h2Img);
                }
                if(soldier.getHealth() <= 100 && soldier.getHealth()>50){
                    soldier.setImg(s3Img);
                    healthbar.setImg(h3Img);
                }
                if(soldier.getHealth() <= 50 && soldier.getHealth()>0){
                    soldier.setImg(s4Img);
                    healthbar.setImg(h4Img);
                }
                if(soldier.getHealth() == 0) {
                    this.lf.setFrame("end");
                    return;
                }
                for(int i=0; i< Game.puppies1.size();i++){
                    if(Game.puppies1.get(i).getHealthP() == 0 && Game.puppies1.get(i).getY()<500){
                        Game.puppies1.get(i).setX(600);
                        Game.puppies1.get(i).setY(600);
                        puppyCount--;
                        System.out.println("pupcount:" + puppyCount);
                    }
                }
                if(this.tick >= 1000) {
                    powerUp.update();
                    if(powerUp.getCount()==1){
                        soldier.setImg(powImg);
                    }
                }

                if(puppyCount == 0) {
                    this.currentTick++;
                    winnerSymbol.setX(200);
                    winnerSymbol.setY(200);
                    if(currentTick> 300) {
                        this.lf.setFrame("end");
                        return;
                    }
                }



                Thread.sleep(1000 / 144); //sleep for a few milliseconds
                /*
                 * simulate an end game event
                 * we will do this with by ending the game when drawn 2000 frames have been drawn
                 */
                if(this.tick > 14000){
                    this.lf.setFrame("end");
                    return;
                }
            }
        } catch (InterruptedException ignored) {
            System.out.println(ignored);
        }
    }

    /**
     * Reset game to its initial state.
     */
    public void resetGame(){
        this.tick = 0;
        this.soldier.setX(360);
        this.soldier.setY(500);
        this.healthbar.setX(10);
        this.healthbar.setY(10);
        this.background.setX(0);
        this.background.setY(0);
        this.powerUp.setX(900);
        this.powerUp.setY(600);
        this.winnerSymbol.setX(900);
        this.winnerSymbol.setY(900);
        this.puppyCount = 6;

        this.puppies1 = new ArrayList<>();
        int ax = 10;
        int ay = 10;
        for(int i =0; i<6; i++){
            Puppies p = new Puppies(ax, ay, 60,0, Game.pupImage);
            this.puppies1.add(p);
            ax+= 150;
            if(i==2){
                ax=90;
                ay+=90;
            }
        }
    }


    /**
     * Load all resources for Tank Wars Game. Set all Game Objects to their
     * initial state as well.
     */
    public void gameInitialize() {
        this.world = new BufferedImage(GameConstants.GAME_SCREEN_WIDTH,
                GameConstants.GAME_SCREEN_HEIGHT,
                BufferedImage.TYPE_INT_RGB);

        BufferedImage sImg = null;
        BufferedImage pupImg = null;
        BufferedImage healthImg = null;
        BufferedImage powerImg = null;
        BufferedImage backImg = null;
        BufferedImage winImg = null;

        try {
            /*
             * note class loaders read files from the out folder (build folder in Netbeans) and not the
             * current working directory.
             */
            sImg = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("soldier.png")));
            pupImg = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("pup.png")));
            healthImg = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("health1.png")));
            powerImg = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("Panzerschreck.png")));
            winImg = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("winner.png")));
            Game.pupImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("pup.png")));
            Game.bulletImage = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("bullet.png")));
            Game.bulletImage2 = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("bulletPow.png")));
            backImg = read(Objects.requireNonNull(Game.class.getClassLoader().getResource("back.png")));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }

        background = new Background(0, 0 , backImg);
        healthbar = new Healthbar(50,50, healthImg);
        soldier = new Soldier(20, 20,0, 200, sImg);
        powerUp = new PowerUp(0,0, 0,powerImg);
        winnerSymbol = new WinnerSymbol(0,0, winImg);
        SoldierControl tc1 = new SoldierControl(soldier,  KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
        this.setBackground(Color.BLACK);
        this.lf.getJf().addKeyListener(tc1);
    }


    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        Graphics2D buffer = world.createGraphics();
        buffer.setColor(Color.BLACK);
        buffer.fillRect(0,0,GameConstants.GAME_SCREEN_WIDTH,GameConstants.GAME_SCREEN_HEIGHT);
        this.background.drawImage(buffer);
        this.soldier.drawImage(buffer);
        this.healthbar.drawImage(buffer);
        this.powerUp.drawImage(buffer);
        this.winnerSymbol.drawImage(buffer);
        this.puppies1.forEach(puppies -> puppies.drawImage(buffer));
        g2.drawImage(world,0,0,null);
    }

}
