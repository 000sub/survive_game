package edu.sku.hw;

import java.util.HashMap;
import java.util.Map;

public class GameRun implements Callback {
	Map<Integer, Item> itemGet = new HashMap<>();
    AudioPlayer player = new AudioPlayer();
    
	private GameRun   game;
	private GameScene scene;
    private GameTimer timer;
	private int sceneNum = 0;
	private int itemGetCount = 0;

	
	public boolean isTimerShow() {
		boolean isShow = sceneNum <= 5 ? true : false;
		
		if (!isShow) {
			timer.gameTimerStop();
		}
		return isShow;
	}
	
	
	public void audioStop() {
		if (sceneNum == 5 || sceneNum == 6 || sceneNum == 16) {
			player.stop();
		}
	}
	
	
	@Override
	public void callbackClose() {
		player.stop();
	}
	
	
	@Override
	public void callbackTimeOver() {
		if (Constant.isDebug) {
			return;													//개발시 리턴
		}
		
		if (sceneNum <= 5) {
			sceneNum = 6-1;					
			callbackClick(new Item("다음구역", 0, 0, 0, 0, true));	//타임오버
		}
	}
    
	@Override
	public void callbackClick(Item item) {
		if (Constant.isDebug) {
			System.out.println(item.getCaption() + " ==> Invoked. " + sceneNum + "(secne), " + item.isBtn() + "(isButton)");
		}
		
        switch(item.getCaption()) {		
			case "생존시작": timer.gameTimerStart();
						scene.setVisible(false);
						   sceneNum = -1;
						   scene = new GameScene(game, makeScene(), timer, true);
						   break;
			case "return_icon":
					scene.setVisible(false);
					sceneNum = -1;
					scene = new GameScene(game, makeScene(), timer, true);
					break;
			case "탈출":	sceneNum = 7;
							scene.setVisible(false);
							scene = new GameScene(game, makeScene(), timer, true);
							break;
			case "backpack": sceneNum = -2;
							 scene.setVisible(false);
							  scene = new Inventory(game, makeScene(), timer, true);
							  break;
			case "select_1": sceneNum = 2;
			scene.setVisible(false);
			 scene = new GameScene(game, makeScene(), timer, true);
			 break;
			case "select_2": sceneNum = 3;
			scene.setVisible(false);
			scene = new GameScene(game, makeScene(), timer, true);
			break;
			case "select_3": sceneNum = 1;
			scene.setVisible(false);
			scene = new GameScene(game, makeScene(), timer, true);
			break;
			case "select_4": sceneNum = 4;
			scene.setVisible(false);
			scene = new GameScene(game, makeScene(), timer, true);
			break;
			case "select_5": sceneNum = 5;
			scene.setVisible(false);
			scene = new GameScene(game, makeScene(), timer, true);
			break;
			case "구조완료":scene.setVisible(false);
							scene.dispose();

							scene = null;
							game = null;
							timer = null;
							
							System.exit(0);
							break;
							
			default:		if (item.isBtn()) {									//button
								scene.setVisible(false);
								scene.dispose();
								scene = null;
									
								sceneNum++;
								scene = new GameScene(game, makeScene(), timer, true);
							
							} else {											//아이템 획득
								if (!(item.getCaption().equals("물") || item.getCaption().equals("식량"))) {
									for (Map.Entry<Integer, Item> entry : itemGet.entrySet()) {
								    	Item x = entry.getValue();
								    	
								    	if (x.getCaption() == item.getCaption()) {
								    		return;
								    	}
									}
								}
								
								item.setHave(true);
								itemGet.put(itemGetCount, item);				//아이템 저장
								itemGetCount++;
								
								//아이템 획득 리스트
								System.out.println("\n");
								for (Map.Entry<Integer, Item> entry : itemGet.entrySet()) {
							    	Item x = entry.getValue();
							    	System.out.println("아이템 획득: " + x.getCaption() + ", " + x.isHave() + "(isHave), " + x.isUsed() + "(isUsed)");
							    	
								}
							}
							break;
        }
	}
	
	
	public Map<Integer, Item> makeScene() {
		Map<Integer, Item> map = new HashMap<>();
		
        switch(sceneNum) {
        case -2: map.put(0, new Item("inventory_background"));
                 map.put(1, new Item("return_icon", 23, 13, 1, 1, true));
                 int i = 2;
                 for (Map.Entry<Integer, Item> entry: itemGet.entrySet()) {
                	 map.put(i, new Item(entry.getValue().getCaption(), -1.4+2.4*i, 3, 1, 1, true));
                	 i++;
                 }
        		 break;
        case -1: map.put(0, new Item("scene_mapselect"));
		 map.put(1, new Item("select_1", 5, 5, 4.11, 1.75, true));
		 map.put(2, new Item("select_2", 5, 9, 4.11, 1.75, true));
		 map.put(3, new Item("select_3", 5, 7, 4.11, 1.75, true));
		 map.put(4, new Item("select_4", 10, 5, 4.11, 1.75, true));
		 map.put(5, new Item("select_5", 10, 9, 4.11, 1.75, true));
		 map.put(6, new Item("탈출", 20.38, 11.74, 4.11, 1.75, true));
		 map.put(7, new Item("backpack", 1, 11, 2, 2, true));
		 break;
		 
case 0: map.put(0, new Item("scene00"));
		map.put(1, new Item("생존시작", 20.38, 11.74, 4.11, 1.75, true));
		break;
   
case 1: map.put(0, new Item("scene01"));
		map.put(1, new Item("return_icon", 23, 13, 1, 1, true));
		
		map.put(2, new Item("손난로", 		3.97, 10.32, 0.65, 1.01, false));
		map.put(3, new Item("구급상자", 	9.72,  7.54, 0.79, 1.02, false));
		map.put(4, new Item("서바이벌책", 	21.42, 7.94, 0.94, 0.79, false));
		map.put(5, new Item("책", 			23.49, 7.94, 0.99, 0.91, false));
		break;
	    
case 2: map.put(0, new Item("scene02"));
		map.put(1, new Item("return_icon", 23, 13, 1, 1, true));
		
		map.put(2, new Item("우비", 	0.79,	10.72,	1.59,	1.29, false));
		map.put(3, new Item("펌프", 	8.93,	5.16,	1.39,	1.2,  false));
		map.put(4, new Item("모터", 	14.71,	9.13,	1.31,	2.18, false));
		map.put(5, new Item("손전등", 	21.43,	3.37,	1.28,	0.73, false));
		break;
	    
case 3:	map.put(0, new Item("scene0" + sceneNum));
		map.put(1, new Item("return_icon", 23, 13, 1, 1, true));
		
		map.put(2, new Item("물",		4.76,	6.5,	1.19,	0.99, false));
		map.put(3, new Item("식칼",		9.45,	6.65,	0.71,	0.79, false));
		map.put(4, new Item("식량",		8.93,	8.53,	1.2,	2.43, false));
		map.put(5, new Item("정화기",	16.74,	6.73,	0.64,	1.3, false));
		break;
	    
case 4: map.put(0, new Item("scene0" + sceneNum));
		map.put(1, new Item("return_icon", 23, 13, 1, 1, true));
		
		map.put(2, new Item("작살총",	2.29,	10.96,	1.97,	1.89, false));
		map.put(3, new Item("낚시대",	8.92,	5.98,	2.62,	2.21, false));
		map.put(4, new Item("신호탄",	17.26,	7.35,	0.41,	0.6,  false));
		map.put(5, new Item("거울",		19.33,	8.16,	0.99,	0.63, false));
		break;
	    
case 5: map.put(0, new Item("scene0" + sceneNum));
		map.put(1, new Item("return_icon", 23, 13, 1, 1, true));
		map.put(2, new Item("나침반",		0.2,	8.33,	0.58,	0.6,  false));
		map.put(3, new Item("구명튜브",		1.98,	9.13,	1.92,	2.07, false));
		map.put(4, new Item("레이더반사기",	6.75,	12.5,	1.24,	1.19, false));
		map.put(5, new Item("구명조끼",		18.06,	11.71,	1.92,	1.73, false));
		break;
				    
			case 6: map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("탈출", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			case 7: map.put(0, new Item("scene0" + sceneNum));
					map.put(1, new Item("생존시작", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			//상어
			case 8: map.put(0, new Item("scene0" + sceneNum));
					//map.put(1, new Item("상어", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//태풍
			case 9: map.put(0, new Item("scene0" + sceneNum));
					//map.put(1, new Item("태풍", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//해적
			case 10:map.put(0, new Item("scene" + sceneNum));
					//map.put(1, new Item("해적", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//옷
			case 11:map.put(0, new Item("scene" + sceneNum));
					//map.put(1, new Item("옷", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//비
			case 12:map.put(0, new Item("scene" + sceneNum));
					//map.put(1, new Item("비", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//거북이
			case 13:map.put(0, new Item("scene" + sceneNum));
					//map.put(1, new Item("거북이", 18.3, 11.74, 6.19, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//구조신호
			case 14:map.put(0, new Item("scene" + sceneNum));
					//map.put(1, new Item("구조신호", 16.1, 11.74, 8.39, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//구조헬기
			case 15:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("구조헬기", 16.1, 11.74, 8.39, 1.75, true));
					map.put(2, new Item("상태", 0.9,  11.74, 1.8,  1.75, true));
					break;
				    
			//구조완료
			case 16:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("구조완료", 16.1, 11.74, 8.39, 1.75, true));
					break;
				    
			//GameOver
			case 99:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("게임오버", 7.9, 5.94, 8.39, 1.75, true));
					break;
        }
        
        return map;
	}
	
	public void gameStart(GameRun game) {
		this.game  = game;
		this.timer = new GameTimer(game);
		this.sceneNum = 0;
		scene = new GameScene(game, makeScene(), timer, true);
        player.play(0);
	}
	
	
	//여기부터 작성하세요.
	public Map<Integer, Item> makeStstus() {
		Map<Integer, Item> map = new HashMap<>();
		
		map.put(0, new Item("status"));
		
		//일차
		//건강
		//열량
		//갈증
		//구명정상태
		//소지품 목록 표시
					
        return map;
	}
}
