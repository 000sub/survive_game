package edu.sku.hw;

import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.util.Map;

public class GameRun implements Callback {
	Map<Integer, Item> itemGet = new HashMap<>();
    AudioPlayer player = new AudioPlayer();
    
    Random rand = new Random();
	private GameRun   game;
	private GameScene scene;
    private GameTimer timer;
	private int sceneNum = 0;
	private int itemGetCount = 0;
	private int[] randomScenes = rand.ints(10,8,19).toArray(); //scene 랜덤 지정 (씬 개수, 8부터, n-1까지 )
	private int sceneCounter = 0;
	private Status status = new Status();
	private int prevScene = 0;
	public int foodCount = 0;
	public int waterCount = 0;
	private boolean itemSelected = false;
	private boolean setFoodConsume = false;
	private boolean setWaterConsume = false;
	private String selectedItem = null;
	private double randNum;
	private int deathCount = 0;

	
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
			JOptionPane.showMessageDialog(scene, "Time Over!");
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
			case "표류시작": scene.setVisible(false);
			   			   sceneNum = 98;
			   			   scene = new GameScene(game, makeScene(), timer, true);
			   			   break;
			case "아이템선택": prevScene = sceneNum;
							scene.setVisible(false);
							sceneNum = -3;
							scene = new GameScene(game, makeScene(), timer, true);
							break;
			case "itemchecker": itemSelected=false;
								scene.setVisible(false);
								sceneNum = -3;
								scene = new GameScene(game, makeScene(), timer, true);
								break;
			//scene 별로 결과 나뉨
			case "show_result": randNum = Math.random();
			
				scene.setVisible(false);
			
				if (selectedItem == "구급상자") { //구급상자 사용시 체력 회복
					if (status.health <4) {
						status.health++;
					}
				}
				
