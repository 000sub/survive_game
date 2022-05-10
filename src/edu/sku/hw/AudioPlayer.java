package edu.sku.hw;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

public class AudioPlayer implements LineListener {
    boolean playCompleted;
    Clip clip = null;

    void play(int opt) {
    	stop();

    	String fn  = opt == 0 ? "0_challenge.wav" : (opt == 1 ? "1_theme.wav" : "2_requiem.wav");
    	URL    url = AudioPlayer.class.getResource("resources/audio/"+ fn);
        String fileName = url.getFile();
        
        File audioFile = new File(fileName);
 
        try {
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
 
            DataLine.Info info = new DataLine.Info(Clip.class, format);
 
            clip = (Clip) AudioSystem.getLine(info);
            clip.addLineListener(this);
            clip.open(audioStream);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
             
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
     
    @Override
    public void update(LineEvent event) {
        LineEvent.Type type = event.getType();
         
        if (type == LineEvent.Type.START) {
        	playCompleted = false;
            System.out.println("Audio started.");
             
        } else if (type == LineEvent.Type.STOP) {
            playCompleted = true;
            System.out.println("Audio completed.");
        }
    }
    
    public void start() {
	    clip.start();
	    
	    while (!playCompleted) {
	        try {
	            Thread.sleep(1000);
	            
	        } catch (InterruptedException ex) {
	            ex.printStackTrace();
	        }
	    }
	     
	    clip.close();
    }  
    
    public void stop() {
    	if (clip != null) {
            playCompleted = true;
            clip.stop();
    		clip.close();
    		clip = null;
            System.out.println("Audio stopped.");
    	}
    }
}