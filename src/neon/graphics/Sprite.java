package neon.graphics;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class Sprite {
	
	private Image image;
	private String name;
	private float width;
	private float height;
	private float offsetX;
	private float offsetY;

	public Sprite(String name) {
		this.name = name;
		createImage();
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
	public float getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(float offsetX) {
		this.offsetX = offsetX;
	}

	public float getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(float offsetY) {
		this.offsetY = offsetY;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getWidth() {
		return this.width;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public float getHeight() {
		return this.height;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	public Image getImage() {
		return image;
	}
	
	private void createImage() {
		try {
			image = new Image("res/images/" + name + ".png");
		} catch (SlickException e) {
			e.printStackTrace();
		}	
	}
}
