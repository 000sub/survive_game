package edu.sku.hw;

public class GameMain {
	public static void main(String[] args) throws Exception {
		GameRun game = new GameRun();
		game.gameStart(game);
		
		SwingTimerDemo timer = new SwingTimerDemo();
    	timer.createAndShowGUI();
	}
}