package edu.sku.hw;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
class GameScene extends JFrame {
	Map<Integer, Item> map;
	MyPanel mypanel;																						//내부 클래스 변수 선언
	GameRun game;

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

	public GameScene(GameRun game, Map<Integer, Item> map) {
		this.game = game;
		this.map  = map;
		
		initData();
		setInitLayout();
		addEventListener();
	}

	private void initData() {
		setTitle("Survival Game");  																		//프레임 제목
		setSize(1300, 730);  																				//프레임 크기
		setLocationRelativeTo(null);																		//센터
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  													//프레임 창 닫기 
		mypanel = new MyPanel(); 																			//내부 클래스 객체 생성, 객체를 생성하지 않으면 이미지 표시 안됨
	}

	private void setInitLayout() {
		setVisible(true);  																					//true값을 넣어야 프레임이 화면에 표시됨
		this.add(mypanel);  																				//내부 클래스를 프레임에 추가
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
			    		System.out.println(item.getCaption() + " ==> " + posX + "(x), " + posY + "(x) have been clicked.");
			    		game.callbackClick(item);
			    	}
			    }
		    }
		});
	}
}

