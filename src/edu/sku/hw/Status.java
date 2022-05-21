package edu.sku.hw;

public class Status {

	public int health = 4;   //4: 활기참 3: 보통: 2: 지침 1: 아픔 0: 중상
	public int hunger = 3;   //3: 포만 2: 보통 1: 굶주림 0: 기아
	public int thirst = 3;   //3: 촉촉함 2: 보통 1: 목마름 0: 탈수
	public int day = 1;
	public int score = 0;
	

	public String getHealth() {
		if (health ==4) {
			return "활기참";
		}
		else if (health == 3) {
			return "보통";
		}
		else if (health == 2) {
			return "지침";
		}
		else if (health == 1) {
			return "아픔";
		}
		else if (health == 0) {
			return "중상";
		}
		
		return null;
	}
	
	public String getHunger() {
		if (hunger ==3) {
			return "포만";
		}
		else if (hunger == 2) {
			return "보통";
		}
		else if (hunger == 1) {
			return "굶주림";
		}
		else if (hunger == 0) {
			return "기아";
		}
		
		return null;
	}
	
	public String getThirst() {
		if (thirst ==3) {
			return "촉촉함";
		}
		else if (thirst == 2) {
			return "보통";
		}
		else if (thirst == 1) {
			return "목마름";
		}
		else if (thirst == 0) {
			return "탈수";
		}
		
		return null;
	}
	
	public int getScore() { //올만점 14000 + 날짜수
		score = health*2000 + hunger*1000 + thirst*1000 + day*500;
		return score;
	}
	
	
	public String getRank() {
		if (score > 12000) {
			return "A";
		}
		else if (score > 8000) {
			return "B";
		}
		else if (score > 4000) {
			return "C";
		}
		else {
			return "D";
		}
	}
}
