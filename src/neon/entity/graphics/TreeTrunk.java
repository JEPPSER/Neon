package neon.entity.graphics;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;

import neon.entity.Entity;
import neon.graphics.EntityGraphics;
import neon.io.SpriteLoader;

public class TreeTrunk implements Entity {
	
	private String name;
	private float x;
	private float y;
	private float height;
	private int layer;
	private Image[] images;
	
	public TreeTrunk(float x, float y, float height) {
		this.name = "TreeTrunk";
		this.x = x;
		this.y = y;
		this.height = height;
		this.layer = 1;
		images = new Image[3];
		images[0] = SpriteLoader.getSprite("tree_1").getImage();
		images[1] = SpriteLoader.getSprite("tree_2").getImage();
		images[2] = SpriteLoader.getSprite("tree_3").getImage();
	}
	
	@Override
	public void render(Graphics g, float offsetX, float offsetY) {
		for (int i = 0; i < height / 50; i++) {
			g.drawImage(images[i % 3], x + offsetX, y + offsetY + i * 50);
		}
	}
	
	@Override
	public String toString() {
		return getID() + "," + x + "," + y + "," + height;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public void setX(float x) {
		this.x = x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public void setY(float y) {
		this.y = y;
	}

	@Override
	public float getWidth() {
		return 50;
	}

	@Override
	public void setWidth(float width) {
	}

	@Override
	public float getHeight() {
		return height;
	}

	@Override
	public void setHeight(float height) {
		this.height = height;
	}

	@Override
	public void setSize(float width, float height) {
	}

	@Override
	public void setLayer(int layer) {
		this.layer = layer;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	@Override
	public EntityGraphics getGraphics() {
		return null;
	}

	@Override
	public int getID() {
		return 29;
	}
}
