package edu.sku.hw;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class GameScene extends JFrame {
	Map<Integer, Item> map;
	MyPanel mypanel;																						//내부 클래스 변수 선언
	GameRun game;
	JComponent timer;
	boolean canExit;

	private class MyPanel extends JPanel {  																//JPanel을 상속받은 내부 클래스 선언
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);

		    for (Map.Entry<Integer, Item> entry : map.entrySet()) {
		    	Item item = entry.getValue();
		    	g.drawImage(item.getImg(), item.getX(), item.getY(), item.getW(), item.getH(), null); 		//이미지 위치 및 크기 설정
		    }
		}
	}

	public GameScene(GameRun game, Map<Integer, Item> map, JComponent timer, boolean canExit) {
		this.game    = game;
		this.map     = map;
		this.timer   = timer;
		this.canExit = canExit;
		
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setTitle("Survival Game");  	
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

	private void setInitLayout() {
		setVisible(true);  														//프레임이 화면에 표시됨
		this.add(mypanel);  													//내부 클래스를 프레임에 추가
	}

	private void addEventListener() {
		mypanel.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int posX = e.getX();
		        int posY = e.getY();

			    for (Map.Entry<Integer, Item> entry : map.entrySet()) {
			    	Item item = entry.getValue();
			    	
			    	if (item.isItem() && posX > item.getX() && posX < item.getX() + item.getW() && posY > item.getY() && posY < item.getY() + item.getH()) {
			    		
			    		if (Constant.isDebug) {
			    			System.out.println(item.getCaption() + " ==> " + posX + "(x), " + posY + "(x) have been clicked.");
			    		}
			    		
			    		game.callbackClick(item);
			    	}
			    }
		    }
		});
	}
}

