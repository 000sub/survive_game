package edu.sku.hw;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.UIManager;

@SuppressWarnings("serial")
public class GameTimer extends JPanel {
	private final static int ONE_SECOND = 1000;
	
    private Timer timer;
    private TimerTask task;
    private JProgressBar progressBar;																					//내부 클래스 변수 선언
    private GameRun game;

    public GameTimer(GameRun game) {
        super(new BorderLayout());
        
        this.game = game;
        task = new TimerTask();
        
        UIManager.put("ProgressBar.selectionBackground", Color.black);
        UIManager.put("ProgressBar.selectionForeground", Color.white);
        UIManager.put("ProgressBar.foreground", new Color(8, 32, 128));
        
        progressBar = new JProgressBar(0, task.getLengthOfTask());
        progressBar.setValue(100);
        progressBar.setString("00:60");
        progressBar.setStringPainted(true);
        
        JPanel panel = new JPanel();
        panel.add(progressBar);
        
        add(panel, BorderLayout.PAGE_START);

        //create a timer.
        timer = new Timer(ONE_SECOND, new ActionListener() {
        	
            public void actionPerformed(ActionEvent evt) {
                String  secs = "00"  + Integer.toString(task.getCurrent());
                		secs = "00:" + secs.substring(secs.length() - 2); 

                progressBar.setValue(task.getCurrent());
                progressBar.setString(secs);
                
                if (task.isDone()) {
                    timer.stop();
                    progressBar.setValue(task.getLengthOfTask());
                    progressBar.setString("00:60");
                    gameTimerOver();
                }
            }
        });
    }
    
    public void gameTimerStart() {
    	task.setCurrent();
        task.go();
        timer.start();
    }
    
    public void gameTimerStop() {
        timer.stop();
        progressBar.setValue(task.getLengthOfTask());
        progressBar.setString("00:60");
    }
    
    public void gameTimerOver() {
        game.callbackTimeOver();
    }
}