				if (prevScene == 8) { //상어
					if (selectedItem == "식칼" || selectedItem == "작살총") { //무기 소지
						if (randNum <= 0.1) { //중상 10%
							sceneNum = 83;
							status.health = 0;
						}
						else if (randNum <= 0.6) { //도망 50%
							sceneNum = 82;
						}
						else { //사냥 40%
							sceneNum = 81;
							foodCount+=2;
						}
					}
					
					else if (selectedItem == "모터") { //이동 관련 아이템 소지
						if (randNum <= 0.1) { //중상 10%
							sceneNum = 83;
							status.health = 0;
						}
						else if (randNum <= 1) { //도망 90%
							sceneNum = 82;
						}
						else { //사냥 0%
							sceneNum = 81;
							foodCount+=2;
						}
					}
					
					else  { // 그 외
						if (randNum <= 0.6) { //중상 60%
							sceneNum = 83;
							status.health = 0;
						}
						else if (randNum <= 1) { //도망 40%
							sceneNum = 82;
						}
						else { //사냥 0%
							sceneNum = 81;
							foodCount+=2;
						}
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 9){ //토네이도
					if (selectedItem == "낚시대") { //낚시 관련 아이템 소지
						if (randNum <= 0.5) { //낚시 50%
							sceneNum = 91;
							foodCount+=3;
						}
						else if (randNum <= 0.6) { //도망 10%
							sceneNum = 92;
						}
						else { //중상 40%
							sceneNum = 93;
							status.health = 0;
						}
					}
					
					else if (selectedItem == "모터") { //이동 관련 아이템 소지
						if (randNum <= 0.1) { //중상 10%
							sceneNum = 93;
							status.health = 0;
						}
						else if (randNum <= 1) { //도망 90%
							sceneNum = 92;
						}
						else { //낚시 0%
							sceneNum = 91;
						}
					}
					
					else  { // 그 외
						if (randNum <= 0.4) { //중상 40%
							sceneNum = 93;
							status.health = 0;
						}
						else if (randNum <= 1) { //도망 60%
							sceneNum = 92;
						}
						else { //낚시 0%
							sceneNum = 91;
						}
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 10){ //난파선
					if (selectedItem == "식칼" || selectedItem == "작살총") { //무기 관련 아이템 소지
						if (randNum <= 0.1) { //탈출 도중 상처 10%
							sceneNum = 103;
							status.health--;
						}
						else if (randNum <= 0.9) { //도망 80%
							sceneNum = 102;
						}
						else { //아이템 획득 10%
							sceneNum = 101;
							itemGet.put(101, new Item("레이더반사기", 1,1,1,1,false));
						}
					}
					
					else if (selectedItem == "손전등" || selectedItem == "나침반") { //탐사 관련 아이템 소지
						if (randNum <= 0.1) { //탈출 도중 상처 10%
							sceneNum = 103;
							status.health--;
						}
						else if (randNum <= 0.2) { //도망 10%
							sceneNum = 102;
						}
						else { //아이템 획득 80%
							sceneNum = 101;
							itemGet.put(101, new Item("레이더반사기", 1,1,1,1,false));
						}
					}
					
					else  { // 그 외
						if (randNum <= 0.4) { //탈출 도중 상처 40%
							sceneNum = 103;
							status.health--;
						}
						else if (randNum <= 1) { //도망 50%
							sceneNum = 102;
						}
						else { //아이템 획득 10%
							sceneNum = 101;
							itemGet.put(101, new Item("레이더반사기", 1,1,1,1,false));
						}
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 11){ //무인도
					if (selectedItem == "식칼" || selectedItem == "작살총") { //무기 관련 아이템 소지
						if (randNum <= 0.3) { //코코넛 발견 30%
							sceneNum = 113;
							foodCount+=2;
							waterCount+=2;
						}
						else if (randNum <= 0.9) { //빈손 60%
							sceneNum = 112;
						}
						else { //벌레물림 10%
							sceneNum = 111;
							status.health--;
						}
					}
					
					else if (selectedItem == "손전등" || selectedItem == "나침반") { //탐사 관련 아이템 소지
						if (randNum <= 0.7) { //코코넛 발견 70%
							sceneNum = 113;
							foodCount+=2;
							waterCount+=2;
						}
						else if (randNum <= 0.9) { //빈손 20%
							sceneNum = 112;
						}
						else { //벌레물림 10%
							sceneNum = 111;
							status.health--;
						}
					}
					
					else  { // 그 외
						if (randNum <= 0.1) { //코코넛 발견 10%
							sceneNum = 113;
							foodCount+=2;
							waterCount+=2;

						}
						else if (randNum <= 0.5) { //빈손 40%
							sceneNum = 112;
						}
						else { //벌레물림 50%
							sceneNum = 111;
							status.health--;
						}
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 12){ //기상악화 (멀미)
					if (selectedItem == "구급상자") { //구급상자 소지
							sceneNum = 121; //건강 1 회복 (건강 1 증가는 위에서 구현되어있음)
					}
					
					else  { // 그 외
							sceneNum = 122; //건강 1 하락
							status.health--;
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 13){ //거북이 사냥
					if (selectedItem == "식칼" || selectedItem == "작살총" || selectedItem == "구명조끼" || selectedItem == "구명튜브") { //사냥 관련 아이템 소지
						if (randNum <= 0.2) { //사냥 실패 20%
							sceneNum = 132;
						}
						else { //사냥 성공 80%
							sceneNum = 131;
							foodCount++;
						}
					}
					
					else  { // 그 외
						if (randNum <= 0.7) { //사냥 실패 70%
							sceneNum = 132;
						}
						else { //사냥 성공 30%
							sceneNum = 131;
							foodCount++;
						}
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 14){ //일사병
					if (selectedItem == "정화기" || selectedItem == "옷") { //바닷물 식수 변환 아이템 소지
							sceneNum = 143;
							waterCount += 2;
					}
					
					else  { // 그 외
						if (randNum <= 0.7) { //넘어감 70%
							sceneNum = 142;
						}
						else { //탈수 30%
							sceneNum = 141;
							status.thirst = 0;
						}
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 15){ //바다뱀
					if (selectedItem == "식칼" || selectedItem == "작살총") { //무기 소지
						if (randNum <= 0.9) { //쫓아내기 90%
							sceneNum = 151;
						}
						else { //물림 10%
							sceneNum = 152;
							status.health--;
						}
							
					}
					
					else  { // 그 외
						if (randNum <= 0.4) { //쫓아내기 40%
							sceneNum = 151;
						}
						else { //물림 60%
							sceneNum = 152;
							status.health--;
						}
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				
				else if (prevScene == 16){ //우울증
					if (selectedItem == "책" || selectedItem == "서바이벌책" || selectedItem == "거울") { //심리 안정감 물품 소지
						sceneNum = 162;
					}
					
					else  { // 그 외
						sceneNum = 161;
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 17){ //번개
					if (selectedItem == "모터") { //이동 관련 아이템 소지
						if (randNum <= 0.01) { //번개 직격 1%
							sceneNum = 172;
							status.health = 0;
						}
						else { //탈출 99%
							sceneNum = 171;
						}
					}
					
					else  { // 그 외
						if (randNum <= 0.1) { //번개 직격 10%
							sceneNum = 172;
							status.health = 0;
						}
						else { //탈출 90%
							sceneNum = 171;
						}
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 18){ //보트 구멍
					if (selectedItem == "펌프") { //펌프 소지
						sceneNum = 182;
					}
					
					else  { // 그 외
						sceneNum = 181;
						
						//아이템 랜덤 사라짐
						itemGet.remove(2);
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				else if (prevScene == 98){ //1일차 멀미
					if (selectedItem == "구급상자") { //구급상자 소지
							sceneNum = 121; //건강 1 회복
					}
					
					else  { // 그 외
							sceneNum = 122; //건강 1 하락
							status.health--;
					}
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				
				
				
			case "food_off": 
				if (foodCount > 0) {
					setFoodConsume = true;
					scene.setVisible(false);
					scene.map.replace(3, new Item("food_on", 5,  11, 3,  2, true));
					scene = new GameScene(game, scene.map, timer, true);
				}
				break;
			case "water_off": 
				if (waterCount > 0) {
					setWaterConsume = true;
					scene.setVisible(false);
					scene.map.replace(4, new Item("water_on", 8,  11, 3,  2, true));
					scene = new GameScene(game, scene.map, timer, true);
				}
				break;
			case "food_on":
				setFoodConsume = false;
				scene.setVisible(false);
				scene.map.replace(3, new Item("food_off", 5,  11, 3,  2, true));
				scene = new GameScene(game, scene.map, timer, true);
				break;
			case "water_on":
				setWaterConsume = false;
				scene.setVisible(false);
				scene.map.replace(4, new Item("water_off", 8,  11, 3,  2, true));
				scene = new GameScene(game, scene.map, timer, true);
				break;
			
			case "return_icon":
					scene.setVisible(false);
				   sceneNum = prevScene;
				   scene = new GameScene(game, makeScene(), timer, true);
				   break;
			case "탈출":	sceneNum = 7;
							scene.setVisible(false);
							scene = new GameScene(game, makeScene(), timer, true);
							break;
			case "foodwaterbutton": JOptionPane.showMessageDialog(scene, "식량 개수: "+foodCount+"\n물 개수: "+waterCount);
 									break;
			case "backpack": 
							 prevScene = sceneNum;
							 sceneNum = -2;
							 scene.setVisible(false);
							  scene = new Inventory(game, makeScene(), timer, true, itemGet);
							  break;
			case "상태": 
						JOptionPane.showMessageDialog(scene, status.day+" 일 차 생존\n건강: "+status.getHealth()+"\n포만감: "+status.getHunger()+"\n수분: "+status.getThirst());
			 			break;
			case "select_1": prevScene = sceneNum;
				sceneNum = 2;
			scene.setVisible(false);
			 scene = new GameScene(game, makeScene(), timer, true);
			 break;
			case "select_2": prevScene = sceneNum;
			sceneNum = 3;
			scene.setVisible(false);
			scene = new GameScene(game, makeScene(), timer, true);
			break;
			case "select_3": prevScene = sceneNum;
			sceneNum = 1;
			scene.setVisible(false);
			scene = new GameScene(game, makeScene(), timer, true);
			break;
			case "select_4": prevScene = sceneNum;
			sceneNum = 4;
			scene.setVisible(false);
			scene = new GameScene(game, makeScene(), timer, true);
			break;
			case "select_5": prevScene = sceneNum;
			sceneNum = 5;
			scene.setVisible(false);
			scene = new GameScene(game, makeScene(), timer, true);
			break;
			case "nextday": 
				scene.setVisible(false);
				//상태창 날 업데이트
				status.day++;
				
				//배고픔 업데이트
				if (!setFoodConsume) {
					if (status.hunger != 0) {
						status.hunger--;
					}
					else { //기아 상태 게임오버
						sceneNum=99;
						scene = new GameScene(game, makeScene(), timer, true);
						break;
					}
				}
				else {
					status.hunger++;
					foodCount--;
					setFoodConsume = false;
				}
				
				//수분 업데이트
				if (!setWaterConsume) {
					if (status.thirst != 0) {
						status.thirst--;
					}
					else { //탈수 상태 게임오버
						sceneNum=99;
						scene = new GameScene(game, makeScene(), timer, true);
						break;
					}
				}
				else {
					status.thirst++;
					waterCount--;
					setWaterConsume = false;
				}
				
				//상태창 날이 5의 배수라면 탈출 이벤트씬
				if (status.day % 5 == 0) {
					sceneNum = rand.nextInt(3)+50; //50~52
				}
				else {
					sceneNum = randomScenes[sceneCounter++];
				}
				
				if (status.health < 0) { //중상 상태에서 이벤트로 건강 감소
					sceneNum = 99;
					scene = new GameScene(game, makeScene(), timer, true);
					break;
				}
				
				if (status.health == 0) { //중상 체크
					if (deathCount < 3) deathCount++;
					else { //중상 3일 경과 시 게임오버 처리
						sceneNum = 99;
						scene = new GameScene(game, makeScene(), timer, true);
						break;
					}
				}
				
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
							
			default:		
				
								if (item.isBtn()) {									//button
								scene.setVisible(false);
								scene.dispose();
								scene = null;
									
								sceneNum++;
								scene = new GameScene(game, makeScene(), timer, true);
							
							} else {											//아이템 획득
								
								if (sceneNum==-3) { //아이템 선택창에서의 아이템 클릭 처리
									
									if (itemSelected == true) {
										JOptionPane.showMessageDialog(scene, "먼저 장착한 아이템을 해제해주세요");
							 			break;
									}
									selectedItem = item.getCaption();
									for (Map.Entry<Integer, Item> entry : scene.map.entrySet()) {
										Integer key = entry.getKey();
										Item value = entry.getValue();
										
										if (value.getCaption().equals(item.getCaption())) {
											itemSelected=true;
											JOptionPane.showMessageDialog(scene, selectedItem+" 장착");
											scene.map.replace(key, new Item("itemchecker", value.getX()*Constant.ppt_width/ Constant.game_width+0.5, 
													value.getY()*Constant.ppt_height/ Constant.game_height-0.2, 1.3, 1.3, false));
											scene.setVisible(false);
											scene = new GameScene(game, scene.map, timer, true);
											break;
										}
									}
								
									break;
								}
								if (sceneNum==-2) { //Inventory 내부 아이템 클릭 처리
									JOptionPane.showMessageDialog(scene, item.getCaption());
									break;
								}
								
								if (!(item.getCaption().equals("물") || item.getCaption().equals("식량"))) {
									for (Map.Entry<Integer, Item> entry : itemGet.entrySet()) {
								    	Item x = entry.getValue();
								    	
								    	if (x.getCaption() == item.getCaption()) {
								    		return;
								    	}
									}
								}
								
								
								
								item.setHave(true);
								JOptionPane.showMessageDialog(scene, item.getCaption()+" 획득");
								if (item.getCaption().equals("물")) {
									waterCount++;
									break;
								}
								if (item.getCaption().equals("식량")) {
									foodCount++;
									break;
								}
								
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
		int i = 2;
		
        switch(sceneNum) {
        //아이템선택창
        case -3: map.put(0, new Item("inventory_background"));
        		 map.put(1, new Item("show_result", 23, 13, 2, 1, true));
        		
        		for (Map.Entry<Integer, Item> entry: itemGet.entrySet()) {
        			if (i<=9) {
        				map.put(i, new Item(entry.getValue().getCaption(), -1.4+2.4*i, 3, 1, 1, false));
        			}
        			else if (i>9 && i<=17) {
        				map.put(i, new Item(entry.getValue().getCaption(), -1.4+2.4*(i-8), 4.5, 1, 1, false));
        			}
        			else if (i>17 && i<=25) {
        				map.put(i, new Item(entry.getValue().getCaption(), -1.4+2.4*(i-16), 6, 1, 1, false));
        			}
        			i++;
        		}
        		break;
        //인벤토리	
        case -2: map.put(0, new Item("inventory_background"));
                 map.put(1, new Item("return_icon", 23, 13, 1, 1, true));
                 map.put(99, new Item("foodwaterbutton", 0.5, 13, 3, 1, true));
                 
                 for (Map.Entry<Integer, Item> entry: itemGet.entrySet()) {
                	 if (i<=9) {
                		 map.put(i, new Item(entry.getValue().getCaption(), -1.4+2.4*i, 3, 1, 1, false));
                	 }
                	 else if (i>9 && i<=17) {
                		 map.put(i, new Item(entry.getValue().getCaption(), -1.4+2.4*(i-8), 4.5, 1, 1, false));
                	 }
                	 else if (i>17 && i<=25) {
                		 map.put(i, new Item(entry.getValue().getCaption(), -1.4+2.4*(i-16), 6, 1, 1, false));
                	 }
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
					map.put(1, new Item("표류시작", 20.38, 11.74, 4.11, 1.75, true));
					break;
				    
			//상어
			case 8: map.put(0, new Item("scene0" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
					    
			//토네이도
			case 9: map.put(0, new Item("scene0" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
					
			
				    
			//난파선
			case 10:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
				    
			//옷
			case 11:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
				    
			//비
			case 12:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
				    
			//거북이
			case 13:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
		    
		    //일사병
			case 14:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
					
					
			//우울증
			case 16:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
			//번개
			case 17:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
				    
					
			//잠망경
			case 50:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
		    
			//구조선박
			case 51:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
				    
			//구조헬기
			case 52:map.put(0, new Item("scene" + sceneNum));
			map.put(1, new Item("backpack", 1, 11, 2, 2, true));
			map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
			map.put(3, new Item("food_off", 5,  11, 3,  2, true));
			map.put(4, new Item("water_off", 8,  11, 3,  2, true));
			map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
		    break;
				    
			
					
		    
		    //결과창
			case 81: case 82: case 83:
			case 91: case 92: case 93: 
			case 101: case 102: case 103:
			case 111: case 112: case 113:
			case 121: case 122:
			case 131: case 132:
			case 141: case 142: case 143:
			case 151: case 152:
			case 161: case 162:
			case 171: case 172:
			case 181: case 182:
				map.put(0, new Item("scene" + sceneNum));
				map.put(1, new Item("nextday", 23,  13, 2,  1, true));
				break;
			
					
			//구조완료
			case 97:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("구조완료", 16.1, 11.74, 8.39, 1.75, true));
					break;
				    
			//멀미 (1일차 고정)		
			case 98:map.put(0, new Item("scene" + sceneNum));
					map.put(1, new Item("backpack", 1, 11, 2, 2, true));
					map.put(2, new Item("상태", 3,  11, 1.8,  1.75, true));
					map.put(3, new Item("food_off", 5,  11, 3,  2, true));
					map.put(4, new Item("water_off", 8,  11, 3,  2, true));
					map.put(5, new Item("아이템선택", 21,  11, 3,  2, true));
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
	public Map<Integer, Item> makeStatus() {
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
