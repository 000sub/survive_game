package edu.sku.hw;

import java.util.HashMap;
import java.util.Map;

public class GameRun implements Callback {
	private GameRun   game;
	private GameScene scene;
	private int sceneNum = 0;
	
	@Override
	public void callbackClick(Item item) {
		
        switch(item.getCaption()) {		
			case "생존시작":
        	case "다음구역":
			case "탈출":	scene.setVisible(false);
							scene.dispose();
							scene = null;
								
							sceneNum++;
							scene = new GameScene(game, makeScene());
							break;
							
			case "구조완료":scene.setVisible(false);
							scene.dispose();
							scene = null;
							break;
							
			case "상태" :	new GameScene(game, makeStstus());
							break;
							
			default:		if (item.isBtn()) {									//button
								scene.setVisible(false);
								scene.dispose();
								scene = null;
									
								sceneNum++;
								scene = new GameScene(game, makeScene());
							
							} else {											//item
								item.setHave(true);
							}
							break;
        }
		System.out.println(item.getCaption() + " ==> Invoked. " + sceneNum + "(secne), " + item.isBtn() + "(isButton)");
	}
	
	public Map<Integer, Item> makeStstus() {
		Map<Integer, Item> map = new HashMap<>();
		
		map.put(0, new Item("status"));
		map.put(1, new Item("손난로", 		3.97, 10.32, 0.65, 1.01, false));
		map.put(2, new Item("구급상자", 	9.72,  7.54, 0.79, 1.02, false));
		map.put(3, new Item("서바이벌책", 	21.42, 7.94, 0.94, 0.79, false));
		map.put(4, new Item("책", 			23.49, 7.94, 0.99, 0.91, false));
					
        return map;
	}
	
	public Map<Integer, Item> makeScene() {
		Map<Integer, Item> map = new HashMap<>();
		
        switch(sceneNum) {
			case 0: map.put(0, new Item("scene00"));
					map.put(1, new Item("생존시작", 20.38, 11.74, 4.11, 1.75, true));
					break;
			    
			case 1: map.put(0, new Item("scene01"));
					map.put(1, new Item("다음구역", 20.38, 11.74, 4.11, 1.75, true));
					
					map.put(2, new Item("손난로", 		3.97, 10.32, 0.65, 1.01, false));
					map.put(3, new Item("구급상자", 	9.72,  7.54, 0.79, 1.02, false));
					map.put(4, new Item("서바이벌책", 	21.42, 7.94, 0.94, 0.79, false));
					map.put(5, new Item("책", 			23.49, 7.94, 0.99, 0.91, false));
					
					break;
				    
			case 2: map.put(0, new Item("scene02"));
					map.put(1, new Item("다음구역", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			case 3:	map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("다음구역", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			case 4: map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("다음구역", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			case 5: map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("다음구역", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			case 6: map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("다음구역", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			case 7: map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("탈출", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			//구명정
			case 8: map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("생존시작", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			//상어
			case 9: map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("상어", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//태풍
			case 10:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("태풍", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//해적
			case 11:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("해적", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//옷
			case 12:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("옷", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//비
			case 13:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("비", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//거북이
			case 14:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("거북이", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//구조신호
			case 15:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("구조신호", 16.1, 11.74, 8.39, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//구조헬기
			case 16:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("구조헬기", 16.1, 11.74, 8.39, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//구조완료
			case 17:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("구조완료", 16.1, 11.74, 8.39, 1.75, true));
					break;
					
        }
        
        return map;
	}
	
	public void gameStart(GameRun game) {
		this.game = game;
		this.sceneNum = 0;
		scene = new GameScene(game, makeScene());
	}
}
