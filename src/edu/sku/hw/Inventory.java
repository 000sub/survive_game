package edu.sku.hw;

import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;

import edu.sku.hw.GameScene.MyPanel;

public class Inventory extends GameScene {

	public Inventory(GameRun game, Map<Integer, Item> map, JComponent timer, boolean canExit) {
		super(game, map, timer, canExit);
		// TODO Auto-generated constructor stub
	}
	
	private void initData() {
		setTitle("Inventory");  	
        setAlwaysOnTop(true);
		setSize(Constant.game_width, Constant.game_height);  					//프레임 크기
		setLocationRelativeTo(null);											//센터
		
		if (canExit) {
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  					//창 닫기 -> 종료
		}
		
		//panel
		mypanel = new MyPanel(); 												//객체를 생성하지 않으면 이미지 표시 안됨
		
        //timer
		if (game.isTimerShow()) {
			mypanel.add(timer);
		}
	}

}
