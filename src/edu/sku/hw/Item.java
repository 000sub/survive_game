package edu.sku.hw;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Item {
	String caption;
	BufferedImage img;
	
	int x;
	int y;
	int w;
	int h;
	
	boolean isHave;
	boolean isUsed;
	boolean isItem;
	boolean isBtn;
	
	public Item(String caption) {
		this.caption = caption;
		
		try {
			this.img = ImageIO.read(Item.class.getResource("resources/scene/"+ caption + ".jpg"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.x = 0;
		this.y = 0;
		this.w = 1300;
		this.h = 730;

		this.isHave  = false;
		this.isUsed	 = false;
		this.isItem  = false;
		this.isBtn   = false;
	}
	
	public Item(String caption, double x, double y, double w, double h, boolean isBtn) {
		this.caption = caption;
		
		try {
			this.img = ImageIO.read(Item.class.getResource("resources/items/"+ caption + ".png"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		this.x = (int)(x / 25 * 1300);
		this.y = (int)(y / 15 * 730);
		this.w = (int)(w / 25 * 1300);
		this.h = (int)(h / 15 * 730);
		
		this.isHave  = false;
		this.isUsed	 = false;
		this.isItem  = true;
		this.isBtn   = isBtn;
		
		System.out.println(caption + " ==> " + this.x + "(x), " + this.y + "(y), " + this.w + "(w), " + this.h + "(h)");
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public boolean isHave() {
		return isHave;
	}

	public void setHave(boolean isHave) {
		this.isHave = isHave;
	}

	public boolean isUsed() {
		return isUsed;
	}

	public void setUsed(boolean isUsed) {
		this.isUsed = isUsed;
	}

	public boolean isItem() {
		return isItem;
	}

	public void setItem(boolean isItem) {
		this.isItem = isItem;
	}

	public boolean isBtn() {
		return isBtn;
	}

	public void setBtn(boolean isBtn) {
		this.isBtn = isBtn;
	}
}
