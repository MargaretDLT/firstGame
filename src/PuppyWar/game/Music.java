package PuppyWar.game;
/**
 * taken from SoundPlayer file from the Airstrike example
 */

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Music {
    private AudioInputStream soundStream;
    private String soundFile;
    private Clip clip;
    private int type;//1 for sounds that needs to be played all the time
    // 2 for sounds that only need to be played once


    public Music(int type, String soundFile){
        this.soundFile = soundFile;
        this.type = type;
        try{
            soundStream = AudioSystem.getAudioInputStream(Music.class.getClassLoader().getResource(soundFile));
            clip = AudioSystem.getClip();
            clip.open(soundStream);
        }
        catch(Exception e){
            System.out.println(e.getMessage() + "No sound documents are fouond");
        }
        if(this.type == 1){
            Runnable myRunnable = new Runnable(){
                public void run(){
                    while(true){
                        clip.start();
                        clip.loop(clip.LOOP_CONTINUOUSLY);
                        try {
                            Thread.sleep(10000);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Music.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
            };
            Thread thread = new Thread(myRunnable);
            thread.start();
        }
    }

    public void play(){
        clip.start();
    }
    public void stop(){
        clip.stop();
    }
}

